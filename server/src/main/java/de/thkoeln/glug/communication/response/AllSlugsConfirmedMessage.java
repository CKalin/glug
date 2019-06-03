package de.thkoeln.glug.communication.response;

public class AllSlugsConfirmedMessage {
	String action = "ALL_SLUGS_CONFIRMED";
	int roundId;

	public AllSlugsConfirmedMessage(int roundId) {
		this.roundId = roundId;
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
}
