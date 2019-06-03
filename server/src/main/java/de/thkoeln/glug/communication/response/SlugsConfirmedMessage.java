package de.thkoeln.glug.communication.response;

public class SlugsConfirmedMessage {
	String action = "SLUGS_CONFIRMED";
	int roundId;
	int playerId;

	public SlugsConfirmedMessage(int roundId, int playerId) {
		super();
		this.roundId = roundId;
		this.playerId = playerId;
	}
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
}
