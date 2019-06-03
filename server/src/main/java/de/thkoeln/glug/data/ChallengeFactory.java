package de.thkoeln.glug.data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChallengeFactory {
	public static enum Colors{blue, yellow, green, brown, grey, red, orange, purple}
	public static enum Shapes{circle, triangle, square, pentagon, hexagon}
	public static enum Types{COLOR_BACKGROUND, COLOR_OBJECT, COLOR_OBJECT_BORDER, TEXT, SHAPE_OBJECT}



	private static final SecureRandom random = new SecureRandom();

	public static QuizChallenge generateRandomChallenge() {
		List<Colors> usedColors = new ArrayList<Colors>();
		Types type = randomEnum(Types.class);
		QuizChallenge generatedChallenge = new QuizChallenge();
		generatedChallenge.setColorBackground(generateColor(usedColors));
		generatedChallenge.setColorObject(generateColor(usedColors));
		generatedChallenge.setColorObjectBorder(generateColor(usedColors));
		generatedChallenge.setColorText(generateColor(usedColors));
		generatedChallenge.setShape(randomEnum(Shapes.class).toString());
		generatedChallenge.setQuestionType(type.toString());
		generatedChallenge.setQuestion(generateQuestion(type));
		generatedChallenge.setText(generateText(generatedChallenge));
		Set<QuizAnswer> answers = new HashSet<QuizAnswer>();
		answers.addAll(generateWrongAnswers(generatedChallenge));
		answers.add(generateCorrectAnswer(generatedChallenge));
		generatedChallenge.setAnswers(answers);

		return generatedChallenge;
	}

	private static String generateColor(List<Colors> usedColors) {
		List<Colors> availableColors = Stream.of(Colors.values()).collect(Collectors.toList());
		availableColors.removeAll(usedColors);
		int x = random.nextInt(availableColors.size());
		Colors selectedColor = availableColors.get(x);
		usedColors.add(selectedColor);
		return selectedColor.toString();
	}

	private static String generateShape(List<Shapes> usedShapes) {
		List<Shapes> availableShapes = Stream.of(Shapes.values()).collect(Collectors.toList());
		availableShapes.removeAll(usedShapes);
		int x = random.nextInt(availableShapes.size());
		Shapes selectedShape = availableShapes.get(x);
		usedShapes.add(selectedShape);
		return selectedShape.toString();
	}

	private static String generateText(QuizChallenge challenge) {
		Types type = Types.valueOf(challenge.getQuestionType());
		String text = null;
		if (type.equals(Types.SHAPE_OBJECT)){
			List<Shapes> usedShapes = new ArrayList<Shapes>();
			usedShapes.add(Shapes.valueOf(challenge.getShape()));
			text = generateShape(usedShapes);
		}
		else{
			text = randomEnum(Colors.class).toString();
		}
		return text;
	}

	private static String generateQuestion(Types type) {
		String question = "";
		switch (type) {
		case COLOR_BACKGROUND:
			question = "Welche Hintergrundfarbe?";
			break;

		case COLOR_OBJECT:
			question = "Welche Farbe hat das Objekt?";
			break;

		case COLOR_OBJECT_BORDER:
			question = "Welche Farbe hat der Objektrand?";
			break;

		case SHAPE_OBJECT:
			question = "Welche Form hat das Objekt?";
			break;

		case TEXT:
			question = "Was steht geschrieben?";
			break;
		}
		return question;
	}

	private static QuizAnswer generateCorrectAnswer(QuizChallenge generatedChallenge) {
		Types type = Types.valueOf(generatedChallenge.getQuestionType());
		QuizAnswer correctAnswer = new QuizAnswer();
		correctAnswer.setCorrect(true);
		String text = null;
		switch (type) {
		case COLOR_BACKGROUND:
			text = generatedChallenge.getColorBackground();
			break;
		case COLOR_OBJECT:
			text = generatedChallenge.getColorObject();
			break;
		case COLOR_OBJECT_BORDER:
			text = generatedChallenge.getColorObjectBorder();
			break;
		case SHAPE_OBJECT:
			text = generatedChallenge.getShape();
			break;
		case TEXT:
			text = generatedChallenge.getText();
			break;
		default:
			break;
		}
		correctAnswer.setText(text);
		correctAnswer.setQuizChallenge(generatedChallenge);
		return correctAnswer;
	}

	private static List<QuizAnswer> generateWrongAnswers(QuizChallenge generatedChallenge) {
		Types type = Types.valueOf(generatedChallenge.getQuestionType());
		QuizAnswer wrongAnswer1 = new QuizAnswer();
		wrongAnswer1.setCorrect(false);
		QuizAnswer wrongAnswer2 = new QuizAnswer();
		wrongAnswer2.setCorrect(false);
		QuizAnswer wrongAnswer3 = new QuizAnswer();
		wrongAnswer3.setCorrect(false);
		switch (type) {
		case COLOR_BACKGROUND:

			wrongAnswer2.setText(generatedChallenge.getColorObjectBorder());
			wrongAnswer3.setText(generatedChallenge.getColorObject());
			if(wrongAnswer2.getText().equals(generatedChallenge.getText()) || wrongAnswer3.getText().equals(generatedChallenge.getText())) {
				wrongAnswer1.setText(generatedChallenge.getText());
			} else {
				wrongAnswer1.setText(generatedChallenge.getColorText());
			}
			break;
		case COLOR_OBJECT:
			wrongAnswer2.setText(generatedChallenge.getColorObjectBorder());
			wrongAnswer3.setText(generatedChallenge.getColorBackground());
			if(wrongAnswer2.getText().equals(generatedChallenge.getText()) || wrongAnswer3.getText().equals(generatedChallenge.getText())) {
				wrongAnswer1.setText(generatedChallenge.getText());
			} else {
				wrongAnswer1.setText(generatedChallenge.getColorText());
			}
			break;
		case COLOR_OBJECT_BORDER:
			wrongAnswer2.setText(generatedChallenge.getColorObject());
			wrongAnswer3.setText(generatedChallenge.getColorBackground());
			if(wrongAnswer2.getText().equals(generatedChallenge.getText()) || wrongAnswer3.getText().equals(generatedChallenge.getText())) {
				wrongAnswer1.setText(generatedChallenge.getText());
			} else {
				wrongAnswer1.setText(generatedChallenge.getColorText());
			}
			break;
		case SHAPE_OBJECT:
			List<Shapes> usedShapes = new ArrayList<Shapes>();
			usedShapes.add(Shapes.valueOf(generatedChallenge.getShape()));
			Shapes textShape = Shapes.valueOf(generatedChallenge.getText());
			usedShapes.add(textShape);
			wrongAnswer1.setText(textShape.toString());
			wrongAnswer2.setText(generateShape(usedShapes));
			wrongAnswer3.setText(generateShape(usedShapes));
			break;
		case TEXT:
			wrongAnswer1.setText(generatedChallenge.getColorObjectBorder());
			wrongAnswer2.setText(generatedChallenge.getColorObject());
			wrongAnswer3.setText(generatedChallenge.getColorBackground());
			break;
		default:
			break;
		}
		wrongAnswer1.setQuizChallenge(generatedChallenge);
		wrongAnswer2.setQuizChallenge(generatedChallenge);
		wrongAnswer3.setQuizChallenge(generatedChallenge);
		List<QuizAnswer> wrongAnswers = new ArrayList<QuizAnswer>();
		wrongAnswers.add(wrongAnswer1);
		wrongAnswers.add(wrongAnswer2);
		wrongAnswers.add(wrongAnswer3);
		return wrongAnswers;
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
