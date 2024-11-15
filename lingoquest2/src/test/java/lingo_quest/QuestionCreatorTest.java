
package lingo_quest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import lingoquestpackage.models.*;


public class QuestionCreatorTest {
    private QuestionCreator questionCreator;
    private Lesson mockLesson;
    private Word mockWord;
    private ArrayList<Word> mockWordList;

    @BeforeEach
    public void setUp() {
        questionCreator = new QuestionCreator();
        mockLesson = mock(Lesson.class);
        mockWord = mock(Word.class);
        
        mockWordList = new ArrayList<>();
        mockWordList.add(mock(Word.class));
        mockWordList.add(mock(Word.class));
        mockWordList.add(mock(Word.class));
        mockWordList.add(mock(Word.class));
    }

    @Test
    public void testCreateMatchingQuestion() {
        when(mockWord.getPoints()).thenReturn(300);
        when(mockLesson.getRandomWord()).thenReturn(mockWord);
        when(mockLesson.getLanguageName()).thenReturn("Spanish");
        when(mockLesson.getWords(mockWord, 4)).thenReturn(mockWordList);

        Question question = questionCreator.createQuestion(mockLesson);
        
        assertTrue(question instanceof Matching, "Expected a Matching question for high complexity word.");
    }

    @Test
    public void testCreateFillInTheBlankQuestion() {
        when(mockWord.getPoints()).thenReturn(200);
        when(mockLesson.getRandomWord()).thenReturn(mockWord);
        when(mockLesson.getLanguageName()).thenReturn("Spanish");

        Question question = questionCreator.createQuestion(mockLesson);
        
        assertTrue(question instanceof FillInTheBlank, "Expected a FillInTheBlank question for medium complexity word.");
    }

    @Test
    public void testCreateMultipleChoiceQuestion() {
        when(mockWord.getPoints()).thenReturn(100);
        when(mockLesson.getRandomWord()).thenReturn(mockWord);
        when(mockLesson.getLanguageName()).thenReturn("Spanish");
        when(mockLesson.getWords(mockWord, 4)).thenReturn(mockWordList);

        Question question = questionCreator.createQuestion(mockLesson);
        
        assertTrue(question instanceof MultipleChoice, "Expected a MultipleChoice question for lower complexity word.");
    }

    @Test
    public void testCreateTrueOrFalseQuestion() {
        when(mockWord.getPoints()).thenReturn(0);
        when(mockLesson.getRandomWord()).thenReturn(mockWord).thenReturn(mockWordList.get(1));  
        when(mockLesson.getLanguageName()).thenReturn("Spanish");

        Question question = questionCreator.createQuestion(mockLesson);
        
        assertTrue(question instanceof TrueOrFalse, "Expected a TrueOrFalse question for zero or negative point word.");
    }

    @Test
    public void testCreateQuestionWithNullWord() {
        when(mockLesson.getRandomWord()).thenReturn(null);

        Question question = questionCreator.createQuestion(mockLesson);
        
        assertNull(question, "Expected null when lesson provides no word.");
    }

    @Test
    public void testCreateQuestionWithInvalidPointValue() {
        when(mockWord.getPoints()).thenReturn(50);  
        when(mockLesson.getRandomWord()).thenReturn(mockWord);

        assertThrows(IllegalArgumentException.class, () -> questionCreator.createQuestion(mockLesson), 
                     "Expected IllegalArgumentException for unsupported point value.");
    }
}
