package de.thkoeln.glug.communication;

import de.thkoeln.glug.data.QuizChallenge;

public class NewChallengeMessage {
	String action = "NEW_CHALLENGE";
	QuizChallenge challenge;

	public NewChallengeMessage(QuizChallenge challenge) {
		super();
		this.challenge = challenge;
	}

	public QuizChallenge getChallenge() {
		return challenge;
	}
}
