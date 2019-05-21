package de.thkoeln.glug.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;

public interface QuizChallengeRepository extends CrudRepository<QuizChallenge, Integer> {
	List<QuizChallenge> findAllByRoundAndWinner(Round roundId, Player winnerId);
}
