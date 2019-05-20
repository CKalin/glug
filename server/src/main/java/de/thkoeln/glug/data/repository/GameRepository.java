package de.thkoeln.glug.data.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
	Optional<Game> findOneByAccessCode(String accessCode);
}
