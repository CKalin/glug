package de.thkoeln.glug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thkoeln.glug.communication.response.GetStatisticResponse;
import de.thkoeln.glug.service.StatisticService;

@RestController()
@RequestMapping("/api/statistic")
public class StatisticRestController {
	
	@Autowired
	StatisticService statisticService;
	
	@GetMapping("")
    public GetStatisticResponse getStatistic(@RequestParam(value="accessCode") String accessCode) {
		GetStatisticResponse statistic = new GetStatisticResponse();
		
		statistic.addStatistic(statisticService.getMostSlugsAllocated(accessCode));
		statistic.addStatistic(statisticService.getLeastSlugsAllocated(accessCode));
		statistic.addStatistic(statisticService.getMostSlugsReceived(accessCode));
		statistic.addStatistic(statisticService.getLeastSlugsReceived(accessCode));
		
		return statistic;
		
    }

}
