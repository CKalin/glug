package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Player gamemaster;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Player> players;
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Round> rounds;
	private String accessCode;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;


	public Game(Player gamemaster, String accessionCode){
		this.setGamemaster(gamemaster);
		this.setAccessCode(accessionCode);
		this.setCreateDateTime(LocalDateTime.now());
		this.setPlayers(new HashSet<Player>());
		this.setRounds(new HashSet<Round>());
		this.JoinGame(gamemaster, accessionCode);
	}

	public Game() {

	}

	public boolean JoinGame(Player player, String accessionCode){
		if (this.getAccessCode().equalsIgnoreCase(accessionCode)){
			return this.getPlayers().add(player);
		}
		return false;
	}

	public boolean QuitGame(Player player){
		return this.players.remove(player);
	}

	public Round newRound(){
		Round round = new Round(this);
		this.rounds.add(round);
		return round;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Player getGamemaster() {
		return gamemaster;
	}
	public void setGamemaster(Player gamemaster) {
		this.gamemaster = gamemaster;
	}
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	public Set<Round> getRounds() {
		return rounds;
	}
	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessionCode) {
		this.accessCode = accessionCode;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}


}
