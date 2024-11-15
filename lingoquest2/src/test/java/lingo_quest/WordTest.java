package lingo_quest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

import lingoquestpackage.models.*;


public class WordTest {

    private Word word;

    @Before
    public void setUp() {
        word = new Word(Languages.SPANISH, "comida", "food", UUID.randomUUID(), UUID.randomUUID());
    }

    // Test for Default Constructor
    @Test
    public void testDefaultConstructor() {
        Word defaultWord = new Word();
        assertEquals("", defaultWord.getWordinLanguage());
        assertEquals(0, defaultWord.getTimesPresented());
        assertEquals(0, defaultWord.getTimesCorrect());
        assertEquals(0.0, defaultWord.getUserUnderstanding(), 0.01);
        assertNotNull(defaultWord.getWordUUID());
        assertNotNull(defaultWord.getLessonID());
        assertEquals(Languages.DEFAULT, defaultWord.getLanguage());
    }

    // Test for Parameterized Constructor
    @Test
    public void testParameterizedConstructor() {
        assertEquals("comida", word.getWordinLanguage());
        assertEquals("food", word.getEnglishVersion());
        assertEquals(Languages.SPANISH, word.getLanguage());
        assertNotNull(word.getLessonID());
        assertNotNull(word.getWordUUID());
    }

    @Test
    public void testParameterizedConstructorPoints() {
        assertEquals(0, word.getPoints());
    }

    // Test for setWord Method
    @Test
    public void testSetWord() {
        word.setWord("bebida");
        assertEquals("bebida", word.getWordinLanguage());
    }

    @Test
    public void testSetWordEmpty() {
        word.setWord("");
        assertEquals("", word.getWordinLanguage());
    }

    @Test
    public void testSetWordNull() {
        word.setWord(null);
        assertNull(word.getWordinLanguage());
    }

    // Test for setEnglishVersion Method
    @Test
    public void testSetEnglishVersion() {
        word.setEnglishVersion("drink");
        assertEquals("drink", word.getEnglishVersion());
    }

    @Test
    public void testSetEnglishVersionEmpty() {
        word.setEnglishVersion("");
        assertEquals("", word.getEnglishVersion());
    }

    @Test
    public void testSetEnglishVersionNull() {
        word.setEnglishVersion(null);
        assertNull(word.getEnglishVersion());
    }

    // Test for setTimesPresented Method
    @Test
    public void testSetTimesPresentedPositive() {
        word.setTimesPresented(5);
        assertEquals(5, word.getTimesPresented());
    }

    @Test
    public void testSetTimesPresentedZero() {
        word.setTimesPresented(0);
        assertEquals(0, word.getTimesPresented());
    }

    @Test
    public void testSetTimesPresentedNegative() {
        word.setTimesPresented(-5);
        assertEquals(-5, word.getTimesPresented()); // Handling of negative values may need adjustment in logic
    }

    // Test for setTimesCorrect Method
    @Test
    public void testSetTimesCorrectPositive() {
        word.setTimesCorrect(3);
        assertEquals(3, word.getTimesCorrect());
    }

    @Test
    public void testSetTimesCorrectZero() {
        word.setTimesCorrect(0);
        assertEquals(0, word.getTimesCorrect());
    }

    @Test
    public void testSetTimesCorrectNegative() {
        word.setTimesCorrect(-2);
        assertEquals(-2, word.getTimesCorrect()); // Handling of negative values may need adjustment in logic
    }

    // Test for wordPresented Method
    @Test
    public void testWordPresentedCorrectAnswer() {
        word.wordPresented(true);
        assertEquals(1, word.getTimesPresented());
        assertEquals(1, word.getTimesCorrect());
        assertEquals(100, word.getPoints());
    }

    @Test
    public void testWordPresentedIncorrectAnswer() {
        word.wordPresented(false);
        assertEquals(1, word.getTimesPresented());
        assertEquals(0, word.getTimesCorrect());
        assertEquals(0, word.getPoints());
    }

    @Test
    public void testWordPresentedMultipleCorrectAnswers() {
        word.wordPresented(true);
        word.wordPresented(true);
        assertEquals(2, word.getTimesPresented());
        assertEquals(2, word.getTimesCorrect());
        assertEquals(200, word.getPoints());
    }

    @Test
    public void testWordPresentedMultipleIncorrectAnswers() {
        word.wordPresented(false);
        word.wordPresented(false);
        assertEquals(2, word.getTimesPresented());
        assertEquals(0, word.getTimesCorrect());
        assertEquals(0, word.getPoints());
    }

    @Test
    public void testWordPresentedMixedAnswers() {
        word.wordPresented(true);
        word.wordPresented(false);
        word.wordPresented(true);
        assertEquals(3, word.getTimesPresented());
        assertEquals(2, word.getTimesCorrect());
        assertEquals(200, word.getPoints());
    }

    // Test for User Understanding
    @Test
    public void testUserUnderstandingCorrectAnswer() {
        word.wordPresented(true);
        assertEquals(100.0, word.getUserUnderstanding(), 0.01);
    }

    @Test
    public void testUserUnderstandingIncorrectAnswer() {
        word.wordPresented(false);
        assertEquals(0.0, word.getUserUnderstanding(), 0.01);
    }

    @Test
    public void testUserUnderstandingMixedAnswers() {
        word.wordPresented(true);
        word.wordPresented(false);
        assertEquals(50.0, word.getUserUnderstanding(), 0.01);
    }

    @Test
    public void testUserUnderstandingNoPresentations() {
        assertEquals(0.0, word.getUserUnderstanding(), 0.01);
    }

    // Test for Points System
    @Test
    public void testPointsMaxLimit() {
        word.wordPresented(true);
        word.wordPresented(true);
        word.wordPresented(true);
        word.wordPresented(true); // Max points is 400
        assertEquals(400, word.getPoints());
    }

    @Test
    public void testPointsMinLimit() {
        word.wordPresented(false);
        word.wordPresented(false);
        assertEquals(0, word.getPoints());
    }

    @Test
    public void testPointsReduction() {
        word.wordPresented(true);
        word.wordPresented(false);
        assertEquals(0, word.getPoints());
    }

    // Test for getLanguage Method
    @Test
    public void testGetLanguage() {
        assertEquals(Languages.SPANISH, word.getLanguage());
    }

    @Test
    public void testSetLanguage() {
        word.setLanguage(Languages.FRENCH);
        assertEquals(Languages.FRENCH, word.getLanguage());
    }

    // Test for isEqualTo Method
    @Test
    public void testIsEqualToSameWord() {
        Word otherWord = new Word(Languages.SPANISH, "comida", "food", UUID.randomUUID(), UUID.randomUUID());
        assertTrue(word.isEqualTo(otherWord));
    }

    @Test
    public void testIsEqualToDifferentWord() {
        Word otherWord = new Word(Languages.SPANISH, "bebida", "drink", UUID.randomUUID(), UUID.randomUUID());
        assertFalse(word.isEqualTo(otherWord));
    }

    @Test
    public void testIsEqualToCaseInsensitive() {
        Word otherWord = new Word(Languages.SPANISH, "Comida", "food", UUID.randomUUID(), UUID.randomUUID());
        assertTrue(word.isEqualTo(otherWord));
    }

    // Test for toString Method
    @Test
    public void testToString() {
        String expected = "Word Details:\n" +
                "------------------------\n" +
                "Word: comida (SPANISH)\n" +
                "English Version: food\n" +
                "Points: 0\n" +
                "Times Presented: 0\n" +
                "Times Correct: 0\n" +
                "User Understanding: 0.00%\n" +
                "------------------------";
        assertEquals(expected, word.toString());
    }

    @Test
    public void testToStringAfterWordPresented() {
        word.wordPresented(true);
        String expected = "Word Details:\n" +
                "------------------------\n" +
                "Word: comida (SPANISH)\n" +
                "English Version: food\n" +
                "Points: 100\n" +
                "Times Presented: 1\n" +
                "Times Correct: 1\n" +
                "User Understanding: 100.00%\n" +
                "------------------------";
        assertEquals(expected, word.toString());
    }
}



