package de.thkoeln.glug.controller;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.GameRepository;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.PlayerRepository;

@Service
public class GameService {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;

	@Transactional(readOnly=false)
	public Set<Player> addPlayer(String accessCode, int playerId) {
		Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not existing"));
		Game game = gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
		Set<Player> players = game.getPlayers();
		if(players.size() == 0) {
			game.setGamemaster(player);
		}
		players.add(player);
		gameRepository.save(game);
		return players;
	}
}
