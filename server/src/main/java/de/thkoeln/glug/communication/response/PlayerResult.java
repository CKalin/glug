package de.thkoeln.glug.communication.response;

public class PlayerResult {
	private int playerId;
	private int slugCountToAllocate;
	private int slugCountRecieved;

	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getSlugCountToAllocate() {
		return slugCountToAllocate;
	}
	public void setSlugCountToAllocate(int slugCountToAllocate) {
		this.slugCountToAllocate = slugCountToAllocate;
	}
	public int getSlugCountRecieved() {
		return slugCountRecieved;
	}
	public void setSlugCountRecieved(int slugCountRecieved) {
		this.slugCountRecieved = slugCountRecieved;
	}

}
