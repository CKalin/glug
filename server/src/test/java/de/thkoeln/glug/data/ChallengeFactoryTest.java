package de.thkoeln.glug.data;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ChallengeFactoryTest {

	@Test
    public void generateRandomChallenge() {
		QuizChallenge challenge = ChallengeFactory.generateRandomChallenge();
		assertNotNull(challenge.getColorBackground());
		assertNotNull(challenge.getColorObject());
		assertNotNull(challenge.getColorObjectBorder());
		assertNotNull(challenge.getColorText());
		assertNotNull(challenge.getQuestion());
		assertNotNull(challenge.getQuestionType());
		assertNotNull(challenge.getText());
		assertNotNull(challenge.getAnswers());
		assertNotNull(challenge.getShape());
    }

}
