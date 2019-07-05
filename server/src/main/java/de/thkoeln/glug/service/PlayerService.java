package de.thkoeln.glug.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.PlayerRepository;

@Service
public class PlayerService {
	@Autowired
	PlayerRepository playerRepository;

	public Player fetchPlayer(int playerId) {
		return playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not existing"));
	}

}
