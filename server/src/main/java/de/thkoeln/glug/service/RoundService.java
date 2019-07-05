package de.thkoeln.glug.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.communication.response.SlugsAllocatedStatistic;
import de.thkoeln.glug.controller.GameController;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;

@Service
public class RoundService {
	final static Logger LOG = LoggerFactory.getLogger(RoundService.class);
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
			playerResult.setSlugsConfirmed(false);
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
		Set<QuizChallenge> challenges = round.getQuizChallenges();
		long correctlyAnsweredCount = challenges.stream().filter(challenge -> challenge.getWinner() != null).collect(Collectors.counting());
		int slugsAllocatedCount = round.getSlugAllocations().size();
		LOG.debug("correctly answered: {} | clugs allocated: {}", correctlyAnsweredCount, slugsAllocatedCount);
		return correctlyAnsweredCount <= slugsAllocatedCount;
	}

	@Transactional(readOnly=false)
	public List<SlugsAllocatedStatistic> fetchSlugsAllocatedStatistics(int roundId) {
		Round round = fetchRound(roundId);
		return slugAllocationRepository.findAllByRoundGroupByFromPlayerAndToPlayer(round);
	}

	@Transactional(readOnly=false)
	public void confirmSlug(Player player, Round round) {
		RoundResult playerResult = roundResultRepository.findOneByRoundAndPlayer(round, player).orElseThrow(() -> new RuntimeException("RoundResult not existing"));
		playerResult.setSlugsConfirmed(true);
		roundResultRepository.save(playerResult);
	}

	@Transactional(readOnly=false)
	public boolean allSlugsConfirmed(Round round) {
		List<RoundResult> playerResults = roundResultRepository.findByRoundAndSlugsConfirmed(round, false);
		return playerResults.size() == 0;
	}
}
