package de.thkoeln.glug.communication.request;

public class JoinGameRequest {
	String action = "JOIN_GAME";
	int playerId;

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
}
