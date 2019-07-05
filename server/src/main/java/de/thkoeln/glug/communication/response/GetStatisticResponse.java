package de.thkoeln.glug.communication.response;

import java.util.ArrayList;
import java.util.List;

public class GetStatisticResponse {
	String action = "GET_STATISTIC";
	List<String> statistics = new ArrayList<String>();

	public GetStatisticResponse() {
		
	}
	
	public void addStatistic(String statistic) {
		statistics.add(statistic);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<String> statistics) {
		this.statistics = statistics;
	}
	
}
