package de.thkoeln.glug.data.repository;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.Game;

public interface RoundRepository extends CrudRepository<Round, Integer> {
	Iterable<Round> findByGame(Game game);
}
