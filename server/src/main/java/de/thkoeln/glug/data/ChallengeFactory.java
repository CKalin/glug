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
	public static enum Types{COLOR_BACKGROUND, COLOR_OBJECT, COLOR_OBJECT_BORDER, COLOR_TEXT, TEXT, SHAPE_OBJECT}



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

		case COLOR_TEXT:
			question = "Welche Farbe hat der Text?";
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
		case COLOR_TEXT:
			text = generatedChallenge.getColorText();
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
		List<String> usedColors = new ArrayList<String>();
		List<String> availableColors = new ArrayList<String>();
		availableColors.add(generatedChallenge.getColorObjectBorder());
		availableColors.add(generatedChallenge.getColorObject());
		availableColors.add(generatedChallenge.getColorBackground());
		availableColors.add(generatedChallenge.getText());
		availableColors.add(generatedChallenge.getColorText());
		switch (type) {
		case COLOR_BACKGROUND:
			usedColors.clear();
			usedColors.add(generatedChallenge.getColorBackground());
			wrongAnswer1.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer2.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer3.setText(generateAvailableColor(availableColors, usedColors));
			break;
		case COLOR_OBJECT:
			usedColors.clear();
			usedColors.add(generatedChallenge.getColorObject());
			wrongAnswer1.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer2.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer3.setText(generateAvailableColor(availableColors, usedColors));
			break;
		case COLOR_OBJECT_BORDER:
			usedColors.clear();
			usedColors.add(generatedChallenge.getColorObjectBorder());
			wrongAnswer1.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer2.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer3.setText(generateAvailableColor(availableColors, usedColors));
			break;
		case COLOR_TEXT:
			usedColors.clear();
			usedColors.add(generatedChallenge.getColorText());
			wrongAnswer1.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer2.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer3.setText(generateAvailableColor(availableColors, usedColors));
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
			usedColors.clear();
			usedColors.add(generatedChallenge.getText());
			wrongAnswer1.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer2.setText(generateAvailableColor(availableColors, usedColors));
			wrongAnswer3.setText(generateAvailableColor(availableColors, usedColors));
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

	private static String generateAvailableColor(List<String> availableColors, List<String> usedColors) {
		List<String> availableColorsCopy = new ArrayList<String>();
		availableColorsCopy.addAll(availableColors);
		availableColorsCopy.removeAll(usedColors);
		int x = random.nextInt(availableColorsCopy.size());
		String selectedColor = availableColorsCopy.get(x);
		usedColors.add(selectedColor);
		return selectedColor.toString();
	}


	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
