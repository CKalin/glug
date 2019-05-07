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

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public QuizChallenge getQuizChallenge() {
		return quizChallenge;
	}
	public void setQuizChallenge(QuizChallenge quizChallenge) {
		this.quizChallenge = quizChallenge;
	}
	public String getColorBackground() {
		return colorBackground;
	}
	public void setColorBackground(String colorBackground) {
		this.colorBackground = colorBackground;
	}
	public String getColorObject() {
		return colorObject;
	}
	public void setColorObject(String colorObject) {
		this.colorObject = colorObject;
	}
	public String getColorObjectBorder() {
		return colorObjectBorder;
	}
	public void setColorObjectBorder(String colorObjectBorder) {
		this.colorObjectBorder = colorObjectBorder;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswerOne() {
		return answerOne;
	}
	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}
	public String getAnswerTwo() {
		return answerTwo;
	}
	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}
	public String getAnswerThree() {
		return answerThree;
	}
	public void setAnswerThree(String answerThree) {
		this.answerThree = answerThree;
	}
	public String getAnswerFour() {
		return answerFour;
	}
	public void setAnswerFour(String answerFour) {
		this.answerFour = answerFour;
	}
	public String getAnswerCorrect() {
		return answerCorrect;
	}
	public void setAnswerCorrect(String answerCorrect) {
		this.answerCorrect = answerCorrect;
	}
	public Set<ChallengeResponse> getResponses() {
		return responses;
	}
	public void setResponses(Set<ChallengeResponse> responses) {
		this.responses = responses;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}


}
