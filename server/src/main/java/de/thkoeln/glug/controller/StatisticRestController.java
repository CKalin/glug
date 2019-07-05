package de.thkoeln.glug.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thkoeln.glug.communication.response.GetStatisticResponse;

@RestController()
@RequestMapping("/api/statistic")
public class StatisticRestController {
	
	@GetMapping("")
    public GetStatisticResponse getStatistic(@RequestParam(value="gameId") String gameId) {
		GetStatisticResponse statistic = new GetStatisticResponse();
		
		return statistic;
		
    }

}
