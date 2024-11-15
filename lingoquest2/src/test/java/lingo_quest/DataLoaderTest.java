package lingo_quest;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lingoquestpackage.models.*;

/**
 * @author CADE
 */
public class DataLoaderTest {
    private Users users = Users.getInstance();
    private ArrayList<User> userList = users.getUsers();
    private DictionaryManager dictionaryManager = DictionaryManager.getInstance();
    private ArrayList<Dictionary> dictionaryList = dictionaryManager.getDictionaries();
    private LanguageManager languageManager = LanguageManager.getInstance();
    private ArrayList<Language> languageList = languageManager.getLanguages();
    private ItemShop itemShop = ItemShop.getInstance();
    private ArrayList<Item> itemList = itemShop.getItems();

    // load users
    @Test
    public void testLoadUsersReturnsNonEmptyList() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<User> users = DataLoader.loadUsers(DataLoader.getUserFile());
        assertNotNull(users);
        assertTrue(users.size() > 0, "User list should not be empty after loading.");
    }

    @Test
    public void testLoadUsersHandlesEmptyFileGracefully() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<User> users = DataLoader.loadUsers("empty_users.json");  // Assuming empty test file created
        assertNotNull(users);
        assertEquals(0, users.size(), "User list should be empty if the file is empty.");
    }

    @Test
    public void testLoadUsersHandlesCorruptedFile() {
        assertThrows(ParseException.class, () -> DataLoader.loadUsers("corrupt_users.json"));
    }

    @Test
    public void testLoadUsersWithInvalidDataFormat() {
        assertThrows(ClassCastException.class, () -> DataLoader.loadUsers("invalid_format_users.json"));
    }

    @Test
    public void testLoadUsersWithValidAndInvalidDataMixed() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<User> users = DataLoader.loadUsers("mixed_valid_invalid_users.json");
        assertNotNull(users);
        assertTrue(users.size() > 0, "Mixed file should still return valid user data.");
    }

    // load item shop
    @Test
    public void testLoadItemShopReturnsNonEmptyList() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Item> items = DataLoader.loadItemShop(DataLoader.getItemFile());
        assertNotNull(items);
        assertTrue(items.size() > 0, "Item shop list should not be empty after loading.");
    }

    @Test
    public void testLoadItemShopHandlesEmptyFile() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Item> items = DataLoader.loadItemShop("empty_items.json");
        assertNotNull(items);
        assertEquals(0, items.size(), "Item shop list should be empty if the file is empty.");
    }

    @Test
    public void testLoadItemShopHandlesCorruptFile() {
        assertThrows(ParseException.class, () -> DataLoader.loadItemShop("corrupt_items.json"));
    }

    @Test
    public void testLoadItemShopWithInvalidDataFormat() {
        assertThrows(ClassCastException.class, () -> DataLoader.loadItemShop("invalid_format_items.json"));
    }

    @Test
    public void testLoadItemShopWithValidAndInvalidData() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Item> items = DataLoader.loadItemShop("mixed_valid_invalid_items.json");
        assertNotNull(items);
        assertTrue(items.size() > 0, "Mixed file should still return valid item data.");
    }

    // load words
    @Test
    public void testLoadWordsReturnsNonEmptyList() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Word> words = DataLoader.loadWords(DataLoader.getWordFile());
        assertNotNull(words);
        assertTrue(words.size() > 0, "Words list should not be empty after loading.");
    }

    @Test
    public void testLoadWordsHandlesEmptyFile() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Word> words = DataLoader.loadWords("empty_words.json");
        assertNotNull(words);
        assertEquals(0, words.size(), "Words list should be empty if the file is empty.");
    }

    @Test
    public void testLoadWordsHandlesCorruptFile() {
        assertThrows(ParseException.class, () -> DataLoader.loadWords("corrupt_words.json"));
    }

    @Test
    public void testLoadWordsWithInvalidDataFormat() {
        assertThrows(ClassCastException.class, () -> DataLoader.loadWords("invalid_format_words.json"));
    }

    @Test
    public void testLoadWordsWithValidAndInvalidData() throws IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<Word> words = DataLoader.loadWords("mixed_valid_invalid_words.json");
        assertNotNull(words);
        assertTrue(words.size() > 0, "Mixed file should still return valid word data.");
    }

    // load languages
    @Test
    public void testLoadLanguagesReturnsNonEmptyList() throws Exception {
        ArrayList<Language> languages = DataLoader.loadLanguages(DataLoader.getLanguageFile());
        assertNotNull(languages);
        assertTrue(languages.size() > 0, "Languages list should not be empty after loading.");
    }

    @Test
    public void testLoadLanguagesHandlesEmptyFile() throws Exception {
        ArrayList<Language> languages = DataLoader.loadLanguages("empty_languages.json");
        assertNotNull(languages);
        assertEquals(0, languages.size(), "Languages list should be empty if the file is empty.");
    }

    @Test
    public void testLoadLanguagesHandlesCorruptFile() {
        assertThrows(ParseException.class, () -> DataLoader.loadLanguages("corrupt_languages.json"));
    }

    @Test
    public void testLoadLanguagesWithInvalidDataFormat() {
        assertThrows(ClassCastException.class, () -> DataLoader.loadLanguages("invalid_format_languages.json"));
    }

    @Test
    public void testLoadLanguagesWithValidAndInvalidData() throws Exception {
        ArrayList<Language> languages = DataLoader.loadLanguages("mixed_valid_invalid_languages.json");
        assertNotNull(languages);
        assertTrue(languages.size() > 0, "Mixed file should still return valid language data.");
    }

    // makeword
    @Test
    public void testMakeWordWithValidData() {
        JSONObject wordJson = new JSONObject();
        wordJson.put("language", "Spanish");
        wordJson.put("word", "Hola");
        wordJson.put("englishVersion", "Hello");
        wordJson.put("lessonUUID", UUID.randomUUID().toString());
        wordJson.put("wordUUID", UUID.randomUUID().toString());
        wordJson.put("timesPresented", 5L);
        wordJson.put("timesCorrect", 3L);
        wordJson.put("userUnderstanding", 75.5);
        wordJson.put("points", 100L);

        Word word = DataLoader.makeWord(wordJson);

        assertNotNull(word);
        assertEquals("Hola", word.getWordinLanguage());
        assertEquals("Hello", word.getEnglishVersion());
        assertEquals(Languages.SPANISH, word.getLanguage());
        assertEquals(5, word.getTimesPresented());
        assertEquals(3, word.getTimesCorrect());
        assertEquals(75.5, word.getUserUnderstanding());
        assertEquals(100, word.getPoints());
    }

    @Test
    public void testMakeWordWithMissingField() {
        JSONObject wordJson = new JSONObject();
        wordJson.put("language", "Spanish");
        wordJson.put("word", "Hola");

        Word word = DataLoader.makeWord(wordJson);

        assertNotNull(word);
        assertEquals("Hola", word.getWordinLanguage());
        assertNull(word.getEnglishVersion());
    }

    @Test
    public void testMakeWordWithEmptyJson() {
        JSONObject wordJson = new JSONObject();

        Word word = DataLoader.makeWord(wordJson);

        assertNotNull(word);
        assertNull(word.getWordinLanguage());
    }

    @Test
    public void testMakeWordWithNullLanguage() {
        JSONObject wordJson = new JSONObject();
        wordJson.put("language", null);
        wordJson.put("word", "Hola");

        Word word = DataLoader.makeWord(wordJson);

        assertNotNull(word);
        assertEquals(Languages.DEFAULT, word.getLanguage());
    }

    @Test
    public void testMakeWordWithUnsupportedLanguage() {
        JSONObject wordJson = new JSONObject();
        wordJson.put("language", "unknownLanguage");
        wordJson.put("word", "Hola");

        Word word = DataLoader.makeWord(wordJson);

        assertNotNull(word);
        assertEquals(Languages.DEFAULT, word.getLanguage());
    }
}
