package de.thkoeln.glug.data;

import de.thkoeln.glug.data.Player;
import de.thkoeln.glug.data.repository.GameRepository;
import de.thkoeln.glug.data.repository.PlayerRepository;
import de.thkoeln.glug.data.repository.RoundRepository;
import de.thkoeln.glug.data.repository.SlugAllocationRepository;


public class Statistic {
	private String statisticOne;
	private String statisticTwo;
	private String statisticThree;
	private String statisticFour;
	private String statisticFive;
	private String statisticSix;
	private Game game;
	private Round round;
	String player;

	PlayerRepository playerRepository;
	GameRepository gameRepository;
	RoundRepository roundrepository;
	SlugAllocationRepository slugAllocationRepository;

	public Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}
	public Iterable<Player> getPlayersWithName(String name) {
		return playerRepository.findByName(name);
	}

	public Statistic(String player, Game game){
		this.setPlayer(player);
		this.setStatisticOne(game);
		this.setStatisticTwo(game);
		this.setStatisticThree(game);
		this.setStatisticFour(game);
		this.setStatisticFive(game, player);
		this.setStatisticSix(game, player);
	}

	public String getStatisticOne() {
		return statisticOne;
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
	public void setStatisticOne(Game game) {
		StringBuilder statOneText = new StringBuilder();
		statOneText.append("Der Trinker: ");
		statOneText.append("Peter ");//  Select
		statOneText.append("musste ");
	    statOneText.append("20 "); // Select
	    statOneText.append("Schlücke trinken.");
		this.statisticOne = statOneText.toString();
	}
	public void setStatisticTwo(Game game) {
		StringBuilder statTwoText = new StringBuilder();
		statTwoText.append("Der Teufel: ");
		statTwoText.append("Basti ");// Select
		statTwoText.append("hat ganze ");
	    statTwoText.append("30 "); // Select
	    statTwoText.append("Schlücke verteilt.");
		this.statisticTwo = statTwoText.toString();
		}
	public void setStatisticThree(Game game) {
		StringBuilder statThreeText = new StringBuilder();
		statThreeText.append("Der Verlierer: ");
		statThreeText.append("Marius ");// Select
		statThreeText.append("hat nur ");
		statThreeText.append("5 ");	// Select
	    statThreeText.append("Schlücke verteilt.");
		this.statisticThree = statThreeText.toString();
		}
	public void setStatisticFour(Game game) {
		StringBuilder statFourText = new StringBuilder();
		statFourText.append("Der Nüchterne: ");
		statFourText.append("Maria ");// Select
		statFourText.append("musste ");
        statFourText.append("2 "); //Select
		statFourText.append("Schlücke getrunken.");
		this.statisticFour = statFourText.toString();
		}
	public void setStatisticFive(Game game, String player) {
		StringBuilder statFiveText = new StringBuilder();
		statFiveText.append("Marius ");// Select
		statFiveText.append("hat dir die meisten Schlücke ");
		statFiveText.append("(20) "); // Select
		statFiveText.append("gegeben.");
		this.statisticFive = statFiveText.toString();
		}
	public void setStatisticSix(Game game, String player) {
		StringBuilder statSixText = new StringBuilder();
		//roundrepository.findByGame(game);



		statSixText.append("Du hast ");
		statSixText.append("Peter ");//Select
		statSixText.append("am meisten Schlücke ");
		statSixText.append("(15) "); // Select
		statSixText.append("gegeben.");
		this.statisticSix = statSixText.toString();
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public Round getRound() {
		return round;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
}
