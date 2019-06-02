package de.thkoeln.glug.communication.response;

import java.util.ArrayList;
import java.util.List;

import de.thkoeln.glug.data.QuizChallenge;

public class NewChallengeMessage {
	String action = "NEW_CHALLENGE";
	private int challengeId;
	private int roundId;
	private String colorBackground;
	private String colorObject;
	private String colorObjectBorder;
	private String colorText;
	private String text;
	private String shape;
	private String questionType;
	private String question;
	private List<Answer> answers = new ArrayList<>();

	public NewChallengeMessage(QuizChallenge challenge) {
		super();
		this.roundId = challenge.getRound().getId();
		this.challengeId = challenge.getId();
		this.colorBackground = challenge.getColorBackground();
		this.colorObject = challenge.getColorObject();
		this.colorObjectBorder = challenge.getColorObjectBorder();
		this.colorText = challenge.getColorText();
		this.text = challenge.getText();
		this.shape = challenge.getShape();
		this.questionType = challenge.getQuestionType();
		this.question = challenge.getQuestion();
		challenge.getAnswers().forEach(answer -> {
			Answer a = new Answer();
			a.setText(answer.getText());
			a.setId(answer.getId());
			answers.add(a);
		});
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public int getRoundId() {
		return roundId;
	}

	public void setRoundId(int roundId) {
		this.roundId = roundId;
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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
