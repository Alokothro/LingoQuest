package lingo_quest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lingoquestpackage.models.*;

/**
 * @author CADE
 */
public class DictionaryManagerTest {
    private DictionaryManager dictionaryManager = DictionaryManager.getInstance();
    private Dictionary testDictionary = new Dictionary();
    private Dictionary testDictionary2 = new Dictionary();
    private UUID testID = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        testDictionary.setID(testID);
    }

    @AfterEach
    public void tearDown() {
        dictionaryManager.getDictionaries().clear();
    }

    // add dictionary tests
    @Test
    public void testAddDictionaryValid() {
        dictionaryManager.addDictionary(testDictionary);
        assertTrue(dictionaryManager.getDictionaries().contains(testDictionary));
    }

    @Test
    public void testAddDictionaryNull() {
        int initialSize = dictionaryManager.getDictionaries().size();
        dictionaryManager.addDictionary(null);
        assertEquals(initialSize, dictionaryManager.getDictionaries().size());
    }

    @Test
    public void testAddDictionaryDuplicate() {
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.addDictionary(testDictionary);
        long count = dictionaryManager.getDictionaries().stream().filter(d -> d.equals(testDictionary)).count();
        assertEquals(2, count);
    }

    @Test
    public void testAddDictionaryEmptyDictionary() {
        Dictionary emptyDictionary = new Dictionary();
        dictionaryManager.addDictionary(emptyDictionary);
        assertTrue(dictionaryManager.getDictionaries().contains(emptyDictionary));
    }

    @Test
    public void testAddDictionaryMultipleDictionaries() {
        Dictionary anotherDictionary = new Dictionary();
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.addDictionary(anotherDictionary);
        assertEquals(2, dictionaryManager.getDictionaries().size());
    }

    // remove dictionary
    @Test
    void testRemoveDictionaryExisting() {
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.removeDictionary(testID);
        assertFalse(dictionaryManager.getDictionaries().contains(testDictionary));
    }

    @Test
    void testRemoveDictionaryNonexistent() {
        int initialSize = dictionaryManager.getDictionaries().size();
        dictionaryManager.removeDictionary(UUID.randomUUID());
        assertEquals(initialSize, dictionaryManager.getDictionaries().size());
    }

    @Test
    void testRemoveDictionaryNull() {
        int initialSize = dictionaryManager.getDictionaries().size();
        dictionaryManager.removeDictionary(null);
        assertEquals(initialSize, dictionaryManager.getDictionaries().size());
    }

    @Test
    void testRemoveDictionaryDuplicate() {
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.removeDictionary(testID);
        assertTrue(dictionaryManager.getDictionaries().isEmpty());
    }

    @Test
    void testRemoveDictionaryEmptyList() {
        dictionaryManager.getDictionaries().clear();
        dictionaryManager.removeDictionary(testID);
        assertTrue(dictionaryManager.getDictionaries().isEmpty());
    }

    // load dictionaries
    @Test
    void testLoadDictionariesValid() {
        dictionaryManager.loadDictionaries();
        assertNotNull(dictionaryManager.getDictionaries());
    }

    @Test
    void testLoadDictionariesNotEmptyAfterLoad() {
        dictionaryManager.loadDictionaries();
        assertFalse(dictionaryManager.getDictionaries().isEmpty());
    }

    @Test
    void testLoadDictionariesIOExceptionHandled() {
        try {
            dictionaryManager.loadDictionaries();
        } catch (Exception e) {
            fail("IOException should be handled within loadDictionaries");
        }
    }

    @Test
    void testLoadDictionariesNoDuplicatesAfterLoad() {
        dictionaryManager.loadDictionaries();
        // only count dictionaries that are distinct (no duplicates)
        long uniqueDictionaries = dictionaryManager.getDictionaries().stream().distinct().count();
        // unique count should = total count
        assertEquals(uniqueDictionaries, dictionaryManager.getDictionaries().size());
    }

    // saveDictionary
    @Test
    void testSaveDictionaryValidFile() {
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.saveDictionary();
        dictionaryManager.loadDictionaries();
        // load the dictionaries back in, should only hold test dictionary
        assertTrue(dictionaryManager.getDictionaryByID(testDictionary.getID()) != null);
    }

    @Test
    void testSaveDictionaryEmptyList() {
        dictionaryManager.getDictionaries().clear();
        dictionaryManager.saveDictionary();
        dictionaryManager.loadDictionaries();
        // save the dictionaries as an empty list, then load them back in
        assertTrue(dictionaryManager.getDictionaries().isEmpty());
    }

    @Test
    void testSaveDictionaryWithNullDictionary() {
        int initialSize = dictionaryManager.getDictionaries().size();
        dictionaryManager.addDictionary(null);
        dictionaryManager.saveDictionary();
        dictionaryManager.getDictionaries().clear();
        dictionaryManager.loadDictionaries();
        assertEquals(initialSize, dictionaryManager.getDictionaries().size());
    }

    @Test
    void testSaveDictionaryOnlyValidDictionariesSaved() {
        int initialSize = dictionaryManager.getDictionaries().size();
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.addDictionary(null);
        dictionaryManager.saveDictionary();
        assertEquals(initialSize, dictionaryManager.getDictionaries().size());
    }

    // get dictionary by id
    @Test
    void testGetDictionaryByIDExisting() {
        dictionaryManager.addDictionary(testDictionary);
        assertEquals(testDictionary, dictionaryManager.getDictionaryByID(testID));
    }

    @Test
    void testGetDictionaryByIDNonexistent() {
        assertNull(dictionaryManager.getDictionaryByID(UUID.randomUUID()));
    }

    @Test
    void testGetDictionaryByIDNull() {
        assertNull(dictionaryManager.getDictionaryByID(null));
    }

    @Test
    void testGetDictionaryByIDAfterRemoval() {
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.removeDictionary(testID);
        assertNull(dictionaryManager.getDictionaryByID(testID));
    }

    @Test
    void testGetDictionaryByIDWithMultipleDictionaries() {
        Dictionary anotherDictionary = new Dictionary();
        UUID anotherID = UUID.randomUUID();
        anotherDictionary.setID(anotherID);
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.addDictionary(anotherDictionary);
        assertEquals(anotherDictionary, dictionaryManager.getDictionaryByID(anotherID));
    }

    // get dictionary by user
    @Test
    void testGetDictionaryByUserWithExistingUserDictionary() {
        User user = new User("username", "password");
        user.setUserDictionaryID(testID);
        dictionaryManager.addDictionary(testDictionary);
        assertEquals(testDictionary, dictionaryManager.getDictionaryByUser(user));
    }

    @Test
    void testGetDictionaryByUserWithNoDictionaryID() {
        User user = new User("username", "password");
        user.setUserDictionaryID(null);
        assertNull(dictionaryManager.getDictionaryByUser(user));
    }

    @Test
    void testGetDictionaryByUserWithNonexistentDictionary() {
        User user = new User("username", "password");
        user.setUserDictionaryID(UUID.randomUUID());
        assertNull(dictionaryManager.getDictionaryByUser(user));
    }

    @Test
    void testGetDictionaryByUserNullUser() {
        assertNull(dictionaryManager.getDictionaryByUser(null));
    }

    @Test
    void testGetDictionaryByUserAfterRemoval() {
        User user = new User("username", "password");
        user.setUserDictionaryID(testID);
        dictionaryManager.addDictionary(testDictionary);
        dictionaryManager.removeDictionary(testID);
        assertNull(dictionaryManager.getDictionaryByUser(user));
    }

    // duplicate dictionary
    @Test
    void testDuplicateDictionaryValidID() {
        dictionaryManager.addDictionary(testDictionary);
        Dictionary duplicate = dictionaryManager.duplicateDictionary(testID);
        assertNotNull(duplicate);
        assertNotEquals(testDictionary, duplicate);
    }

    @Test
    void testDuplicateDictionaryNullID() {
        assertNull(dictionaryManager.duplicateDictionary(null));
    }

    @Test
    void testDuplicateDictionaryNonexistentID() {
        assertNull(dictionaryManager.duplicateDictionary(UUID.randomUUID()));
    }

    @Test
    void testDuplicateDictionaryContainsSameWords() {
        testDictionary.addWord(new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID()));
        dictionaryManager.addDictionary(testDictionary);
        Dictionary duplicate = dictionaryManager.duplicateDictionary(testID);
        assertEquals(testDictionary.getWords(), duplicate.getWords());
    }

    @Test
    void testDuplicateDictionaryAddedToManager() {
        dictionaryManager.addDictionary(testDictionary);
        Dictionary duplicate = dictionaryManager.duplicateDictionary(testID);
        assertTrue(dictionaryManager.getDictionaries().contains(duplicate));
    }
}
