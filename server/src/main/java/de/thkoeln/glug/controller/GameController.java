package de.thkoeln.glug.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
	final static Logger LOG = LoggerFactory.getLogger(GameController.class);

	/*
	@MessageMapping("/game/{game_id}")
    @SendTo("/topic/game/{game_id}")
    public ChatMessage sendMessage(@DestinationVariable String gameId, @Payload ChatMessage chatMessage) {
        return chatMessage;
    }*/

	@MessageMapping("/game")
    @SendTo("/topic/game")
    public String sendMessage(@Payload String chatMessage) {
		LOG.info(chatMessage);
        return chatMessage;
    }
}
