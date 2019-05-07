package de.thkoeln.glug.data;

import java.time.LocalDateTime;
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
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private QuizChallenge quizChallenge;
	private String colorBackground;
	private String colorObject;
	private String colorObjectBorder;
	private String shape;
	private String text;
	private String question;
	private String answerOne;
	private String answerTwo;
	private String answerThree;
	private String answerFour;
	private String answerCorrect;
	@OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ChallengeResponse> responses;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;
}
