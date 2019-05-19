package de.thkoeln.glug.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.GameRepository;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.PlayerRepository;

@RestController()
@RequestMapping("/api/game")
public class GameRestController {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;

	@GetMapping("/create")
    public String createGame() {
		Game game = new Game();
		String accessCode = String.valueOf((int) Math.floor(Math.random()*100000+10000)%100000);
		game.setAccessCode(accessCode);
		gameRepository.save(game);
		return accessCode;
    }

	@GetMapping("/isPresent")
    public boolean validateGame(@RequestParam(value="accessCode") String accessCode) {
		return gameRepository.findOneByAccessCode(accessCode).isPresent();
    }

}
