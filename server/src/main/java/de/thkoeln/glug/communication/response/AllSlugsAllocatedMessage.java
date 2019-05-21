package de.thkoeln.glug.communication.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.thkoeln.glug.data.RoundResult;

public class AllSlugsAllocatedMessage {
	String action = "ALL_SLUGS_ALLOCATED";
	int roundId;
	List<PlayerResult> results = new ArrayList<PlayerResult>();

	public AllSlugsAllocatedMessage(Set<RoundResult> results) {
		results.forEach(result -> {
			PlayerResult playerResult = new PlayerResult();
			playerResult.setPlayerId(result.getPlayer().getId());
			playerResult.setSlugCountToAllocate(result.getSlugCountToSpend());
			playerResult.setSlugCountRecieved(result.getSlugCountRecieved());
			this.results.add(playerResult);
			roundId = result.getRound().getId();
		});
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
	public List<PlayerResult> getResults() {
		return results;
	}
	public void setResults(List<PlayerResult> results) {
		this.results = results;
	}
}
