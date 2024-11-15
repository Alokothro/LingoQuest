package lingo_quest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
import org.junit.Test;

public class LessonTest {
    // using this helper method because my @beforeEach
    // and @aftereach aren't working for some reason
    private ArrayList<Word> createSampleWords() {
        ArrayList<Word> sampleWords = new ArrayList<>();
        Word word1 = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        word1.setPoints(100);
        word1.setTimesPresented(1);

        Word word2 = new Word(Languages.SPANISH, "gato", "cat", UUID.randomUUID(), UUID.randomUUID());
        word2.setPoints(200);
        word2.setTimesPresented(1);

        sampleWords.add(word1);
        sampleWords.add(word2);
        return sampleWords;
    }

    // update progress
    @Test
    public void testUpdateProgressCalculatesCorrectly() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        lesson.updateProgress();
        double expectedProgress = (double) (sampleWords.get(0).getPoints() + sampleWords.get(1).getPoints()) / lesson.getTotalPoints() * 100;
        assertEquals(expectedProgress, lesson.getLessonProgress());
    }

    // returns NaN ??
    @Test
    public void testUpdateProgressWithNoWords() {
        Lesson lesson = new Lesson();
        lesson.setTopicWordsByList(new ArrayList<>());
        lesson.updateProgress();
        assertEquals(0, lesson.getLessonProgress());
    }

    @Test
    public void testUpdateProgressZeroPointsEarned() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        sampleWords.forEach(word -> word.setPoints(0)); // Set points to 0
        lesson.setTopicWordsByList(sampleWords);
        lesson.updateProgress();
        assertEquals(0, lesson.getLessonProgress());
    }

    @Test
    public void testUpdateProgressPartialPointsEarned() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        sampleWords.get(0).setPoints(50); // Partial points
        lesson.setTopicWordsByList(sampleWords);
        lesson.updateProgress();
        double expectedProgress = (double) (sampleWords.get(0).getPoints() + sampleWords.get(1).getPoints()) / lesson.getTotalPoints() * 100;
        assertEquals(expectedProgress, lesson.getLessonProgress());
    }

    @Test
    public void testUpdateProgressMaxPointsEarned() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        sampleWords.forEach(word -> word.setPoints(300)); // Max points per word
        lesson.setTopicWordsByList(sampleWords);
        lesson.updateProgress();
        assertEquals(100.0, lesson.getLessonProgress());
    }

    // set topic word by list
    @Test
    public void testSetTopicWordsByListValidWords() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        assertEquals(2, lesson.topicWords.size());
    }

    @Test
    public void testSetTopicWordsByListEmptyList() {
        Lesson lesson = new Lesson();
        lesson.setTopicWordsByList(new ArrayList<>());
        assertNotNull(lesson.topicWords);
        assertEquals(0, lesson.topicWords.size());
    }

    @Test
    public void testSetTopicWordsByListWithNull() {
        Lesson lesson = new Lesson();
        lesson.setTopicWordsByList(null);
        assertNull(lesson.topicWords);
    }

    @Test
    public void testSetTopicWordsByListDuplicates() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        sampleWords.add(sampleWords.get(0)); // Add duplicate
        lesson.setTopicWordsByList(sampleWords);
        assertEquals(3, lesson.topicWords.size()); // Duplicate allowed
    }

    // get random word
    @Test
    public void testGetRandomWordFromNonEmptyList() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        Word randomWord = lesson.getRandomWord();
        assertNotNull(randomWord);
        assertTrue(lesson.topicWords.contains(randomWord));
    }

    @Test
    public void testGetRandomWordFromSingleWordList() {
        Lesson lesson = new Lesson();
        Word singleWord = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        singleWord.setTimesPresented(1);
        ArrayList<Word> singleWordList = new ArrayList<>();
        singleWordList.add(singleWord);
        lesson.setTopicWordsByList(singleWordList);
        assertEquals(singleWord, lesson.getRandomWord());
    }

    @Test
    public void testGetRandomWordThrowsExceptionIfNoWords() {
        Lesson lesson = new Lesson();
        lesson.setTopicWordsByList(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> lesson.getRandomWord());
    }

    @Test
    public void testGetRandomWordSelectsAnyWordFromList() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        boolean[] found = new boolean[sampleWords.size()];
        for (int i = 0; i < 100; i++) {
            Word randomWord = lesson.getRandomWord();
            if (randomWord.equals(sampleWords.get(0))) found[0] = true;
            if (randomWord.equals(sampleWords.get(1))) found[1] = true;
            if (found[0] && found[1]) break;
        }
        assertTrue(found[0] && found[1]);
    }

    // boud exception
    @Test
    public void testGetRandomWordReturnsNonNullForLargeList() {
        Lesson lesson = new Lesson();
        ArrayList<Word> largeWordList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largeWordList.add(new Word(Languages.SPANISH, "word" + ((Integer)i).toString(i), "word" + ((Integer)i).toString(i), UUID.randomUUID(), UUID.randomUUID()));
        }
        lesson.setTopicWordsByList(largeWordList);
        assertNotNull(lesson.getRandomWord());
    }

    // get words
    @Test
    public void testGetWordsReturnsCorrectNumber() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        ArrayList<Word> result = lesson.getWords(2);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetWordsReturnsUniqueWords() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        ArrayList<Word> result = lesson.getWords(2);
        assertEquals(2, result.size());
        assertTrue(result.contains(sampleWords.get(0)));
        assertNotEquals(result.get(0), result.get(1));
    }

    // infinite loop
    @Test
    public void testGetWordsHandlesInsufficientUniqueWords() {
        Lesson lesson = new Lesson();
        Word word1 = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        word1.setTimesPresented(1);
        ArrayList<Word> singleWordList = new ArrayList<>();
        singleWordList.add(word1);
        lesson.setTopicWordsByList(singleWordList);
        ArrayList<Word> result = lesson.getWords(2);
        assertEquals(1, result.size());  
    }

    @Test
    public void testGetWordsDoesNotIncludeDuplicates() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        ArrayList<Word> result = lesson.getWords( 2);
        assertEquals(2, result.size());
        assertTrue(result.stream().distinct().count() == result.size()); 
    }

    // infinite loop
    @Test
    public void testGetWordsHandlesLargeNumOfWordsRequested() {
        Lesson lesson = new Lesson();
        ArrayList<Word> sampleWords = createSampleWords();
        lesson.setTopicWordsByList(sampleWords);
        ArrayList<Word> result = lesson.getWords(5);
        assertEquals(2, result.size()); 
    }

}
