package de.thkoeln.glug.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import de.thkoeln.glug.communication.AllSlugsAllocatedMessage;
import de.thkoeln.glug.communication.AllocateSlugRequest;
import de.thkoeln.glug.communication.AnswerChallengeRequest;
import de.thkoeln.glug.communication.AnswerChallengeValidationMessage;
import de.thkoeln.glug.communication.CountdownMessage;
import de.thkoeln.glug.communication.PlayerResponse;
import de.thkoeln.glug.communication.RoundFinishedMessage;
import de.thkoeln.glug.communication.SlugAllocatedMessage;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;

@Controller
public class GameController {
	final static Logger LOG = LoggerFactory.getLogger(GameController.class);
	private final static long ROUND_DURATION_MS = 60000;
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;

	@Autowired
	GameService gameService;

	@MessageMapping("/game/{accessCode}/player")
    @SendTo("/topic/game/{accessCode}/players")
    public Set<PlayerResponse> joinGame(@DestinationVariable String accessCode, @Payload int playerId) {
		LOG.info("new player {} for game {}", playerId, accessCode);
		Set<Player> players = gameService.addPlayer(accessCode, playerId);
		Player gamemaster = gameService.getGamemaster(accessCode);
		Set<PlayerResponse> playersResponse = new HashSet<PlayerResponse>();
		players.forEach(player -> {
			playersResponse.add(new PlayerResponse(player.getId(), player.getName(), player.getId() == gamemaster.getId()));
		});
        return playersResponse;
    }

	@MessageMapping("/game/{accessCode}/start")
    public void startGame(@DestinationVariable String accessCode) {
		LOG.info("starting round for game {}", accessCode);
		Round round = gameService.startRound(accessCode);
		publishQuizChallenge(accessCode, round);
    }

	@MessageMapping("/game/{accessCode}/answer")
    public void answerChallenge(@DestinationVariable String accessCode, @Payload AnswerChallengeRequest answerChallengeRequest) {
		LOG.info("new answer in game {}: {}", accessCode, answerChallengeRequest);
		//validate challenge answer
		boolean answeredCorrectly = gameService.answerChallenge(accessCode, answerChallengeRequest);
		//inform all about validated answers
		template.convertAndSend("/topic/game/" + accessCode, new AnswerChallengeValidationMessage(answerChallengeRequest, answeredCorrectly));
		if(!answeredCorrectly) {
			//wait for next answer
			//TODO Check if maybe all wrong
			return;
		}
		Round round = gameService.fetchRoundByChallengeId(answerChallengeRequest.getChallengeId());
		LocalDateTime roundStarted = round.getCreateDateTime();
		LocalDateTime now = LocalDateTime.now();
		Duration dur = Duration.between(roundStarted, now);
		long millis = dur.toMillis();
		if (millis > ROUND_DURATION_MS) {
			//round time limit exceeded: finish round
			Set<RoundResult> results = gameService.calculateRoundResult(round);
			template.convertAndSend("/topic/game/" + accessCode, new RoundFinishedMessage(results));
			return;
		}
		publishQuizChallenge(accessCode, round);
    }

	@MessageMapping("/game/{accessCode}/allocateSlug")
    public void allocateSlug(@DestinationVariable String accessCode, @Payload AllocateSlugRequest allocateSlugRequest) {
		LOG.info("allocating slug {}", allocateSlugRequest);
		//save allocated slug
		SlugAllocation allocation = gameService.allocateSlug(allocateSlugRequest);
		template.convertAndSend("/topic/game/" + accessCode, new SlugAllocatedMessage(allocation));
		if (gameService.allSlugsAllocated(allocateSlugRequest.getRoundId())) {
			Set<RoundResult> results = gameService.calculateRoundResultAllocated(allocateSlugRequest.getRoundId());
			template.convertAndSend("/topic/game/" + accessCode, new AllSlugsAllocatedMessage(results));
		}
    }

	private void publishQuizChallenge(String accessCode, Round round) {
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(3));
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(2));
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(1));
		QuizChallenge generatedChallenge = gameService.generateChallenge(round);
		template.convertAndSend("/topic/game/" + accessCode, generatedChallenge);
	}





	/*
	@MessageMapping("/game/{game_id}")
    @SendTo("/topic/game/{game_id}")
    public ChatMessage sendMessage(@DestinationVariable String gameId, @Payload ChatMessage chatMessage) {
        return chatMessage;
    }*/

	@MessageMapping("/game")
    @SendTo("/topic/game")
    public String sendMessage(@Payload String chatMessage) {
        return chatMessage;
    }
}
