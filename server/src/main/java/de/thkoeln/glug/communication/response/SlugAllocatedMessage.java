package de.thkoeln.glug.communication.response;

import de.thkoeln.glug.data.SlugAllocation;

public class SlugAllocatedMessage {
	private String action = "SLUG_ALLOCATED";
	int roundId;
	int fromPlayerId;
	int toPlayerId;

	public SlugAllocatedMessage(SlugAllocation allocation) {
		this.roundId = allocation.getRound().getId();
		this.fromPlayerId = allocation.getFromPlayer().getId();
		this.toPlayerId = allocation.getToPlayer().getId();
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
}
