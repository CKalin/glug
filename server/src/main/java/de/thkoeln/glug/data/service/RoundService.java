package de.thkoeln.glug.data.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;

@Service
public class RoundService {
	@Autowired
	RoundRepository roundRepository;
	@Autowired
	RoundResultRepository roundResultRepository;
	@Autowired
	SlugAllocationRepository slugAllocationRepository;
	@Autowired
	QuizChallengeService quizChallengeService;

	@Transactional(readOnly=false)
	public Round startRound(Game game) {
		Round round = new Round();
		round.setGame(game);
		roundRepository.save(round);
		return round;
	}

	@Transactional(readOnly=false)
	public Round fetchRound(int roundId) {
		return roundRepository.findById(roundId).orElseThrow(() -> new RuntimeException("Round not existing"));
	}

	@Transactional(readOnly=false)
	public Set<RoundResult> calculateRoundResult(int roundId) {
		Round round = fetchRound(roundId);
		Set<RoundResult> results = new HashSet<RoundResult>();
		round.getGame().getPlayers().forEach(player -> {
			RoundResult playerResult = new RoundResult();
			playerResult.setPlayer(player);
			playerResult.setRound(round);
			playerResult.setSlugCountToSpend(quizChallengeService.calculateSlugsToSpend(round, player));
			playerResult.setSlugCountRecieved(0);
			playerResult.setRound(round);
			results.add(playerResult);
			roundResultRepository.save(playerResult);
		});
		return results;
	}

	@Transactional(readOnly=false)
	public Set<RoundResult> calculateRoundResultAllocated(int roundId) {
		Round round = fetchRound(roundId);
		round.getRoundResults().forEach(roundResult -> {
			roundResult.setSlugCountRecieved(calculateRecievedSlugs(round, roundResult.getPlayer()));
			roundResultRepository.save(roundResult);
		});
		Round finishedRound = fetchRound(roundId);
		return finishedRound.getRoundResults();
	}

	private Integer calculateRecievedSlugs(Round round, Player player) {
		List<SlugAllocation> recievedSlugs = slugAllocationRepository.findAllByRoundAndToPlayer(round, player);
		return recievedSlugs.size();
	}

	@Transactional(readOnly=false)
	public SlugAllocation allocateSlug(Player fromPlayer, Player toPlayer, Round round) {
		SlugAllocation allocation = new SlugAllocation();
		allocation.setFromPlayer(fromPlayer);
		allocation.setToPlayer(toPlayer);
		allocation.setRound(round);
		slugAllocationRepository.save(allocation);
		return allocation;
	}

	@Transactional(readOnly=false)
	public boolean allSlugsAllocated(int roundId) {
		Round round = fetchRound(roundId);
		return round.getQuizChallenges().size() <= round.getSlugAllocations().size();
	}
}
