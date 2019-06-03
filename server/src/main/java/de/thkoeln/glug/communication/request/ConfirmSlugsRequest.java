package de.thkoeln.glug.communication.request;

public class ConfirmSlugsRequest {
	String action = "CONFIRM_SLUGS";
	int roundId;
	int playerId;

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
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	@Override
	public String toString() {
		return "ConfirmSlugsRequest [action=" + action + ", roundId=" + roundId + ", playerId=" + playerId + "]";
	}
}
