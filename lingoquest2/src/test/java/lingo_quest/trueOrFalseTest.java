package lingo_quest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;
import static org.junit.Test.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import lingoquestpackage.models.*;


public class trueOrFalseTest {
    private TrueOrFalse question;
    private Word correctWord;
    private Word incorrectWord;
    private User user;

    @Before
    public void setUp() {
        correctWord = new Word(null, "casa", "house", UUID.randomUUID(), UUID.randomUUID());
        incorrectWord = new Word(null, "gato", "cat", UUID.randomUUID(), UUID.randomUUID());
        user = new User(); // Initialize with necessary values/mocks if required
    }

    @Test
    public void testConstructorTrueAnswer() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        question.setUserAnswer("true");

        if ("true".equals(question.answer)) {
            assertEquals(correctWord, question.displayedWord);
            assertEquals("true", question.answer);
        } else {
            assertEquals(incorrectWord, question.displayedWord);
            assertEquals("false", question.answer);
        }
    }

    @Test
    public void testConstructorFalseAnswer() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        question.setUserAnswer("false");

        if ("false".equals(question.answer)) {
            assertEquals(incorrectWord, question.displayedWord);
            assertEquals("false", question.answer);
        } else {
            assertEquals(correctWord, question.displayedWord);
            assertEquals("true", question.answer);
        }
    }

    @Test
    public void testSetUserAnswer() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        question.setUserAnswer("true");
        assertEquals("true", question.userAnswer);
    }

    /**
     * 
     */
    @Test
    public void testIsCorrectWithCorrectAnswer() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        question.setUserAnswer(question.answer); // Set user's answer to match the correct answer

        boolean result = question.isCorrect(user);
        //assertTrue(result);
      
    }

    @Test
    public void testIsCorrectWithIncorrectAnswer() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        question.setUserAnswer(question.answer.equals("true") ? "false" : "true"); // Set user's answer to the opposite of correct answer

        boolean result = question.isCorrect(user);
        assertFalse(result);
    }

    @Test
    public void testToString() {
        question = new TrueOrFalse("spanish", correctWord, incorrectWord);
        String expectedString = "Is " + question.displayedWord.getWordinLanguage() + " the Spanish word for "
                + correctWord.getEnglishVersion() + "? (true/false)";
        assertEquals(expectedString, question.toString());
    }
}
