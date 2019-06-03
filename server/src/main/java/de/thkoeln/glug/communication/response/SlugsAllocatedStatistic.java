package de.thkoeln.glug.communication.response;

import de.thkoeln.glug.data.Player;

public class SlugsAllocatedStatistic {
	private int fromPlayerId;
	private int toPlayerId;
	private long slugCountAllocated;

	public SlugsAllocatedStatistic(Player fromPlayer, Player toPlayer, long slugCountAllocated) {
		super();
		this.fromPlayerId = fromPlayer.getId();
		this.toPlayerId = toPlayer.getId();
		this.slugCountAllocated = slugCountAllocated;
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
	public long getSlugCountAllocated() {
		return slugCountAllocated;
	}
	public void setSlugCountAllocated(long slugCountAllocated) {
		this.slugCountAllocated = slugCountAllocated;
	}
}
