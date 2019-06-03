package de.thkoeln.glug.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;

public interface RoundResultRepository extends CrudRepository<RoundResult, Integer> {
	Optional<RoundResult> findOneByRoundAndPlayer(Round round, Player player);
	List<RoundResult> findByRoundAndSlugsConfirmed(Round round, boolean slugsConfirmed);
}
