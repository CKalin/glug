package de.thkoeln.glug.communication.response;

import de.thkoeln.glug.data.Game;

public class CreateGameResponse {
	String action = "GAME_CREATED";
	int gameId;
	String accessCode;

	public CreateGameResponse(Game game) {
		this.gameId = game.getId();
		this.accessCode = game.getAccessCode();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
}
