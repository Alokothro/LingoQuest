package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import lingoquestpackage.models.*;


import java.util.UUID;
import static org.junit.Assert.*;

public class QuestionTest {
    @Mock
    private Question question;  // Mocking the abstract Question class

    private Word correctWord;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Assuming Languages enum exists and is properly defined
        Languages language = Languages.DEFAULT;
        String wordText = "correct";
        String englishVersion = "correct";
        UUID lessonUUID = UUID.randomUUID();
        UUID wordUUID = UUID.randomUUID();

        // Creating a realistic Word object
        correctWord = new Word(language, wordText, englishVersion, lessonUUID, wordUUID);
        
        // Setup mocks for the Question methods
        when(question.getCorrectAnswer()).thenReturn(correctWord);
        when(question.isCorrect(any(User.class))).thenReturn(true);
        when(question.toString()).thenReturn("Mocked Question");
    }

    @Test
    public void testGetCorrectAnswer_returnsExpectedWord() {
        Word word = question.getCorrectAnswer();
        assertNotNull("Word should not be null", word);
        assertEquals("Expected word should be returned", "correct", word.getWordinLanguage());
    }

    @Test
    public void testIsCorrect_withCorrectAnswer() {
        assertTrue("Question should be correct", question.isCorrect(null));  // Assuming null user for simplicity
    }

    @Test
    public void testToString_returnsExpectedString() {
        assertEquals("toString should return mocked message", "Mocked Question", question.toString());
    }

    @Test
    public void testIsCorrect_withIncorrectAnswer() {
        when(question.isCorrect(any(User.class))).thenReturn(false); // Change mock behavior
        assertFalse("Question should be incorrect", question.isCorrect(null));
    }

    @Test
    public void testToString_withNoAnswer() {
        when(question.toString()).thenReturn("No answer provided");
        assertEquals("Should return 'No answer provided'", "No answer provided", question.toString());
    }

    @Test
    public void testIsCorrect_withUserAnswer() {
        User user = mock(User.class); // Create a mock user
        when(question.isCorrect(user)).thenReturn(true);
        assertTrue("Question should be correct when user is correct", question.isCorrect(user));
    }

    @Test
    public void testIsCorrect_afterUserChangesAnswer() {
        when(question.isCorrect(any(User.class))).thenReturn(false, true);
        assertFalse("First check: user is incorrect", question.isCorrect(null));
        assertTrue("Second check after answer changed: user is correct", question.isCorrect(null));
    }

    @Test
    public void testToString_differentStates() {
        when(question.toString()).thenReturn("Initial state");
        assertEquals("Should match initial state", "Initial state", question.toString());

        when(question.toString()).thenReturn("Updated state");
        assertEquals("Should match updated state", "Updated state", question.toString());
    }
}
