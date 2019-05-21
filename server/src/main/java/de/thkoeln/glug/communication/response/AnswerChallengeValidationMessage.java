package de.thkoeln.glug.communication.response;

import de.thkoeln.glug.communication.request.AnswerChallengeRequest;

public class AnswerChallengeValidationMessage {
	private String action = "CHALLENGE_ANSWERED";
	private int playerId;
	private boolean correct;

	public AnswerChallengeValidationMessage(AnswerChallengeRequest answerChallengeRequest, boolean answeredCorrectly) {
		this.playerId = answerChallengeRequest.getPlayerId();
		this.correct = answeredCorrectly;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


}
