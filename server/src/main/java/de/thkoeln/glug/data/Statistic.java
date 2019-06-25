package de.thkoeln.glug.data;

import de.thkoeln.glug.communication.response.SlugsAllocatedStatistic;
//import de.thkoeln.glug.controller.GameService;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.QuizAnswerRepository;
import de.thkoeln.glug.data.repository.QuizChallengeRepository;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;
import de.thkoeln.glug.data.service.PlayerService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;



public class Statistic {
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

	private String mostSlugsDistributed;
	private String leastSlugsDistributed;
	private String leastSlugsReceived;
	private String mostSlugsReceived;
	private Player player;
	private Game game;
	private long highValue;
	private long lowValue;
	private long ValuefromPlayerID;
	private int[][] playerSlugsArray; 
	private long[][] playerSlugsArrayl;
	
	
	public Statistic(Player player, String accessCode){
		this.setGame(accessCode);		
		this.setMostSlugsFromPlayerDistributed(this.getGame());
		this.setleastSlugsFromPlayerDistributed(this.getGame());
		this.setMostSlugsToPlayerReceived(this.game);
		this.setleastSlugsToPlayerReceived(this.game);
	}

	private List<SlugsAllocatedStatistic> calculateSlugsPerRound(Round round) {
		List<SlugsAllocatedStatistic> allSlugsPerRound = slugAllocationRepository.findAllByRoundGroupByFromPlayerAndToPlayer(round);
		return allSlugsPerRound;
	}
	
	public Game getGame() {
		return game;
	}		
	
	public String getMostSlugsFromPlayerDistributed() {
		return this.mostSlugsDistributed;
	};
	
	public String getleastSlugsFromPlayerDistributed() {
		return this.leastSlugsDistributed;
	}
	public String getMostSlugsToPlayerReceived() {
		return this.mostSlugsReceived;
	};
	
	public String getleastSlugsToPlayerReceived() {
		return this.leastSlugsReceived;
	}
	
	private Player FindPlayerByID(long playerId) {
		return player = new PlayerService().fetchPlayer((int) playerId);
	}
	
	private Game fetchGame(String accessCode) {
		return gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
	}
	
	public void setGame(String accessCode) {
		this.game = fetchGame(accessCode);
	}
	public void setMostSlugsFromPlayerDistributed(Game game)
	{		
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
		StringBuilder mostDistributed = new StringBuilder();
		mostDistributed.append("Der Teufel: ");
		mostDistributed.append(player.getName());// Select
		mostDistributed.append("hat ganze ");
		mostDistributed.append(highValue); // Select
		mostDistributed.append("Schlücke verteilt.");
		this.mostSlugsDistributed = mostDistributed.toString();
		
		
	}
	
	public void setleastSlugsFromPlayerDistributed(Game game)
	{		
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
		StringBuilder leastDistributed = new StringBuilder();
		leastDistributed.append("Der Verlier: ");
		leastDistributed.append(player.getName());// Select
		leastDistributed.append("hat nur ");
		leastDistributed.append(lowValue); // Select
		leastDistributed.append("Schlücke verteilt.");
		this.leastSlugsDistributed = leastDistributed.toString();
		
	}
	
	public void setleastSlugsToPlayerReceived(Game game)
	{		
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
		this.leastSlugsReceived = leastReceived.toString();
		
	}
	                 

	public void setMostSlugsToPlayerReceived(Game game)
	{		
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
		this.mostSlugsReceived = mostReceived.toString();
		
	}
	
	
	

}
