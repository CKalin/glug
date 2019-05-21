package de.thkoeln.glug.communication.response;

public class PlayerResult {
	private int playerId;
	private int slugCountToAllocate;
	private int slugCountReceived;

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
	public int getSlugCountReceived() {
		return slugCountReceived;
	}
	public void setSlugCountReceived(int slugCountReceived) {
		this.slugCountReceived = slugCountReceived;
	}

}
