package de.thkoeln.glug.communication.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.thkoeln.glug.data.RoundResult;

public class AllSlugsAllocatedStatisticsMessage {
	String action = "ALL_SLUGS_ALLOCATED_STATISTICS";
	int roundId;
	List<SlugsAllocatedStatistic> statistics = new ArrayList<SlugsAllocatedStatistic>();

	public AllSlugsAllocatedStatisticsMessage(List<SlugsAllocatedStatistic> statistics) {
		this.statistics = statistics;
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
	public List<SlugsAllocatedStatistic> getStatistics() {
		return statistics;
	}
	public void setStatistics(List<SlugsAllocatedStatistic> statistics) {
		this.statistics = statistics;
	}
	@Override
	public String toString() {
		return "AllSlugsAllocatedStatisticsMessage [action=" + action + ", roundId=" + roundId + ", statistics="
				+ statistics + "]";
	}
}
