package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
public class QuizChallenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Round round;
	@OneToMany(mappedBy = "quizChallenge", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Challenge> challenges;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public QuizChallenge(Round round){
		this.setRound(round);
		this.setCreateDateTime(LocalDateTime.now());
		this.setChallenges(new HashSet<Challenge>());
		for (int i = 0; i < 5; i++) {
			this.getChallenges().add(new Challenge(this));
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
	public Set<Challenge> getChallenges() {
		return challenges;
	}
	public void setChallenges(Set<Challenge> challenges) {
		this.challenges = challenges;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}


}
