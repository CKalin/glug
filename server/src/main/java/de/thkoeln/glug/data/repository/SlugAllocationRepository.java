
package de.thkoeln.glug.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.SlugAllocation;

public interface SlugAllocationRepository extends CrudRepository<SlugAllocation, Integer> {
	List<SlugAllocation> findAllByRoundAndToPlayer(Round roundId, Player playerId);
	}
