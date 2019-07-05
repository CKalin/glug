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
}
