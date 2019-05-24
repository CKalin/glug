package de.thkoeln.glug.data;

//import de.thkoeln.glug.controller.GameService;
import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.QuizAnswerRepository;
import de.thkoeln.glug.data.repository.QuizChallengeRepository;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.RoundResultRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;
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

	private String AllSlugsToPlayer;
	private String statisticTwo;
	private String statisticThree;
	private String statisticFour;
	private String statisticFive;
	private String statisticSix;
	//private Iterable<Round> iterable_round;
	private Game game;
	//private SlugAllocation slugallocation;
	//private GameService gameservice;
	private Integer slugsreceived;
	private Integer columnIndex;
	private int[][] playerSlugsArray; //= new int[rowIndex][columnIndex];

	
	public Statistic(Player player, String accessCode){
		this.setGame(accessCode);		
		this.setAllReceivedSlugs(this.getGame());
		this.setAllSlugsinAllRoundsToPlayer(player);
		this.setAllSlugsFromPlayerReceived(player, this.getGame());
		this.setAtLeastSlugsReceivedInGame(this.getGame());
		this.setMostSlugsReceivedInGameByPlayer(getGame(), player);
		
		
		/*
		this.setStatisticTwo(game);
		this.setStatisticThree(game);
		this.setStatisticFour(game);
		this.setStatisticFive(game, player);
		this.setStatisticSix(game, player);
		*/
	}

	private Integer calculateRecievedSlugs(Round round, Player player) {
		List<SlugAllocation> recievedSlugs = slugAllocationRepository.findAllByRoundAndToPlayer(round, player);
		return recievedSlugs.size();
	}
	
	public Game getGame() {
		return game;
	}		
	public String getAllSlugsToPlayer() {
		return AllSlugsToPlayer;
	}
	public String getStatisticTwo() {
		return statisticTwo;
	}
	public String getStatisticThree() {
		return statisticThree;
	}
	public String getStatisticFour() {
		return statisticFour;
	}
	public String getStatisticFive() {
		return statisticFive;
	}
	public String getStatisticSix() {
		return statisticSix;
	}
	
	
	private Game fetchGame(String accessCode) {
		return gameRepository.findOneByAccessCode(accessCode).orElseThrow(() -> new RuntimeException("Game not existing"));
	}
	
	public void setGame(String accessCode) {
		this.game = fetchGame(accessCode);
	}
 	
	public void setAllReceivedSlugs(Game game) {
		
		Set<Player> players = game.getPlayers();
		players.forEach(player ->{
			this.playerSlugsArray[0][columnIndex] = player.getId();	
			slugsreceived = null;
				game.getRounds().forEach(round -> {
			    Integer calculateRecievedSlugs = calculateRecievedSlugs(round, player);		
			    slugsreceived = slugsreceived + calculateRecievedSlugs;
				});
			
				this.playerSlugsArray[1][columnIndex] = slugsreceived;
				columnIndex++;
		});
	}
	
	public void setAllSlugsinAllRoundsToPlayer(Player player) {
		for(int i = 0 ; i < this.playerSlugsArray.length; i++) {
			if(	this.playerSlugsArray[i][0] == player.getId() ) {
				this.slugsreceived = this.playerSlugsArray[1][i];
				break;
			}
		}
	}
	
	public void setAllSlugsFromPlayerReceived(Player player, Game game) {
		game.getRounds().forEach(round -> {
		
		});
		StringBuilder statTwoText = new StringBuilder();
		statTwoText.append("Der Teufel: ");
		statTwoText.append("Basti ");// Select
		statTwoText.append("hat ganze ");
	    statTwoText.append("30 "); // Select
	    statTwoText.append("Schlücke verteilt.");
		this.statisticTwo = statTwoText.toString();
		}
	

	
	
	public void setAtLeastSlugsReceivedInGame(Game game) {
		StringBuilder statFourText = new StringBuilder();
		statFourText.append("Der Nüchterne: ");
		statFourText.append("Maria ");// Select
		statFourText.append("musste ");
        statFourText.append("2 "); //Select
		statFourText.append("Schlücke getrunken.");
		this.statisticFour = statFourText.toString();
		}
	

	public void setMostSlugsReceivedInGameByPlayer(Game game, Player player) {
		StringBuilder statFiveText = new StringBuilder();
		statFiveText.append("Marius ");// Select
		statFiveText.append("hat dir die meisten Schlücke ");
		statFiveText.append("(20) "); // Select
		statFiveText.append("gegeben.");
		this.statisticFive = statFiveText.toString();
		}
	
	
	public void setStatisticSix(Game game, Player player) {
		/*
		StringBuilder statSixText = new StringBuilder();
		
		long numer_of_players = this.playerRepository.count();
		Integer slug_from_a_player = null;
		
		iterable_round = roundRepository.findByGame(game); // Alle Runden
		for (Round stat_round : iterable_round) { 
		Iterator<Round> Iter_round = iterable_round.iterator();
		while (Iter_round.hasNext()) {
			Round round = (Round) Iter_round.next();
			Set<RoundResult> results = gameservice.calculateRoundResult(round.getId());	
			
			
			Set<SlugAllocation> Slugallocation = round.getSlugAllocations();// Alle Schlücke der Runde
				Iterator SlugIterator = Slugallocation.iterator();
				while(SlugIterator.hasNext()) {
					Object slugallocation_o = SlugIterator.next();
					slugallocation = (SlugAllocation) slugallocation_o;
					Player FromPlayer = slugallocation.getFromPlayer(); // Schlücke für den Spieler hochzählen
						if (FromPlayer == player) {
							slug_from_a_player ++;	
						}
			
			List<SlugAllocation> Slugs =  slugAllocationRepository.findAllByRoundAndToPlayer(round, player);
			Iterator iterator = Slugs.iterator();
			while(iterator.hasNext() ){
			Object Slug = 	iterator.next();
			
			}
		}
		}
		
		statSixText.append("Du hast ");
		statSixText.append(slug_from_a_player);
		statSixText.append("gegeben.");
		this.statisticSix = statSixText.toString();
		*/
	}
}
