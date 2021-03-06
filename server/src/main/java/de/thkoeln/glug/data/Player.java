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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "gamemaster", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> gamesLeaded;
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Game> games;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<QuizAnswer> answers;
	@JsonIgnore
	@OneToMany(mappedBy = "player")
	private Set<RoundResult> roundResults;
	@JsonIgnore
	@OneToMany(mappedBy = "fromPlayer")
	private Set<SlugAllocation> slugsAllocated;
	@JsonIgnore
	@OneToMany(mappedBy = "toPlayer")
	private Set<SlugAllocation> slugsReceived;
	@JsonIgnore
	@OneToMany(mappedBy = "winner")
	private Set<QuizChallenge> wonChallenges;
	@JsonIgnore
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public Player(String name){
		this.setName(name);
		this.setCreateDateTime(LocalDateTime.now());

		this.setGamesLeaded(new HashSet<Game>());
		this.setGames(new HashSet<Game>());
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
	public Set<SlugAllocation> getSlugsReceived() {
		return slugsReceived;
	}
	public void setSlugsRecieved(Set<SlugAllocation> slugsReceived) {
		this.slugsReceived = slugsReceived;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Set<QuizAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<QuizAnswer> answers) {
		this.answers = answers;
	}
	public Set<QuizChallenge> getWonChallenges() {
		return wonChallenges;
	}
	public void setWonChallenges(Set<QuizChallenge> wonChallenges) {
		this.wonChallenges = wonChallenges;
	}
	public void setSlugsReceived(Set<SlugAllocation> slugsReceived) {
		this.slugsReceived = slugsReceived;
	}
}
