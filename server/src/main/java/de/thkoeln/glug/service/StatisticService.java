package de.thkoeln.glug.service;

import de.thkoeln.glug.communication.response.SlugsAllocatedStatistic;
import de.thkoeln.glug.data.Game;
//import de.thkoeln.glug.controller.GameService;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.Round;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.QuizAnswerRepository;
import de.thkoeln.glug.data.repository.QuizChallengeRepository;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StatisticService {
	@Autowired
	GameService gameService;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameRepository gameRepository;
	@Autowired
	RoundRepository roundRepository;
	@Autowired
	RoundResultRepository roundResultRepository;
	@Autowired
	QuizChallengeRepository quizChallengeRepository;
	@Autowired
	QuizAnswerRepository quizAnswerRepository;
	@Autowired
	SlugAllocationRepository slugAllocationRepository;
	
	public StatisticService() {
		
	}
	
	@Transactional(readOnly=false)
	public String getMostSlugsAllocated(String accessCode) {	
		Game game = gameService.fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		players.forEach(player ->{
			for( int i = 0; i < game.getPlayers().size(); i++) {
				this.playerSlugsArrayl[i][1] = player.getId();
			};
		});			
		
		game.getRounds().forEach(round -> 
		{
			List<SlugsAllocatedStatistic> SlugsAllocatedStatistic = calculateSlugsPerRound(round);
			for ( int i = 0; i < SlugsAllocatedStatistic.size(); i++ ) 
			{
				de.thkoeln.glug.communication.response.SlugsAllocatedStatistic SlugAllocation = SlugsAllocatedStatistic.get(i);
				for( int  numberPlayer =  0; i < game.getPlayers().size(); i++) 
				{
					if ( SlugAllocation.getFromPlayerId() == this.playerSlugsArray[numberPlayer][1] ) 
					{
					this.playerSlugsArrayl[numberPlayer][2] = this.playerSlugsArrayl[numberPlayer][2] + SlugAllocation.getSlugCountAllocated();
						if(highValue < this.playerSlugsArrayl[numberPlayer][2]) 
						{
							highValue = this.playerSlugsArrayl[numberPlayer][2];
							ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1];
							player = FindPlayerByID(ValuefromPlayerID);
						}
					}
				}
			}
		});
		StringBuilder mostAllocated = new StringBuilder();
		mostAllocated.append("Der Teufel: ");
		mostAllocated.append(player.getName());// Select
		mostAllocated.append("hat ganze ");
		mostAllocated.append(highValue); // Select
		mostAllocated.append("Schlücke verteilt.");
		return mostAllocated.toString();
	}
	
	@Transactional(readOnly=false)
	public String getLeastSlugsAllocated(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		players.forEach(player ->{
			for( int i = 0; i < game.getPlayers().size(); i++) {
				this.playerSlugsArrayl[i][1] = player.getId();
			};
		});			
		
		game.getRounds().forEach(round -> 
		{
			List<SlugsAllocatedStatistic> SlugsAllocatedStatistic = calculateSlugsPerRound(round);
			for ( int i = 0; i < SlugsAllocatedStatistic.size(); i++ ) 
			{
				de.thkoeln.glug.communication.response.SlugsAllocatedStatistic SlugAllocation = SlugsAllocatedStatistic.get(i);
				for( int  numberPlayer =  0; i < game.getPlayers().size(); i++) 
				{
					if ( SlugAllocation.getFromPlayerId() == this.playerSlugsArray[numberPlayer][1] ) 
					{
					this.playerSlugsArrayl[numberPlayer][2] = this.playerSlugsArrayl[numberPlayer][2] + SlugAllocation.getSlugCountAllocated();
						
					   if(lowValue == 0) 
						{
						   lowValue = this.playerSlugsArrayl[numberPlayer][2];  
						   ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1]; 
						}
					   else {
						   if (lowValue > this.playerSlugsArrayl[numberPlayer][2]){
						   lowValue = this.playerSlugsArrayl[numberPlayer][2];
						   ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1];
					   }
							player = FindPlayerByID(ValuefromPlayerID);
						}
					}
				}
			}
		});
		StringBuilder leastAllocated = new StringBuilder();
		leastAllocated.append("Der Verlier: ");
		leastAllocated.append(player.getName());// Select
		leastAllocated.append("hat nur ");
		leastAllocated.append(lowValue); // Select
		leastAllocated.append("Schlücke verteilt.");
		return leastAllocated.toString();
		
	}
	
	@Transactional(readOnly=false)
	public String getLeastSlugsReceived(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		players.forEach(player ->{
			for( int i = 0; i < game.getPlayers().size(); i++) {
				this.playerSlugsArrayl[i][1] = player.getId();
			};
		});			
		
		game.getRounds().forEach(round -> 
		{
			List<SlugsAllocatedStatistic> SlugsAllocatedStatistic = calculateSlugsPerRound(round);
			for ( int i = 0; i < SlugsAllocatedStatistic.size(); i++ ) 
			{
				de.thkoeln.glug.communication.response.SlugsAllocatedStatistic SlugAllocation = SlugsAllocatedStatistic.get(i);
				for( int  numberPlayer =  0; i < game.getPlayers().size(); i++) 
				{
					if ( SlugAllocation.getToPlayerId() == this.playerSlugsArray[numberPlayer][1] ) 
					{
					this.playerSlugsArrayl[numberPlayer][2] = this.playerSlugsArrayl[numberPlayer][2] + SlugAllocation.getSlugCountAllocated();
						
					   if(lowValue == 0) 
						{
						   lowValue = this.playerSlugsArrayl[numberPlayer][2];  
						   ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1]; 
						}
					   else {
						   if (lowValue > this.playerSlugsArrayl[numberPlayer][2]){
						   lowValue = this.playerSlugsArrayl[numberPlayer][2];
						   ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1];
					   }
							player = FindPlayerByID(ValuefromPlayerID);
						}
					}
				}
			}
		});
		StringBuilder leastReceived = new StringBuilder();
		leastReceived.append("Der Nüchterne: ");
		leastReceived.append(player.getName());// Select
		leastReceived.append("hat nur ");
		leastReceived.append(lowValue); // Select
		leastReceived.append("Schlücke bekommen.");
		return leastReceived.toString();		
	}
	                 
	@Transactional(readOnly=false)
	public String getMostSlugsReceived(String accessCode) {
		Game game = gameService.fetchGame(accessCode);
		Set<Player> players = game.getPlayers();
		players.forEach(player ->{
			for( int i = 0; i < game.getPlayers().size(); i++) {
				this.playerSlugsArrayl[i][1] = player.getId();
			};
		});			
		
		game.getRounds().forEach(round -> 
		{
			List<SlugsAllocatedStatistic> SlugsAllocatedStatistic = calculateSlugsPerRound(round);
			for ( int i = 0; i < SlugsAllocatedStatistic.size(); i++ ) 
			{
				de.thkoeln.glug.communication.response.SlugsAllocatedStatistic SlugAllocation = SlugsAllocatedStatistic.get(i);
				for( int  numberPlayer =  0; i < game.getPlayers().size(); i++) 
				{
					if ( SlugAllocation.getToPlayerId() == this.playerSlugsArray[numberPlayer][1] ) 
					{
					this.playerSlugsArrayl[numberPlayer][2] = this.playerSlugsArrayl[numberPlayer][2] + SlugAllocation.getSlugCountAllocated();
						
						   if (highValue < this.playerSlugsArrayl[numberPlayer][2]){
						   highValue = this.playerSlugsArrayl[numberPlayer][2];
						   ValuefromPlayerID = this.playerSlugsArrayl[numberPlayer][1];
					   }
							player = FindPlayerByID(ValuefromPlayerID);
					}
				}
			}
		});
		
		StringBuilder mostReceived = new StringBuilder();
		mostReceived.append("Trinker des Tages: ");
		mostReceived.append(player.getName());// Select
		mostReceived.append("hat stolze");
		mostReceived.append(highValue); // Select
		mostReceived.append("Schlücke bekommen.");
		return mostReceived.toString();
	}
}
