package lingo_quest;

import static org.junit.Assert.*;
import static org.junit.Test.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
//import org.junit.internal.matchers.Each;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class DictionaryTest {
    private Dictionary dictionary = new Dictionary();
    private ArrayList<Word> testWords = new ArrayList();
    private UUID testTopicID = UUID.randomUUID();


    @BeforeEach
    public void setup() {
        testWords.clear();
        Word w1 = new Word(Languages.SPANISH, "perro", "dog", testTopicID, UUID.randomUUID());
        w1.setUserUnderstanding(20.0);
        Word w2 = new Word(Languages.SPANISH, "gato", "cat", testTopicID, UUID.randomUUID());
        w2.setUserUnderstanding(80.0);
        Word w3 = new Word(Languages.SPANISH, "rana", "frog", UUID.randomUUID(), UUID.randomUUID());
        w3.setUserUnderstanding(40.0);
        Word w4 = new Word(Languages.SPANISH, "mango", "mango", UUID.randomUUID(), UUID.randomUUID());
        w4.setUserUnderstanding(70.0);
        // add the words
        testWords.add(w1);
        testWords.add(w2);
        testWords.add(w3);
        testWords.add(w4);
        // set the dictionary's words to be the array list we just made
        dictionary.setWords(testWords);
    }

    @AfterEach
    public void tearDown() {
        testWords.clear();
    }

    @Test
    public void testAddWord() {
        // add a valid word and see if it's contained in dictionary
        Word w = new Word(Languages.SPANISH,"pez","fish",UUID.randomUUID(),UUID.randomUUID());
        dictionary.addWord(w);
        assertTrue(dictionary.contains("pez"));
    }

    @Test
    public void testAddWordNull() {
        // add a null word then see if the list contains it
        Word w = null;
        dictionary.addWord(w);
        assertFalse(dictionary.getWords().contains(w));
    }

    @Test
    public void testAddWordIncrementCount() {
        Word newWord = new Word(Languages.SPANISH, "libro", "book", UUID.randomUUID(), UUID.randomUUID());
        int initialCount = dictionary.getNumberOfWords();
        dictionary.addWord(newWord);
        assertEquals(initialCount + 1, dictionary.getNumberOfWords());
    }

    @Test
    public void testAddWordDuplicate() {
        Word duplicateWord = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        dictionary.addWord(duplicateWord);
        long count = dictionary.getWords().stream().filter(w -> w.getWordinLanguage().equals("perro")).count();
        assertEquals(1, count);
    }

    @Test
    public void testAddWordEmptyLanguageWord() {
        Word emptyWord = new Word(Languages.SPANISH, "", "blank", UUID.randomUUID(), UUID.randomUUID());
        dictionary.addWord(emptyWord);
        assertTrue(dictionary.getWords().contains(emptyWord));
    }

    @Test
    public void testContainsWordExisting() {
        // the word should be in the dictionary!
        assertTrue(dictionary.contains("rana"));
    }

    @Test
    public void testContainsWordAbsent() {
        // make a word that isn't in dictionary, then see if it's in the dictionary
        assertFalse(dictionary.contains("cerdo"));
    }

    @Test
    public void testContainsNullWord() {
        // make nul word then see if it's in the list
        Word w = null;
        assertFalse(dictionary.getWords().contains(w));
    }

    @Test
    public void testContainsWordCaseInsensitive() {
        assertTrue(dictionary.contains("gato"));
    }

    @Test
    public void testContainsEmptyString() {
        assertFalse(dictionary.contains(""));
    }

    @Test
    public void testCointainsWordEmptyDictionary() {
        Dictionary empty = new Dictionary();
        assertFalse(empty.contains("perro"));
    }

    @Test
    public void testGetWordsByUnderstandingLow() {
        ArrayList<Word> lowUnderstanding = dictionary.getWordsByUnderstanding(30.0);
        assertEquals(1, lowUnderstanding.size());
        assertEquals("perro", lowUnderstanding.get(0).getWordinLanguage());
    }

    @Test
    public void testGetWordsByUnderstandingHigh() {
        ArrayList<Word> highUnderstandingWords = dictionary.getWordsByUnderstanding(90.0);
        assertEquals(3, highUnderstandingWords.size());
    }

    @Test
    public void testGetWordsByUnderstandingExactMatch() {
        ArrayList<Word> exactUnderstandingWords = dictionary.getWordsByUnderstanding(20.0);
        assertEquals(1, exactUnderstandingWords.size());
        assertEquals("perro", exactUnderstandingWords.get(0).getWordinLanguage());
    }

    @Test
    public void testGetWordsByUnderstandingNoMatches() {
        ArrayList<Word> noMatchWords = dictionary.getWordsByUnderstanding(10.0);
        assertTrue(noMatchWords.isEmpty());
    }

    @Test
    public void testGetWordsByUnderstandingNegativeThreshold() {
        ArrayList<Word> negativeThresholdWords = dictionary.getWordsByUnderstanding(-10.0);
        assertTrue(negativeThresholdWords.isEmpty());
    }


    @Test
    public void testGetWordsByTopicID() {
        Word w1 = new Word(Languages.SPANISH, "perro", "dog", testTopicID, UUID.randomUUID());
        w1.setUserUnderstanding(20.0);
        Word w2 = new Word(Languages.SPANISH, "gato", "cat", testTopicID, UUID.randomUUID());
        w2.setUserUnderstanding(80.0);

        ArrayList<Word> result = dictionary.getWordsByTopicID(testTopicID);

        assertEquals(2, result.size());
        assertTrue(result.contains(w1) && result.contains(w2));
    }

    @Test
    public void testGetWordsByTopicIDNull() {
        ArrayList<Word> result = dictionary.getWordsByTopicID(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetWordsByTopicIDWithNoMatchingWords() {
        UUID topicID = UUID.randomUUID();
        ArrayList<Word> result = dictionary.getWordsByTopicID(topicID);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testRemoveWordPresent() {
        Word word = new Word(Languages.SPANISH, "burro", "donkey", UUID.randomUUID(), UUID.randomUUID());
        dictionary.addWord(word);
        dictionary.removeWord("burro");
        assertFalse(dictionary.contains("burro"));
    }

    @Test
    public void testRemoveWordAbsent() {
        int initialSize = dictionary.getWords().size();
        dictionary.removeWord("nonexistent");
        assertEquals(initialSize, dictionary.getWords().size());
    }

    @Test
    public void testSetNumberOfWordsZero() {
        dictionary.setNumberOfWords(0);
        assertEquals(0, dictionary.getNumberOfWords());
    }

    @Test
    public void testSetNumberOfWordsNegative() {
        dictionary.setNumberOfWords(-5);
        assertEquals(-5, dictionary.getNumberOfWords());
    }

    @Test
    public void testSetNumberOfWordsMaxInt() {
        dictionary.setNumberOfWords(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, dictionary.getNumberOfWords());
    }

    @Test
    public void testSetIDSameID() {
        UUID sameID = dictionary.getID();
        dictionary.setID(sameID);
        assertEquals(sameID, dictionary.getID());
    }

    @Test
    public void testSetIDNull() {
        UUID id = null;
        dictionary.setID(id);
        assertNull(dictionary.getID());
    }

    @Test
    public void testGetWordByUUIDAbsent() {
        UUID randomID = UUID.randomUUID();
        assertNull(dictionary.getWordByUUID(randomID));
    }

    @Test
    public void testGetWordByStringEmpty() {
        assertNull(dictionary.getWordByString(""));
    }

    @Test
    public void testGetWordByStringNull() {
        assertNull(dictionary.getWordByString(null));
    }

    @Test
    public void testGetWordByStringAbsent() {
        assertNull(dictionary.getWordByString("elefante"));
    }

    @Test
    public void testGetWordByStringCaseInsensitive() {
        Word result = dictionary.getWordByString("PERRO");
        assertNotNull(result);
    }

    @Test
    public void testGetWordByStringValid() {
        Word result = dictionary.getWordByString("perro");
        assertNotNull(result);
    }
}