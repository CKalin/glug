
package de.thkoeln.glug.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.communication.response.SlugsAllocatedStatistic;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.SlugAllocation;

public interface SlugAllocationRepository extends CrudRepository<SlugAllocation, Integer> {
	List<SlugAllocation> findAllByRoundAndToPlayer(Round roundId, Player playerId);
	List<SlugAllocation> findAllByRoundIn(List<Round> round);

	@Query("SELECT " +
	           "    new de.thkoeln.glug.communication.response.SlugsAllocatedStatistic(s.fromPlayer, s.toPlayer, COUNT(s.toPlayer)) " +
	           "FROM " +
	           "    SlugAllocation s " +
	           "WHERE " +
	           "		s.round = ?1 " +
	           "GROUP BY " +
	           "    s.fromPlayer, s.toPlayer")
	List<SlugsAllocatedStatistic> findAllByRoundGroupByFromPlayerAndToPlayer(Round round);	
	}
