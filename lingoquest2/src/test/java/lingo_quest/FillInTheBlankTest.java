package lingo_quest;

import org.junit.Before;
import org.junit.Test;
//import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import lingoquestpackage.models.*;


public class FillInTheBlankTest {
    private FillInTheBlank question;
    private Word correctWord;
    private User mockUser;

    @Before
    public void setUp() {
        correctWord = mock(Word.class);
        mockUser = mock(User.class);
        Dictionary userDictionary = mock(Dictionary.class);
        
        when(correctWord.getEnglishVersion()).thenReturn("hello");
        when(correctWord.getWordinLanguage()).thenReturn("hola");
        when(correctWord.getWordUUID()).thenReturn(UUID.randomUUID());
        when(mockUser.getUserDictionary()).thenReturn(userDictionary);
        when(userDictionary.getWordByUUID(any(UUID.class))).thenReturn(correctWord);

        question = new FillInTheBlank("Spanish", correctWord);
    }

    // Test for the constructor
    @Test
    public void constructor_initializesValuesCorrectly() {
        assertEquals("Language should be initialized to Spanish", "Spanish", question.getLanguage());
        assertNotNull("Correct answer should be initialized", question.getCorrectAnswer());
        assertEquals("Coin value should be initialized to 100", 100, question.getCoinValue());
        assertEquals("Point value should be initialized to 100", 100, question.getPointValue());
    }

    // Tests for setUserAnswer
    @Test
    public void setUserAnswer_setsValueCorrectly() {
        question.setUserAnswer("hola");
        assertEquals("User answer should be set to 'hola'", "hola", question.userAnswer);
    }

    @Test
    public void setUserAnswer_withTrimming() {
        question.setUserAnswer("  hola  ");
        assertEquals("User answer should be trimmed", "hola", question.userAnswer);
    }

    @Test
    public void setUserAnswer_withUpperCase() {
        question.setUserAnswer("HOLA");
        assertNotEquals("User answer should be case-sensitive", "hola", question.userAnswer);
    }

    @Test
    public void setUserAnswer_emptyAnswer() {
        question.setUserAnswer("");
        assertEquals("User answer should handle empty strings", "", question.userAnswer);
    }

    @Test
    public void setUserAnswer_nullAnswer() {
        question.setUserAnswer(null);
        assertNull("User answer should handle null", question.userAnswer);
    }

    // Tests for toString
    @Test
    public void toString_returnsFormattedString() {
        assertEquals("What is the spanish version of hello\nType your answer below\n", question.toString());
    }

    // Tests for isCorrect
    @Test
    public void isCorrect_correctAnswerReturnsTrue() {
        question.setUserAnswer("hola");
        assertTrue("Correct answer should return true", question.isCorrect(mockUser));
    }

    @Test
    public void isCorrect_incorrectAnswerReturnsFalse() {
        question.setUserAnswer("adios");
        assertFalse("Incorrect answer should return false", question.isCorrect(mockUser));
    }

    @Test
    public void isCorrect_trimmedAnswer() {
        question.setUserAnswer(" hola ");
        assertTrue("Trimmed answer should still be correct", question.isCorrect(mockUser));
    }

    @Test
    public void isCorrect_caseInsensitive() {
        question.setUserAnswer("HOLA");
        assertTrue("Case insensitive check should still be correct", question.isCorrect(mockUser));
    }

    @Test
    public void isCorrect_nullAnswerReturnsFalse() {
        question.setUserAnswer(null);
        assertFalse("Null answer should return false", question.isCorrect(mockUser));
    }
}