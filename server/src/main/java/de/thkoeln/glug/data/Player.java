package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String password;
	@OneToMany(mappedBy = "gamemaster", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> gamesLeaded;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> games;
	@OneToMany(mappedBy = "player")
	private Set<ChallengeResponse> challengeResponses;
	@OneToMany(mappedBy = "player")
	private Set<RoundResult> roundResults;
	@OneToMany(mappedBy = "fromPlayer")
	private Set<SlugAllocation> slugsAllocated;
	@OneToMany(mappedBy = "toPlayer")
	private Set<SlugAllocation> slugsRecieved;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public Player(String name, String password){
		this.setName(name);
		this.setPassword(password);
		this.setCreateDateTime(LocalDateTime.now());

		this.setGamesLeaded(new HashSet<Game>());
		this.setGames(new HashSet<Game>());
		this.setChallengeResponses(new HashSet<ChallengeResponse>());
		this.setRoundResults(new HashSet<RoundResult>());
		this.setSlugsAllocated(new HashSet<SlugAllocation>());
		this.setSlugsRecieved(new HashSet<SlugAllocation>());
	}
	public Player() {

	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Game> getGamesLeaded() {
		return gamesLeaded;
	}
	public void setGamesLeaded(Set<Game> gamesLeaded) {
		this.gamesLeaded = gamesLeaded;
	}
	public Set<Game> getGames() {
		return games;
	}
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	public Set<ChallengeResponse> getChallengeResponses() {
		return challengeResponses;
	}
	public void setChallengeResponses(Set<ChallengeResponse> challengeResponses) {
		this.challengeResponses = challengeResponses;
	}
	public Set<RoundResult> getRoundResults() {
		return roundResults;
	}
	public void setRoundResults(Set<RoundResult> roundResults) {
		this.roundResults = roundResults;
	}
	public Set<SlugAllocation> getSlugsAllocated() {
		return slugsAllocated;
	}
	public void setSlugsAllocated(Set<SlugAllocation> slugsAllocated) {
		this.slugsAllocated = slugsAllocated;
	}
	public Set<SlugAllocation> getSlugsRecieved() {
		return slugsRecieved;
	}
	public void setSlugsRecieved(Set<SlugAllocation> slugsRecieved) {
		this.slugsRecieved = slugsRecieved;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}


}
