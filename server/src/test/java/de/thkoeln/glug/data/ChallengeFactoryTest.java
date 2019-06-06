package de.thkoeln.glug.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChallengeFactoryTest {
	final static Logger LOG = LoggerFactory.getLogger(ChallengeFactoryTest.class);
	private int countFailed = 0;

	@Test
    public void generateRandomChallenge() {
		for(int i=0; i<1000; i++) {
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
			Set<QuizAnswer> answers = challenge.getAnswers();
			Set<String> texts = new HashSet<String>();
			answers.stream().forEach(answer -> {
				texts.add(answer.getText());
			});

			if(texts.size() == 3) {
				LOG.info("failed: {}", challenge);
				countFailed++;
			}
			assertEquals(4, texts.size());
		}
		LOG.info("FAILED COUNT = {}", countFailed);
    }

}
