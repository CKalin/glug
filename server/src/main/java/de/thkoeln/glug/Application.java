package de.thkoeln.glug;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.thkoeln.glug.data.ChallengeFactory;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.repository.PlayerRepository;

@SpringBootApplication
public class Application {
	Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	private void initDatabase() {
		Player helmutchecker = new Player("Player1");
		helmutchecker.setName("Helmutchecker");
		playerRepository.save(helmutchecker);

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
		QuizChallenge testChallenge2 = ChallengeFactory.generateRandomChallenge();
	}

	public Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	public Iterable<Player> getPlayersWithName(String name) {
		return playerRepository.findByName(name);
	}
}
