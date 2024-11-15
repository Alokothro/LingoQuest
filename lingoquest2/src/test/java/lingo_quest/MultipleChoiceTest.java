package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import lingoquestpackage.models.*;


public class MultipleChoiceTest {
    private MultipleChoice question;
    private Word correctAnswer;
    private ArrayList<Word> answerChoices;
    private User user;

    @Before
    public void setUp() {
        // Initialize a set of answer choices and correct answer using the updated Word constructor
        correctAnswer = new Word(Languages.SPANISH, "comer", "to eat", UUID.randomUUID(), UUID.randomUUID());
        answerChoices = new ArrayList<>(Arrays.asList(
            new Word(Languages.SPANISH, "beber", "to drink", UUID.randomUUID(), UUID.randomUUID()),
            correctAnswer,
            new Word(Languages.SPANISH, "jugar", "to play", UUID.randomUUID(), UUID.randomUUID()),
            new Word(Languages.SPANISH, "leer", "to read", UUID.randomUUID(), UUID.randomUUID())
        ));

        // Initialize MultipleChoice question
        question = new MultipleChoice("Spanish", answerChoices, correctAnswer);

        // Set up User with UserDictionary and the correct word entry
        user = new User("test_user", "secure_password");
        user.getUserDictionary().addWord(correctAnswer);
    }

    @Test
    public void testConstructorShufflesChoices() {
        // Verify that the answer choices were shuffled
        ArrayList<Word> originalOrder = new ArrayList<>(Arrays.asList(
            new Word(Languages.SPANISH, "beber", "to drink", UUID.randomUUID(), UUID.randomUUID()),
            new Word(Languages.SPANISH, "comer", "to eat", UUID.randomUUID(), UUID.randomUUID()),
            new Word(Languages.SPANISH, "jugar", "to play", UUID.randomUUID(), UUID.randomUUID()),
            new Word(Languages.SPANISH, "leer", "to read", UUID.randomUUID(), UUID.randomUUID())
        ));
        assertFalse("Answer choices should be shuffled", answerChoices.equals(originalOrder));
    }

    @Test
    public void testGetCorrectAnswerIndexPlusOne_ValidIndex() {
        // Verify the correct answer index is within the correct range
        int index = question.getCorrectAnswerIndexPlusOne();
        assertTrue("Correct answer index should be between 1 and 4", index >= 1 && index <= 4);
    }

    @Test
    public void testGetCorrectAnswerIndexPlusOne_MatchesCorrectAnswer() {
        // Verify that the correct answer index corresponds to the correct word
        int index = question.getCorrectAnswerIndexPlusOne();
        assertEquals("Index should match correct answer", correctAnswer, answerChoices.get(index - 1));
    }

    @Test
    public void testSetUserAnswer_SetsCorrectly() {
        question.setUserAnswer("2");
        assertEquals("User answer should be set to '2'", "2", question.userAnswer);
    }

    @Test
    public void testToString_FormatCheck() {
        String questionString = question.toString();
        assertTrue("Should contain the English version of the correct answer", questionString.contains(correctAnswer.getEnglishVersion()));
        assertTrue("Should contain all answer choices", questionString.contains(answerChoices.get(0).getWordinLanguage()));
    }

    @Test
    public void testIsCorrect_CorrectAnswer() {
        int correctIndex = question.getCorrectAnswerIndexPlusOne();
        question.setUserAnswer(String.valueOf(correctIndex));
        assertTrue("isCorrect should return true for the correct answer", question.isCorrect(user));
    }

    @Test
    public void testIsCorrect_WrongAnswer() {
        int incorrectIndex = (question.getCorrectAnswerIndexPlusOne() % answerChoices.size()) + 1; // Select any incorrect index
        question.setUserAnswer(String.valueOf(incorrectIndex));
        assertFalse("isCorrect should return false for an incorrect answer", question.isCorrect(user));
    }

    @Test
    public void testIsCorrect_InvalidInput() {
        question.setUserAnswer("invalid"); // Invalid input (not a number)
        assertFalse("isCorrect should return false for invalid input", question.isCorrect(user));
    }

    @Test
    public void testIsCorrect_EmptyInput() {
        question.setUserAnswer(""); // Empty input
        assertFalse("isCorrect should return false for empty input", question.isCorrect(user));
    }

    @Test
    public void testIsCorrect_SpecialCharactersInput() {
        question.setUserAnswer("!@#"); // Special characters as input
        assertFalse("isCorrect should return false for special characters input", question.isCorrect(user));
    }
}
