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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Round {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Game game;
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<QuizChallenge> quizChallenges;
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RoundResult> roundResults;
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SlugAllocation> slugAllocations;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public Round(Game game){
		this.setGame(game);
		this.setCreateDateTime(LocalDateTime.now());
		this.setQuizChallenges(new HashSet<QuizChallenge>());
		this.setRoundResults(new HashSet<RoundResult>());
		this.setSlugAllocations(new HashSet<SlugAllocation>());
	}
	
	public QuizChallenge newQuizChallenge(){
		QuizChallenge quizChallenge = new QuizChallenge(this);
		quizChallenges.add(quizChallenge);
		return quizChallenge;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Set<QuizChallenge> getQuizChallenges() {
		return quizChallenges;
	}
	public void setQuizChallenges(Set<QuizChallenge> quizChallenges) {
		this.quizChallenges = quizChallenges;
	}
	public Set<RoundResult> getRoundResults() {
		return roundResults;
	}
	public void setRoundResults(Set<RoundResult> roundResults) {
		this.roundResults = roundResults;
	}
	public Set<SlugAllocation> getSlugAllocations() {
		return slugAllocations;
	}
	public void setSlugAllocations(Set<SlugAllocation> slugAllocations) {
		this.slugAllocations = slugAllocations;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}


}
