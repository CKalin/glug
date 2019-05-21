package de.thkoeln.glug.communication.response;

import de.thkoeln.glug.data.Player;

public class CreatePlayerResponse {
	String action = "PLAYER_CREATED";
	int playerId;
	String name;

	public CreatePlayerResponse(Player player) {
		this.playerId = player.getId();
		this.name = player.getName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
