package de.thkoeln.glug;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.thkoeln.glug.communication.response.AllSlugsAllocatedStatisticsMessage;
import de.thkoeln.glug.service.RoundService;

@SpringBootApplication
public class Application {

	@Autowired
	RoundService roundService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void test() throws InterruptedException {
		System.out.println("hsadfjsnidjfnwjrbfivwov");
		AllSlugsAllocatedStatisticsMessage statistics = new AllSlugsAllocatedStatisticsMessage(roundService.fetchSlugsAllocatedStatistics(595));
		System.out.println(statistics);
	}
}
