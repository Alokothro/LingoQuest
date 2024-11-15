package lingo_quest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import lingoquestpackage.models.*;


public class MatchingTest {

    private Matching matchingQuestion;
    private ArrayList<Word> answerChoices;
    private Word correctWord;
    private User testUser;

    @BeforeEach
    public void setUp() {
        // Set up test words and user
        correctWord = new Word(null, "Hello", "Hola", null, null);
        Word word2 = new Word(null, "Goodbye", "Adiós", null, null);
        Word word3 = new Word(null, "Please", "Por favor", null, null);
        
        answerChoices = new ArrayList<>();
        answerChoices.add(correctWord);
        answerChoices.add(word2);
        answerChoices.add(word3);

        // Create Matching question and test user
        matchingQuestion = new Matching("Spanish", answerChoices);
        testUser = new User();
    }

    @Test
    public void testGenerateQuestion_generatesValidIndex() {
        int index = matchingQuestion.answerIndex;
        assertTrue(index >= 0 && index < answerChoices.size(), "Generated index should be within answer choices range");
    }

    @Test
    public void testGenerateQuestion_changesAnswerIndex() {
        int initialIndex = matchingQuestion.answerIndex;
        matchingQuestion.generateQuestion();
        int newIndex = matchingQuestion.answerIndex;
        assertNotEquals(initialIndex, newIndex, "generateQuestion should change the answerIndex");
    }

    @Test
    public void testGenerateQuestion_multipleExecutions() {
        boolean differentIndexes = false;
        int initialIndex = matchingQuestion.answerIndex;

        for (int i = 0; i < 10; i++) {
            matchingQuestion.generateQuestion();
            if (matchingQuestion.answerIndex != initialIndex) {
                differentIndexes = true;
                break;
            }
        }
        
        assertTrue(differentIndexes, "Repeated calls to generateQuestion should produce different indexes over time");
    }

    @Test
    public void testIsCorrect_correctAnswer() {
        int correctIndex = matchingQuestion.answerIndex + 1; // Adjust for zero-based to one-based indexing
        assertTrue(matchingQuestion.isCorrect(correctIndex, testUser), "Correct answer should return true");
    }

    @Test
    public void testIsCorrect_incorrectAnswer() {
        int incorrectIndex = matchingQuestion.answerIndex == 0 ? 2 : matchingQuestion.answerIndex; // Choose an index different from the answer
        assertFalse(matchingQuestion.isCorrect(incorrectIndex, testUser), "Incorrect answer should return false");
    }

    @Test
    public void testIsCorrect_updatesUserDictionaryOnCorrect() {
        int correctIndex = matchingQuestion.answerIndex + 1;
        matchingQuestion.isCorrect(correctIndex, testUser);
        Word userWord = testUser.getUserDictionary().getWordByUUID(correctWord.getWordUUID());
        assertTrue(userWord.isWordPresented(), "Correct answer should mark word as presented in user's dictionary");
    }

    @Test
    public void testIsCorrect_updatesUserDictionaryOnIncorrect() {
        int incorrectIndex = matchingQuestion.answerIndex == 0 ? 2 : matchingQuestion.answerIndex;
        matchingQuestion.isCorrect(incorrectIndex, testUser);
        Word userWord = testUser.getUserDictionary().getWordByUUID(correctWord.getWordUUID());
        assertFalse(userWord.isWordPresented(), "Incorrect answer should not mark word as presented in user's dictionary");
    }

    @Test
    public void testToString_containsEnglishWord() {
        String questionText = matchingQuestion.toString();
        assertTrue(questionText.contains(correctWord.getEnglishVersion()), "Question should include the English word to match");
    }

    @Test
    public void testToString_containsAllChoices() {
        String questionText = matchingQuestion.toString();
        for (Word word : answerChoices) {
            assertTrue(questionText.contains(word.getWordinLanguage()), "Question should include all answer choices in the target language");
        }
    }

    @Test
    public void testToString_correctFormat() {
        String questionText = matchingQuestion.toString();
        assertTrue(questionText.startsWith("Match the English word"), "Question string should start with the correct prompt");
    }

    @Test
    public void testToString_uniqueAnswerIndices() {
        matchingQuestion.generateQuestion();
        String questionText1 = matchingQuestion.toString();

        matchingQuestion.generateQuestion();
        String questionText2 = matchingQuestion.toString();

        assertNotEquals(questionText1, questionText2, "toString output should vary when generateQuestion is called");
    }

    @Test
    public void testToString_doesNotModifyAnswerChoices() {
        ArrayList<Word> originalWords = new ArrayList<>(answerChoices);
        matchingQuestion.toString();
        assertEquals(originalWords, answerChoices, "toString should not modify the original list of answer choices");
    }

    @Test
    public void testIsCorrectWithUser_alwaysReturnsTrue() {
        assertTrue(matchingQuestion.isCorrect(testUser), "isCorrect with User should always return true in current implementation");
    }

    @Test
    public void testIsCorrectWithUser_doesNotUpdateUserDictionary() {
        matchingQuestion.isCorrect(testUser);
        Word userWord = testUser.getUserDictionary().getWordByUUID(correctWord.getWordUUID());
        assertNull(userWord, "isCorrect with User should not update the user's dictionary in current implementation");
    }

    @Test
    public void testIsCorrectWithUser_handlesNullUser() {
        assertTrue(matchingQuestion.isCorrect(null), "isCorrect with null User should return true");
    }

    @Test
    public void testIsCorrectWithUser_noSideEffects() {
        int originalIndex = matchingQuestion.answerIndex;
        matchingQuestion.isCorrect(testUser);
        assertEquals(originalIndex, matchingQuestion.answerIndex, "isCorrect with User should not modify answerIndex");
    }

    @Test
    public void testIsCorrectWithUser_doesNotAffectAnswerChoices() {
        ArrayList<Word> originalChoices = new ArrayList<>(answerChoices);
        matchingQuestion.isCorrect(testUser);
        assertEquals(originalChoices, answerChoices, "isCorrect with User should not modify the list of answer choices");
    }
}
