package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.lang.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



public class Statistic {
	private String statisticOne;
	private String statisticTwo;
	private String statisticThree;
	private String statisticFour;
	private String statisticFive;
	private String statisticSix;
	private Round round;
	String player;
	
	
	public Statistic(String player, Round round){
		this.setPlayer(player);
		this.setStatisticOne(round);
		this.setStatisticTwo(round);
		this.setStatisticThree(round);
		this.setStatisticFour(round);
		this.setStatisticFive(round, player);
		this.setStatisticSix(round, player);
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
	public void setStatisticOne(Round round) {
		StringBuilder statOneText = new StringBuilder();
		statOneText.append("Der Trinker: ");
		statOneText.append("Peter ");//  Select
		statOneText.append("musste ");
	    statOneText.append("20 "); // Select
	    statOneText.append("Schlücke trinken.");
		this.statisticOne = statOneText.toString();
	}
	public void setStatisticTwo(Round round) {
		StringBuilder statTwoText = new StringBuilder();
		statTwoText.append("Der Teufel: ");
		statTwoText.append("Basti ");// Select
		statTwoText.append("hat ganze ");
	    statTwoText.append("30 "); // Select
	    statTwoText.append("Schlücke verteilt.");
		this.statisticTwo = statTwoText.toString();	
		}
	public void setStatisticThree(Round round) {
		StringBuilder statThreeText = new StringBuilder();
		statThreeText.append("Der Verlierer: ");
		statThreeText.append("Marius ");// Select
		statThreeText.append("hat nur ");
		statThreeText.append("5 ");	// Select
	    statThreeText.append("Schlücke verteilt.");
		this.statisticThree = statThreeText.toString();	
		}
	public void setStatisticFour(Round round) {
		StringBuilder statFourText = new StringBuilder();
		statFourText.append("Der Nüchterne: ");
		statFourText.append("Maria ");// Select
		statFourText.append("musste ");
        statFourText.append("2 "); //Select
		statFourText.append("Schlücke getrunken.");
		this.statisticFour = statFourText.toString();	
		}
	public void setStatisticFive(Round round, String player) {
		StringBuilder statFiveText = new StringBuilder();
		statFiveText.append("Marius ");// Select
		statFiveText.append("hat dir die meisten Schlücke ");
		statFiveText.append("(20) "); // Select
		statFiveText.append("gegeben.");
		this.statisticFive = statFiveText.toString();	
		}
	public void setStatisticSix(Round round, String player) {
		StringBuilder statSixText = new StringBuilder();
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
