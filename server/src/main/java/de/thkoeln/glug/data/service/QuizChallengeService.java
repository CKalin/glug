package de.thkoeln.glug.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.thkoeln.glug.data.ChallengeFactory;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.QuizAnswer;
import de.thkoeln.glug.data.QuizChallenge;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.repository.QuizAnswerRepository;
import de.thkoeln.glug.data.repository.QuizChallengeRepository;

@Service
public class QuizChallengeService {
	@Autowired
	QuizChallengeRepository quizChallengeRepository;
	@Autowired
	QuizAnswerRepository quizAnswerRepository;
	@Autowired
	PlayerService playerService;

	@Transactional(readOnly=false)
	public QuizChallenge generateChallenge(Round round) {
		QuizChallenge generatedChallenge = ChallengeFactory.generateRandomChallenge();
		generatedChallenge.setRound(round);
		quizChallengeRepository.save(generatedChallenge);
		return generatedChallenge;
	}

	@Transactional(readOnly=false)
	public synchronized boolean hasAnsweredChallenge(int challengeId, int playerId) {
		Player player = playerService.fetchPlayer(playerId);
		QuizChallenge challenge = fetchChallenge(challengeId);
		List<Player> playersAnswered = new ArrayList<Player>();
		Set<QuizAnswer> challengeAnswers = challenge.getAnswers();
		challengeAnswers.stream().forEach(challengeAnswer -> {
			playersAnswered.addAll(challengeAnswer.getPlayers());
		});
		return playersAnswered.contains(player);
	}

	@Transactional(readOnly=false)
	public synchronized boolean answerChallenge(int challengeId, int playerId, int answerId) {
		QuizChallenge challenge = fetchChallenge(challengeId);
		QuizAnswer answer = fetchAnswer(answerId);
		Player player = playerService.fetchPlayer(playerId);
		player.getAnswers().add(answer);
		answer.getPlayers().add(player);
		quizAnswerRepository.save(answer);
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

	@Transactional(readOnly=false)
	public QuizChallenge fetchChallenge(int challengeId) {
		return quizChallengeRepository.findById(challengeId).orElseThrow(() -> new RuntimeException("QuizChallenge not existing"));
	}

	@Transactional(readOnly=false)
	public QuizAnswer fetchAnswer(int answerId) {
		return quizAnswerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Player not existing"));
	}

	@Transactional(readOnly=false)
	public Round fetchRoundByChallengeId(int challengeId) {
		QuizChallenge challenge = fetchChallenge(challengeId);
		return challenge.getRound();
	}

	public Integer calculateSlugsToSpend(Round round, Player player) {
		List<QuizChallenge> wonChallenges = quizChallengeRepository.findAllByRoundAndWinner(round, player);
		return wonChallenges.size();
	}

	@Transactional(readOnly=false)
	public boolean allPlayersAnswered(int challengeId) {
		QuizChallenge challenge = fetchChallenge(challengeId);
		int countInGame = challenge.getRound().getGame().getPlayers().size();
		List<Player> playersAnswered = new ArrayList<Player>();
		Set<QuizAnswer> challengeAnswers = challenge.getAnswers();
		challengeAnswers.stream().forEach(challengeAnswer -> {
			playersAnswered.addAll(challengeAnswer.getPlayers());
		});
		int countAnswered = playersAnswered.size();
		return countAnswered >= countInGame;
	}
}
