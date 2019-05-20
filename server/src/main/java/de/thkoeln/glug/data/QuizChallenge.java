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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QuizChallenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Round round;
	private String colorBackground;
	private String colorObject;
	private String colorObjectBorder;
	private String colorText;
	private String text;
	private String shape;
	private String questionType;
	private String question;
	@OneToMany(mappedBy = "quizChallenge", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<QuizAnswer> answers;
	@JsonIgnore
	@ManyToOne
	private Player winner;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

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
	public String getColorText() {
		return colorText;
	}
	public void setColorText(String colorText) {
		this.colorText = colorText;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Set<QuizAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<QuizAnswer> answers) {
		this.answers = answers;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
