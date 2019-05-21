package de.thkoeln.glug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thkoeln.glug.communication.response.CreatePlayerResponse;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;

@RestController()
@RequestMapping("/api/player")
public class PlayerRestController {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;

	@GetMapping("/create")
    public CreatePlayerResponse createGame(@RequestParam(value="name") String name) {
		Player player = new Player();
		player.setName(name);
		playerRepository.save(player);
		return new CreatePlayerResponse(player);
    }

}
