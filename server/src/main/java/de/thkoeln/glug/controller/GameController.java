package de.thkoeln.glug.controller;

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
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.GameRepository;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.PlayerRepository;

@Controller
public class GameController {
	final static Logger LOG = LoggerFactory.getLogger(GameController.class);
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
		Game game = gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
		Set<PlayerResponse> playersResponse = new HashSet<PlayerResponse>();
		players.forEach(player -> {
			playersResponse.add(new PlayerResponse(player.getId(), player.getName(), player.getId() == game.getGamemaster().getId()));
		});

        return playersResponse;
    }

	@MessageMapping("/game/{gameId}/start")
    @SendTo("/topic/game/{gameId}/players")
    public void startGame(@DestinationVariable String gameId) {
		LOG.info("starting game {}", gameId);

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
