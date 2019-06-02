package de.thkoeln.glug.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import de.thkoeln.glug.communication.request.AllocateSlugRequest;
import de.thkoeln.glug.communication.request.AnswerChallengeRequest;
import de.thkoeln.glug.communication.request.JoinGameRequest;
import de.thkoeln.glug.communication.response.AllSlugsAllocatedMessage;
import de.thkoeln.glug.communication.response.AnswerChallengeValidationMessage;
import de.thkoeln.glug.communication.response.CountdownMessage;
import de.thkoeln.glug.communication.response.NewChallengeMessage;
import de.thkoeln.glug.communication.response.PlayerBean;
import de.thkoeln.glug.communication.response.PlayerJoinedMessage;
import de.thkoeln.glug.communication.response.RoundFinishedMessage;
import de.thkoeln.glug.communication.response.SlugAllocatedMessage;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizAnswer;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.service.GameService;
import de.thkoeln.glug.data.service.PlayerService;
import de.thkoeln.glug.data.service.QuizChallengeService;
import de.thkoeln.glug.data.service.RoundService;

@Controller
public class GameController {
	final static Logger LOG = LoggerFactory.getLogger(GameController.class);
	private final static long ROUND_DURATION_MS = 0; //60000;
	@Autowired
    private SimpMessagingTemplate template;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;

	@Autowired
	GameService gameService;
	@Autowired
	QuizChallengeService quizChallengeService;
	@Autowired
	PlayerService playerService;
	@Autowired
	RoundService roundService;

	@MessageMapping("/game/{accessCode}/player")
    @SendTo("/topic/game/{accessCode}")
    public PlayerJoinedMessage joinGame(@DestinationVariable String accessCode, @Payload JoinGameRequest joinGameRequest) {
		LOG.info("new player {} for game {}", joinGameRequest.getPlayerId(), accessCode);
		Player joinedPlayer = playerService.fetchPlayer(joinGameRequest.getPlayerId());
		Set<Player> players = gameService.addPlayer(accessCode, joinedPlayer);
		Player gamemaster = gameService.getGamemaster(accessCode);
		Set<PlayerBean> inGamePlayers = new HashSet<PlayerBean>();
		players.forEach(player -> {
			inGamePlayers.add(new PlayerBean(player.getId(), player.getName(), player.getId().equals(gamemaster.getId())));
		});
		joinedPlayer = playerService.fetchPlayer(joinGameRequest.getPlayerId());
		PlayerBean joinedPlayerBean = new PlayerBean(joinedPlayer.getId(), joinedPlayer.getName(),
				joinedPlayer.getId().equals(gamemaster.getId()));
        return new PlayerJoinedMessage(joinedPlayerBean, inGamePlayers);
    }

	@MessageMapping("/game/{accessCode}/start")
    public void startGame(@DestinationVariable String accessCode) {
		LOG.info("starting round for game {}", accessCode);
		Game game = gameService.fetchGame(accessCode);
		Round round = roundService.startRound(game);
		publishQuizChallenge(accessCode, round);
    }

	@MessageMapping("/game/{accessCode}/answer")
    public void answerChallenge(@DestinationVariable String accessCode, @Payload AnswerChallengeRequest answerChallengeRequest) {
		LOG.info("new answer in game {}: {}", accessCode, answerChallengeRequest);
		//validate challenge answer
		QuizChallenge challenge = quizChallengeService.fetchChallenge(answerChallengeRequest.getChallengeId());
		Player player = playerService.fetchPlayer(answerChallengeRequest.getPlayerId());
		QuizAnswer answer = quizChallengeService.fetchAnswer(answerChallengeRequest.getAnswerId());
		boolean answeredCorrectly = quizChallengeService.answerChallenge(challenge, player, answer);
		//inform all about validated answers
		template.convertAndSend("/topic/game/" + accessCode, new AnswerChallengeValidationMessage(answerChallengeRequest, answeredCorrectly));
		if(!answeredCorrectly) {
			//wait for next answer
			//TODO Check if maybe all wrong
			return;
		}
		Round round = quizChallengeService.fetchRoundByChallengeId(answerChallengeRequest.getChallengeId());
		LocalDateTime roundStarted = round.getCreateDateTime();
		LocalDateTime now = LocalDateTime.now();
		Duration dur = Duration.between(roundStarted, now);
		long millis = dur.toMillis();
		if (millis > ROUND_DURATION_MS) {
			//round time limit exceeded: finish round
			Set<RoundResult> results = roundService.calculateRoundResult(round.getId());
			template.convertAndSend("/topic/game/" + accessCode, new RoundFinishedMessage(results));
			return;
		}
		publishQuizChallenge(accessCode, round);
    }

	@MessageMapping("/game/{accessCode}/allocateSlug")
    public void allocateSlug(@DestinationVariable String accessCode, @Payload AllocateSlugRequest allocateSlugRequest) {
		LOG.info("allocating slug {}", allocateSlugRequest);
		//save allocated slug
		Round round = roundService.fetchRound(allocateSlugRequest.getRoundId());
		Player fromPlayer = playerService.fetchPlayer(allocateSlugRequest.getFromPlayerId());
		Player toPlayer = playerService.fetchPlayer(allocateSlugRequest.getToPlayerId());
		SlugAllocation allocation = roundService.allocateSlug(fromPlayer, toPlayer, round);
		template.convertAndSend("/topic/game/" + accessCode, new SlugAllocatedMessage(allocation));
		if (roundService.allSlugsAllocated(allocateSlugRequest.getRoundId())) {
			Set<RoundResult> results = roundService.calculateRoundResultAllocated(allocateSlugRequest.getRoundId());
			template.convertAndSend("/topic/game/" + accessCode, new AllSlugsAllocatedMessage(results));
		}
    }

	private void publishQuizChallenge(String accessCode, Round round) {
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(3));
		sleep(1000);
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(2));
		sleep(1000);
		template.convertAndSend("/topic/game/" + accessCode, new CountdownMessage(1));
		sleep(1000);
		QuizChallenge generatedChallenge = quizChallengeService.generateChallenge(round);
		template.convertAndSend("/topic/game/" + accessCode, new NewChallengeMessage(generatedChallenge));
	}





	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
