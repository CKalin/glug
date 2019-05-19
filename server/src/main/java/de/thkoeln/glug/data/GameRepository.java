package de.thkoeln.glug.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
	Optional<Game> findOneByAccessCode(String accessCode);
}
