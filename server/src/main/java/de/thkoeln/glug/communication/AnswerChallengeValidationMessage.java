package de.thkoeln.glug.communication;

public class AnswerChallengeValidationMessage {
	private String action = "CHALLENGE_ANSWERED";
	private int challengeId;
	private int playerId;
	private boolean correct;

	public AnswerChallengeValidationMessage(AnswerChallengeRequest answerChallengeRequest, boolean answeredCorrectly) {
		this.challengeId = answerChallengeRequest.getChallengeId();
		this.playerId = answerChallengeRequest.getPlayerId();
		this.correct = answeredCorrectly;
	}

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

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


}
