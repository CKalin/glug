package de.thkoeln.glug.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import de.thkoeln.glug.data.Constants.color;
import de.thkoeln.glug.data.Constants.objectShape;

@Entity
public class Challenge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private QuizChallenge quizChallenge;
	private Constants.color colorBackground;
	private Constants.color colorObject;
	private Constants.color colorObjectBorder;
	private Constants.objectShape shape;
	private String text;
	private Constants.questionTyp questionTyp;
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
	
	public Challenge(QuizChallenge quizChallenge){
		this.setQuizChallenge(quizChallenge);
		this.setResponses(new HashSet<ChallengeResponse>());
		
		ArrayList<Constants.color> colorArrayList = Constants.getArrayListColors();
		this.setColorBackground(generateRandomColor(colorArrayList));
		this.setColorObject(generateRandomColor(colorArrayList));
		this.setColorObjectBorder(generateRandomColor(colorArrayList));
		
		ArrayList<Constants.objectShape> shapeArrayList = Constants.getArrayListShape();
		this.setShape(generateRandomShape(shapeArrayList));
		
		ArrayList<Constants.questionTyp> questionTypArrayList = Constants.getArrayListQuestionTyp();
		this.setQuestionTyp(generateRandomQuestionTyp(questionTypArrayList));
		
		this.setText(generateText(colorArrayList, shapeArrayList));
		this.setAnswerCorrect(generateCorrectAnswer());
		this.setAnswerOne(this.getAnswerCorrect());
		generateWrongAnswers(colorArrayList, shapeArrayList);
		
		this.setCreateDateTime(LocalDateTime.now());
		
	}
	private void generateWrongAnswers(ArrayList<color> colorArrayList,	ArrayList<objectShape> shapeArrayList) {
		switch (this.getQuestionTyp()) {
		case FarbeHintergrund:
			this.setAnswerTwo(this.getText());
			this.setAnswerThree(this.getColorObjectBorderString());
			this.setAnswerFour(this.getColorObjectString());
			break;
		case FarbeObjekt:
			this.setAnswerTwo(this.getText());
			this.setAnswerThree(this.getColorObjectBorderString());		
			this.setAnswerFour(this.getColorBackgroundString());
			break;
		case FarbeObjektrand:
			this.setAnswerTwo(this.getText());
			this.setAnswerThree(this.getColorBackgroundString());
			this.setAnswerFour(this.getColorObjectString());
			break;
		case FormObjekt:
			this.setAnswerTwo(this.getText());
			this.setAnswerThree(generateRandomShape(shapeArrayList).toString());
			this.setAnswerFour(generateRandomShape(shapeArrayList).toString());
			break;
		case Text:
//			Wenn nach dem Text gefragt wird müssen die restlichen Antwort eine Farbe werden, 
//			da ein gefragter Text immer eine Farbe ist 
			this.setAnswerTwo(this.getColorObjectString());
			this.setAnswerThree(this.getColorBackgroundString());
			this.setAnswerFour(this.getColorObjectBorderString());
			break;
		}
		return;
	}

	private String generateText(ArrayList<Constants.color> colorArrayList, ArrayList<Constants.objectShape> shapeArrayList) {
		String text = null;
		if (getQuestionTyp().equals(Constants.questionTyp.FormObjekt)){
			text = generateRandomShape(shapeArrayList).toString();
		}
		else{
			text = generateRandomColor(colorArrayList).toString();
		}
		return text;
	}

	private String generateCorrectAnswer() {
		String answer = null;
		switch (this.getQuestionTyp()) {
		case FarbeHintergrund:
			answer = getColorBackgroundString();
			break;
		case FarbeObjekt:
			answer = getColorObjectString();
			break;
		case FarbeObjektrand:
			answer = getColorObjectBorderString();
			break;
		case FormObjekt:
			answer = getShapeString();
			break;
		case Text:
			answer = getText();
			break;	
		default:
			break;
		}
		return answer;
	}

	private Constants.color generateRandomColor(ArrayList<Constants.color> colorArrayList) {
		int randomInt = (int) (Math.random()*colorArrayList.size());
		Constants.color choosenColor = colorArrayList.get(randomInt);
		colorArrayList.remove(randomInt);
		return choosenColor;
	}
	
	private Constants.objectShape generateRandomShape(ArrayList<Constants.objectShape> shapeArrayList) {
		int randomInt = (int) (Math.random()*shapeArrayList.size());
		Constants.objectShape choosenShape = shapeArrayList.get(randomInt);
		shapeArrayList.remove(randomInt);
		return choosenShape;
	}
	
	private Constants.questionTyp generateRandomQuestionTyp(ArrayList<Constants.questionTyp> questionTypArrayList) {
		int randomInt = (int) (Math.random()*questionTypArrayList.size());
		Constants.questionTyp choosenQuestionTyp = questionTypArrayList.get(randomInt);
		questionTypArrayList.remove(randomInt);
		return choosenQuestionTyp;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer uuid) {
		this.id = uuid;
	}
	public QuizChallenge getQuizChallenge() {
		return quizChallenge;
	}
	public void setQuizChallenge(QuizChallenge quizChallenge) {
		this.quizChallenge = quizChallenge;
	}
	public Constants.color getColorBackground() {
		return colorBackground;
	}
	public String getColorBackgroundString() {
		return colorBackground.toString();
	}
	public void setColorBackground(Constants.color colorBackground) {
		this.colorBackground = colorBackground;
	}
	public Constants.color getColorObject() {
		return colorObject;
	}
	public String getColorObjectString() {
		return colorObject.toString();
	}
	public void setColorObject(Constants.color colorObject) {
		this.colorObject = colorObject;
	}
	public Constants.color getColorObjectBorder() {
		return colorObjectBorder;
	}
	public String getColorObjectBorderString() {
		return colorObjectBorder.toString();
	}
	public void setColorObjectBorder(Constants.color colorObjectBorder) {
		this.colorObjectBorder = colorObjectBorder;
	}
	public Constants.objectShape getShape() {
		return shape;
	}
	public String getShapeString() {
		return shape.toString();
	}
	public void setShape(Constants.objectShape shape) {
		this.shape = shape;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getQuestion() {
		String question = null;
		switch (this.getQuestionTyp()) {
		case FarbeHintergrund:
			question = "Welche Hintergrundfarbe?";
			break;

		case FarbeObjekt:
			question = "Welche Farbe hat das Objekt?";
			break;

		case FarbeObjektrand:
			question = "Welche Farbe hat der Objektrand?";
			break;

		case FormObjekt:
			question = "Welche Form hat das Objekt?";
			break;

		case Text:
			question = "Was steht geschrieben?";
			break;
		}
		return question;
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

	public Constants.questionTyp getQuestionTyp() {
		return questionTyp;
	}

	public void setQuestionTyp(Constants.questionTyp questionTyp) {
		this.questionTyp = questionTyp;
	}



}
