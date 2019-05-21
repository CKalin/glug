package de.thkoeln.glug.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.communication.request.AllocateSlugRequest;
import de.thkoeln.glug.communication.request.AnswerChallengeRequest;
import de.thkoeln.glug.data.ChallengeFactory;
import de.thkoeln.glug.data.Game;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizAnswer;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.RoundResult;
import de.thkoeln.glug.data.SlugAllocation;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.QuizAnswerRepository;
import de.thkoeln.glug.data.repository.QuizChallengeRepository;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;

@Service
public class GameService {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;
	@Autowired
	RoundRepository roundRepository;
	@Autowired
	RoundResultRepository roundResultRepository;
	@Autowired
	QuizChallengeRepository quizChallengeRepository;
	@Autowired
	QuizAnswerRepository quizAnswerRepository;
	@Autowired
	SlugAllocationRepository slugAllocationRepository;

	@Transactional(readOnly=false)
	public Set<Player> addPlayer(String accessCode, int playerId) {
		Player player = fetchPlayer(playerId);
		Game game = fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		if(players.size() == 0) {
			game.setGamemaster(player);
		}
		players.add(player);
		gameRepository.save(game);
		return players;
	}

	@Transactional(readOnly=false)
	public Player getGamemaster(String accessCode) {
		return fetchGame(accessCode).getGamemaster();
	}

	@Transactional(readOnly=false)
	public Round startRound(String accessCode) {
		Game game = fetchGame(accessCode);
		Round round = new Round();
		round.setGame(game);
		roundRepository.save(round);
		return round;
	}

	@Transactional(readOnly=false)
	public QuizChallenge generateChallenge(Round round) {
		QuizChallenge generatedChallenge = ChallengeFactory.generateRandomChallenge();
		generatedChallenge.setRound(round);
		quizChallengeRepository.save(generatedChallenge);
		return generatedChallenge;
	}

	@Transactional(readOnly=false)
	public boolean answerChallenge(String accessCode, AnswerChallengeRequest answerChallengeRequest) {
		Player player = fetchPlayer(answerChallengeRequest.getPlayerId());
		QuizChallenge challenge = fetchChallenge(answerChallengeRequest.getChallengeId());
		QuizAnswer answer = fetchAnswer(answerChallengeRequest.getAnswerId());
		Player winner = challenge.getWinner();
		boolean answerCorrect = answer.getCorrect();
		boolean hasWinner = winner != null;
		if (!answerCorrect || hasWinner) {
			return false;
		}
		challenge.setWinner(player);
		quizChallengeRepository.save(challenge);
		return true;
	}

	private Game fetchGame(String accessCode) {
		return gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
	}

	public Round fetchRound(int roundId) {
		return roundRepository.findById(roundId).orElseThrow(() -> new RuntimeException("Round not existing"));
	}

	public Player fetchPlayer(int playerId) {
		return playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not existing"));
	}

	public QuizChallenge fetchChallenge(int challengeId) {
		return quizChallengeRepository.findById(challengeId).orElseThrow(() -> new RuntimeException("QuizChallenge not existing"));
	}

	private QuizAnswer fetchAnswer(int answerId) {
		return quizAnswerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Player not existing"));
	}

	@Transactional(readOnly=false)
	public Round fetchRoundByChallengeId(int challengeId) {
		QuizChallenge challenge = fetchChallenge(challengeId);
		return challenge.getRound();
	}

	@Transactional(readOnly=false)
	public Set<RoundResult> calculateRoundResult(int roundId) {
		Round round = fetchRound(roundId);
		Set<RoundResult> results = new HashSet<RoundResult>();
		round.getGame().getPlayers().forEach(player -> {
			RoundResult playerResult = new RoundResult();
			playerResult.setPlayer(player);
			playerResult.setRound(round);
			playerResult.setSlugCountToSpend(calculateSlugsToSpend(round, player));
			playerResult.setSlugCountRecieved(0);
			playerResult.setRound(round);
			results.add(playerResult);
			roundResultRepository.save(playerResult);
		});
		return results;
	}

	private Integer calculateSlugsToSpend(Round round, Player player) {
		List<QuizChallenge> wonChallenges = quizChallengeRepository.findAllByRoundAndWinner(round, player);
		return wonChallenges.size();
	}

	@Transactional(readOnly=false)
	public SlugAllocation allocateSlug(AllocateSlugRequest allocateSlugRequest) {
		SlugAllocation allocation = new SlugAllocation();
		allocation.setFromPlayer(fetchPlayer(allocateSlugRequest.getFromPlayerId()));
		allocation.setToPlayer(fetchPlayer(allocateSlugRequest.getToPlayerId()));
		allocation.setRound(fetchRound(allocateSlugRequest.getRoundId()));
		slugAllocationRepository.save(allocation);
		return allocation;
	}

	@Transactional(readOnly=false)
	public boolean allSlugsAllocated(int roundId) {
		Round round = fetchRound(roundId);
		return round.getQuizChallenges().size() <= round.getSlugAllocations().size();
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


}
