package de.thkoeln.glug.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
public class Scheduler {
	final static Logger LOG = LoggerFactory.getLogger(GameController.class);

	@Autowired
    private SimpMessagingTemplate template;

	@Scheduled(fixedRate=3000)
    public void sendMessage() {
		System.out.println("test");
		LOG.info("Scheduler running");
		template.convertAndSend("/topic/game", "TEST");
    }

}
