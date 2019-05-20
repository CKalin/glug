package de.thkoeln.glug.communication;

import java.util.Set;

import de.thkoeln.glug.data.RoundResult;

public class RoundFinishedMessage {
	String action = "ROUND_FINISHED";
	Set<RoundResult> results;

	public RoundFinishedMessage(Set<RoundResult> results) {
		this.results = results;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Set<RoundResult> getResults() {
		return results;
	}
	public void setResults(Set<RoundResult> results) {
		this.results = results;
	}
}
