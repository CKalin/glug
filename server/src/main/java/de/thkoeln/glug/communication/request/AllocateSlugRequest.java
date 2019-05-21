package de.thkoeln.glug.communication.request;

import de.thkoeln.glug.data.QuizChallenge;

public class AllocateSlugRequest {
	String action = "ALLOCATE_SLUG";
	int roundId;
	int fromPlayerId;
	int toPlayerId;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getFromPlayerId() {
		return fromPlayerId;
	}
	public void setFromPlayerId(int fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}
	public int getToPlayerId() {
		return toPlayerId;
	}
	public void setToPlayerId(int toPlayerId) {
		this.toPlayerId = toPlayerId;
	}
	@Override
	public String toString() {
		return "AllocateSlugRequest [action=" + action + ", roundId=" + roundId + ", fromPlayerId=" + fromPlayerId
				+ ", toPlayerId=" + toPlayerId + "]";
	}


}
