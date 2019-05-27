package de.thkoeln.glug.data.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	GameRepository gameRepository;

	@Transactional(readOnly=false)
	public Set<Player> addPlayer(String accessCode, Player player) {
		Game game = fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		if(players.size() == 0) {
			game.setGamemaster(player);
		}
		players.add(player);
		gameRepository.save(game);
		return players;
	}

	@Transactional(readOnly=false)
	public Player getGamemaster(String accessCode) {
		return fetchGame(accessCode).getGamemaster();
	}

	public Game fetchGame(String accessCode) {
		return gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
	}
}
