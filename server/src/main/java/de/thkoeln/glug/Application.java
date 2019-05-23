package de.thkoeln.glug;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.thkoeln.glug.communication.response.NewChallengeMessage;
import de.thkoeln.glug.controller.GameController;
import de.thkoeln.glug.data.ChallengeFactory;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.Statistic;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;
import de.thkoeln.glug.data.repository.GameRepository;

@SpringBootApplication
public class Application {
	Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	PlayerRepository playerRepository;
	GameRepository gameRepository;
	SlugAllocationRepository slugAllocationRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	private void initDatabase() {
		
		/*
		Player helmutchecker = new Player("Player1");
		helmutchecker.setName("Helmutchecker");
		playerRepository.save(helmutchecker);
		
		Player Harry = new Player("Player2");
		Harry.setName("Harry");
		playerRepository.save(Harry);
		
		
		
		Game gametest = new Game();
		gameRepository.save(gametest);
		Set<Player> players = null;
		players.add(helmutchecker);
		players.add(Harry);
		gametest.setPlayers(players);
		Round roundtest  = new Round();
		roundtest.setGame(gametest);
		Set<Round> rounds = null; 
		rounds.add(roundtest);
		gametest.setRounds(rounds);
		
		Iterable<SlugAllocation> slugs  = slugAllocationRepository.findAll();
		for (SlugAllocation slugAllocation : slugs) {
		String slug = slugs.toString();
		}
		
		
		/*
		Player kaema7 = new Player("Player2");
		kaema7.setName("kaema7");
		playerRepository.save(kaema7);

		getAllPlayers().forEach(player -> {
			logger.info("This is player " + player.getName() + " with id " + player.getId());
		});

		getPlayersWithName("Helmutchecker").forEach(player -> {
			logger.info("Found player with name " + player.getName() + " with id " + player.getId());
		});
		QuizChallenge testChallenge = ChallengeFactory.generateRandomChallenge();
		QuizChallenge testChallenge2 = ChallengeFactory.generateRandomChallenge();*/
		
	// 	Statistic stats = new Statistic(players., gametest);
		
	}

	public Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	public Iterable<Player> getPlayersWithName(String name) {
		return playerRepository.findByName(name);
	}
}
