package de.thkoeln.glug.service;

import de.thkoeln.glug.data.Game;
//import de.thkoeln.glug.controller.GameService;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.SlugAllocation;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StatisticService {
	@Autowired
	GameService gameService;
	@Autowired
	RoundService roundService;
	
	public StatisticService() {
		
	}
	
	@Transactional(readOnly=false)
	public String getMostSlugsAllocated(String accessCode) {	
		Game game = gameService.fetchGame(accessCode);
		List<SlugAllocation> slugAllocationsInGame = roundService.getSlugAllocationsForRounds(new ArrayList(game.getRounds()));
		Map<Player, Integer> allocatedSlugs = new HashMap<Player, Integer>();
		slugAllocationsInGame.forEach(slugAllocation -> {
			if(!allocatedSlugs.containsKey(slugAllocation.getFromPlayer())) {
				allocatedSlugs.put(slugAllocation.getFromPlayer(), 0);
			}
			int newCount = allocatedSlugs.get(slugAllocation.getFromPlayer()) + 1;
			allocatedSlugs.put(slugAllocation.getFromPlayer(), newCount);
		});
		Map.Entry<Player, Integer> mostAllocated = null;
		for (Map.Entry<Player, Integer> allocated : allocatedSlugs.entrySet()) {
	        if (mostAllocated == null || allocated.getValue()
	            .compareTo(mostAllocated.getValue()) > 0) {
	        	mostAllocated = allocated;
	        }
	    }
		
		StringBuilder mostAllocatedBuilder = new StringBuilder();
		mostAllocatedBuilder.append("Der Teufel: ");
		mostAllocatedBuilder.append(mostAllocated.getKey().getName());
		mostAllocatedBuilder.append(" hat ganze ");
		mostAllocatedBuilder.append(mostAllocated.getValue());
		mostAllocatedBuilder.append(" Schlücke verteilt.");
		return mostAllocatedBuilder.toString();
	}
	
	@Transactional(readOnly=false)
	public String getLeastSlugsAllocated(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		List<SlugAllocation> slugAllocationsInGame = roundService.getSlugAllocationsForRounds(new ArrayList(game.getRounds()));
		Map<Player, Integer> allocatedSlugs = new HashMap<Player, Integer>();
		slugAllocationsInGame.forEach(slugAllocation -> {
			if(!allocatedSlugs.containsKey(slugAllocation.getFromPlayer())) {
				allocatedSlugs.put(slugAllocation.getFromPlayer(), 0);
			}
			int newCount = allocatedSlugs.get(slugAllocation.getFromPlayer()) + 1;
			allocatedSlugs.put(slugAllocation.getFromPlayer(), newCount);
		});
		Map.Entry<Player, Integer> leastAllocatedEntry = null;
		for (Map.Entry<Player, Integer> allocated : allocatedSlugs.entrySet()) {
	        if (leastAllocatedEntry == null || allocated.getValue()
	            .compareTo(leastAllocatedEntry.getValue()) < 0) {
	        	leastAllocatedEntry = allocated;
	        }
	    }
		
		StringBuilder leastAllocated = new StringBuilder();
		leastAllocated.append("Der Verlier: ");
		leastAllocated.append(leastAllocatedEntry.getKey().getName());
		leastAllocated.append(" hat nur ");
		leastAllocated.append(leastAllocatedEntry.getValue());
		leastAllocated.append(" Schlücke verteilt.");
		return leastAllocated.toString();
		
	}
	
	@Transactional(readOnly=false)
	public String getLeastSlugsReceived(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		List<SlugAllocation> slugAllocationsInGame = roundService.getSlugAllocationsForRounds(new ArrayList(game.getRounds()));
		Map<Player, Integer> recievedSlugs = new HashMap<Player, Integer>();
		slugAllocationsInGame.forEach(slugAllocation -> {
			if(!recievedSlugs.containsKey(slugAllocation.getToPlayer())) {
				recievedSlugs.put(slugAllocation.getToPlayer(), 0);
			}
			int newCount = recievedSlugs.get(slugAllocation.getToPlayer()) + 1;
			recievedSlugs.put(slugAllocation.getToPlayer(), newCount);
		});
		Map.Entry<Player, Integer> leastRecieved = null;
		for (Map.Entry<Player, Integer> recieved : recievedSlugs.entrySet()) {
	        if (leastRecieved == null || recieved.getValue()
	            .compareTo(leastRecieved.getValue()) < 0) {
	        	leastRecieved = recieved;
	        }
	    }
		
		StringBuilder leastReceived = new StringBuilder();
		leastReceived.append("Der Nüchterne: ");
		leastReceived.append(leastRecieved.getKey().getName());
		leastReceived.append(" hat nur ");
		leastReceived.append(leastRecieved.getValue());
		leastReceived.append(" Schlücke bekommen.");
		return leastReceived.toString();		
	}
	                 
	@Transactional(readOnly=false)
	public String getMostSlugsReceived(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		List<SlugAllocation> slugAllocationsInGame = roundService.getSlugAllocationsForRounds(new ArrayList(game.getRounds()));
		Map<Player, Integer> allocatedSlugs = new HashMap<Player, Integer>();
		slugAllocationsInGame.forEach(slugAllocation -> {
			if(!allocatedSlugs.containsKey(slugAllocation.getToPlayer())) {
				allocatedSlugs.put(slugAllocation.getToPlayer(), 0);
			}
			int newCount = allocatedSlugs.get(slugAllocation.getToPlayer()) + 1;
			allocatedSlugs.put(slugAllocation.getToPlayer(), newCount);
		});
		Map.Entry<Player, Integer> mostRecieved = null;
		for (Map.Entry<Player, Integer> recieved : allocatedSlugs.entrySet()) {
	        if (mostRecieved == null || recieved.getValue()
	            .compareTo(mostRecieved.getValue()) > 0) {
	        	mostRecieved = recieved;
	        }
	    }
		
		StringBuilder mostReceived = new StringBuilder();
		mostReceived.append("Trinker des Tages: ");
		mostReceived.append(mostRecieved.getKey().getName());
		mostReceived.append(" hat stolze");
		mostReceived.append(mostRecieved.getValue()); 
		mostReceived.append(" Schlücke bekommen.");
		return mostReceived.toString();
	}
}
