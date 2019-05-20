package de.thkoeln.glug.communication;

import de.thkoeln.glug.data.QuizChallenge;

public class AnswerChallengeRequest {
	String action = "ANSWER_CHALLENGE";
	int challengeId;
	int playerId;
	int answerId;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	@Override
	public String toString() {
		return "AnswerChallengeRequest [action=" + action + ", challengeId=" + challengeId + ", playerId=" + playerId
				+ ", answerId=" + answerId + "]";
	}

}
