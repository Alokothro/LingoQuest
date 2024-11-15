package lingo_quest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import lingoquestpackage.models.*;

/**
 * @author CADE
 */
public class DataWriterTest {
    DataWriter writer = new DataWriter();
    private Users users = Users.getInstance();
    private ArrayList<User> usersList;
    private ArrayList<Word> words;
    private ArrayList<Item> items;
    private ArrayList<Language> languages;
    private ArrayList<Dictionary> dictionaries;
    private User user;
    private Word word;
    private Item item;
    private Language language;
    private Dictionary dictionary;

    @BeforeEach
    public void setup() {
        usersList = new ArrayList<>();
        words = new ArrayList<>();
        items = new ArrayList<>();
        languages = new ArrayList<>();
        dictionaries = new ArrayList<>();

        user = new User("testUser", "testPassword123");
        word = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        item = new Item("Sword", "A sharp weapon", 150);
        language = new Language();
        dictionary = new Dictionary();

        usersList.add(user);
        words.add(word);
        items.add(item);
        languages.add(language);
        dictionaries.add(dictionary);
    }

    @AfterEach
    public void tearDown() {
        usersList.clear();
        words.clear();
        items.clear();
        languages.clear();
        dictionaries.clear();
    }

    // writeUsers
    @Test
    public void testWriteUsersNonNull() {
        assertDoesNotThrow(() -> DataWriter.writeUsers(users.getUsers(), DataWriter.getUserFile()));
    }

    @Test
    public void testWriteUsersHandlesSpecialCharactersInUsername() {
        User specialCharUser = new User("usérñåme", "specialPass456");
        specialCharUser.addCoins(50);
        users.addUser(specialCharUser);
        assertDoesNotThrow(() -> DataWriter.writeUsers(users.getUsers(), DataWriter.getUserFile()));
    }



    @Test
    public void testWriteUsersEmptyList() {
        ArrayList<User> emptyUsers = new ArrayList<>();
        assertDoesNotThrow(() -> DataWriter.writeUsers(emptyUsers, DataWriter.getUserFile()));
    }

    @Test
    public void testWriteUsersInvalidFilePath() {
        assertDoesNotThrow(() -> DataWriter.writeUsers(usersList, "invalid/path.json"));
    }

    // writewords
    @Test
    public void testWriteWordsNonNull() {
        words = new ArrayList<Word>();
        word = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        words.add(word);
        assertDoesNotThrow(() -> DataWriter.writeWords(words, DataWriter.getWordFile()));
    }

    @Test
    public void testWriteWordsCreatesValidJSON() {
        words = new ArrayList<Word>();
        word = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        words.add(word);
        DataWriter.writeWords(words, DataWriter.getWordFile());
        assertEquals("perro", words.get(0).getWordinLanguage());
    }

    @Test
    public void testWriteWordsEmptyList() {
        ArrayList<Word> emptyWords = new ArrayList<>();
        assertDoesNotThrow(() -> DataWriter.writeWords(emptyWords, DataWriter.getWordFile()));
    }

    @Test
    public void testWriteWordsInvalidFilePath() {
        words = new ArrayList<Word>();
        word = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        words.add(word);
        assertDoesNotThrow(() -> DataWriter.writeWords(words, "invalid/path.json"));
    }

    @Test
    public void testSerializeWordContainsLanguage() throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
        words = new ArrayList<Word>();
        word = new Word(Languages.SPANISH, "perro", "dog", UUID.randomUUID(), UUID.randomUUID());
        words.add(word);
        DataWriter.writeWords(words, DataWriter.getWordFile());
        ArrayList<Word> wordList = DataLoader.loadWords(DataLoader.getWordFile());
        assertEquals(wordList.get(0).getLanguage(), Languages.SPANISH);
    }

    // writeItems
    @Test
    public void testWriteItemsCreatesValidJSON() {
        items = new ArrayList<>();
        item = new Item("Sword", "A sharp weapon", 150);
        items.add(item);
        DataWriter.writeItems(items, DataWriter.getItemFile());
        JSONObject serializedItem = new JSONObject();
        serializedItem.put("name", item.getName());
        serializedItem.put("description", item.getDescription());
        serializedItem.put("price", item.getPrice());
        assertEquals("Sword", serializedItem.get("name"));
    }

    @Test
    public void testWriteItemsEmptyList() {
        ArrayList<Item> emptyItems = new ArrayList<>();
        assertDoesNotThrow(() -> DataWriter.writeItems(emptyItems, DataWriter.getItemFile()));
    }

    @Test
    public void testWriteItemsInvalidFilePath() {
        assertDoesNotThrow(() -> DataWriter.writeItems(items, "invalid/path.json"));
    }

    @Test
    public void testWriteItemsContainsPrice() {
        items = new ArrayList<>();
        item = new Item("Sword", "A sharp weapon", 150);
        items.add(item);
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("price", item.getPrice());
        assertEquals(150, jsonItem.get("price"));
    }

    // writeLanguages
    @Test
    public void testWriteLanguagesNonNull() {
        assertDoesNotThrow(() -> DataWriter.writeLanguages(languages, DataWriter.getLanguageFile()));
    }

    @Test
    public void testWriteLanguagesCreatesValidJSON() {
        DataWriter.writeLanguages(languages, DataWriter.getLanguageFile());
        JSONObject serializedLanguage = DataWriter.serializeLanguage(language);
        assertNotNull(serializedLanguage.get("languageName"));
    }

    @Test
    public void testWriteLanguagesEmptyList() {
        ArrayList<Language> emptyLanguages = new ArrayList<>();
        assertDoesNotThrow(() -> DataWriter.writeLanguages(emptyLanguages, DataWriter.getLanguageFile()));
    }

    @Test
    public void testWriteLanguagesInvalidFilePath() {
        assertDoesNotThrow(() -> DataWriter.writeLanguages(languages, "invalid/path.json"));
    }

    @Test
    public void testSerializeLanguageContainsUserID() {
        language.setUserID(UUID.randomUUID());
        JSONObject jsonLanguage = DataWriter.serializeLanguage(language);
        assertNotNull(jsonLanguage.get("userID"));
    }

    // writeDictionaries
    @Test
    public void testWriteDictionariesNonNull() {
        assertDoesNotThrow(() -> DataWriter.writeDictionaries(dictionaries, DataWriter.getDictionaryFile()));
    }

    @Test
    public void testWriteDictionariesCreatesValidJSON() {
        DataWriter.writeDictionaries(dictionaries, DataWriter.getDictionaryFile());
        JSONObject serializedDictionary = DataWriter.serializeDictionary(dictionary);
        assertNotNull(serializedDictionary.get("dictionaryID"));
    }

    @Test
    public void testWriteDictionariesEmptyList() {
        ArrayList<Dictionary> emptyDictionaries = new ArrayList<>();
        assertDoesNotThrow(() -> DataWriter.writeDictionaries(emptyDictionaries, DataWriter.getDictionaryFile()));
    }

    @Test
    public void testWriteDictionariesInvalidFilePath() {
        assertDoesNotThrow(() -> DataWriter.writeDictionaries(dictionaries, "invalid/path.json"));
    }

    @Test
    public void testSerializeDictionaryContainsWords() {
        dictionary.getWords().add(word);
        JSONObject jsonDictionary = DataWriter.serializeDictionary(dictionary);
        assertTrue(((JSONArray) jsonDictionary.get("words")).size() > 0);
    }
}
