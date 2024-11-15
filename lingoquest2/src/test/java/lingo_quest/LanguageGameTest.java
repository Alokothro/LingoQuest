package lingo_quest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class LanguageGameTest {
    // create user
    @Test
    public void testCreateUserValidNewUser() throws Exception {
        LanguageGame game = createInitializedGame();
        game.createUser("newUser1111", "password123");
        assertNotNull(game.getUser());
        assertEquals("newUser1111", game.getUser().getUsername());
    }

    @Test
    public void testCreateUserWhenAlreadyLoggedIn() throws Exception {
        LanguageGame game = createInitializedGame();
        game.createUser("existingUser", "password123");
        game.createUser("newUser", "password123");
        assertEquals("existingUser", game.getUser().getUsername());
        assertNotEquals("newUser", game.getUser().getUsername());
    }

    @Test
    public void testCreateUserExistingUsername() throws Exception {
        LanguageGame game = createInitializedGame();
        game.createUser("existingUser", "password123");
        game.logout();
        game.createUser("existingUser", "password123");
        assertNull(game.getUser());
    }

    @Test
    public void testCreateUserDefaultLanguageSetToSpanish() throws Exception {
        LanguageGame game = createInitializedGame();
        game.createUser("newUser1111", "password123");

        assertEquals(Languages.SPANISH, game.getUser().getCurrentLanguage().getLanguageName());
    }

    @Test
    public void testCreateUserSetsInitialLanguage() throws Exception {
        LanguageGame game = createInitializedGame();;
        game.createUser("newUser", "password123");
        assertNotNull(game.getUser().getCurrentLanguage());
    }

    // practice low understanding
    // BUG WHERE CREATED USER HAS NULL CURRENT LANGUAGE
    @Test
    public void testPracticeLowUnderstandingSetsLesson() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("testUser1111", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);
        Dictionary dictionary = new Dictionary();
        user.setUserDictionary(dictionary);

        Word word = new Word();
        word.setUserUnderstanding(50.0);
        word.setTimesPresented(1);
        dictionary.addWord(word);

        game.practiceLowUnderstanding();

        assertNotNull(user.currentLesson);
    }

    @Test
    public void testPracticeLowUnderstandingSetsLessonTopicWords() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("testUser1111", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);
        Dictionary dictionary = new Dictionary();
        user.setUserDictionary(dictionary);

        Word word = new Word();
        word.setUserUnderstanding(50.0);
        dictionary.addWord(word);

        game.practiceLowUnderstanding();

        assertTrue(user.currentLesson.topicWords.contains(word));
    }

    @Test
    public void testPracticeLowUnderstandingHandlesNoLowUnderstandingWords() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("testUser", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);
        Dictionary dictionary = new Dictionary();
        user.setUserDictionary(dictionary);

        game.practiceLowUnderstanding();

        assertNull(user.currentLesson);  // No words available, lesson should not be set
    }

    @Test
    public void testPracticeLowUnderstandingAssignsLanguageToLesson() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("testUser1111", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);

        Language language = new Language();
        user.setCurrentLangauge(language);

        Dictionary dictionary = new Dictionary();
        user.setUserDictionary(dictionary);

        Word word = new Word();
        word.setUserUnderstanding(50.0);
        word.setTimesPresented(1);
        dictionary.addWord(word);

        game.practiceLowUnderstanding();

        assertEquals(language.getLanguageID(), user.currentLesson.getLanguageID());
    }

    @Test
    public void testPracticeLowUnderstandingSetsLessonProgressToZero() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("testUser", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);
        Dictionary dictionary = new Dictionary();
        user.setUserDictionary(dictionary);

        Word word = new Word();
        word.setUserUnderstanding(50.0);
        word.setTimesPresented(1);
        dictionary.addWord(word);

        game.practiceLowUnderstanding();

        assertEquals(0, user.currentLesson.getLessonProgress());
    }

    // login
    @Test
    public void testLoginWithValidCredentials() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("validUser1111", "password123");
        game.getUsers().addUser(user);

        game.login("validUser1111", "password123");

        assertEquals(user, game.getUser());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        LanguageGame game = createInitializedGame();

        game.login("invalidUser1111", "wrongPassword");

        assertNull(game.getUser());
    }

    @Test
    public void testLoginInitializesTopicWords() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("validUser", "password123");
        game.getUsers().addUser(user);

        Language language = new Language();
        Section section = new Section();
        Lesson lesson = new Lesson();
        section.setLessons(new ArrayList<>(List.of(lesson)));
        language.setSections(new ArrayList<>(List.of(section)));

        game.setLanguageManager(LanguageManager.getInstance());
        game.getLanguageManager().addLanguage(language);

        game.login("validUser", "password123");

        assertNotNull(lesson.topicWords);
    }

    @Test
    public void testLoginSetsCurrentUserInGame() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("validUser", "password123");
        game.getUsers().addUser(user);

        game.login("validUser", "password123");

        assertEquals(user, game.getUser());
    }

    @Test
    public void testLoginFailsIfUserNotInList() throws Exception {
        LanguageGame game = createInitializedGame();

        game.login("unknownUser", "password123");

        assertNull(game.getUser());
    }

    // logout
    @Test
    public void testLogoutSetsUserToNull() throws Exception {
        LanguageGame game = createInitializedGame();
        User user = new User("loggedUser", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);

        game.logout();

        assertNull(game.getUser());
    }

    @Test
    public void testLogoutPersistsUserList() throws Exception {
        LanguageGame game = createInitializedGame();

        User user = new User("loggedUser", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);

        game.logout();

    }

    @Test
    public void testLogoutResetsCurrentUser() throws Exception {
        LanguageGame game = createInitializedGame();

        User user = new User("loggedUser", "password123");
        game.getUsers().addUser(user);
        game.setUser(user);

        game.logout();

        assertNull(game.getUser());
    }

    @Test
    public void testLogoutPrintsSuccessMessage() throws Exception {
        LanguageGame game = createInitializedGame();

        game.logout();
    }

    // METHOD TO REPLACE WHAT THE BEFORE EACH SHOULD DO
    private LanguageGame createInitializedGame() throws Exception {
        LanguageGame game = new LanguageGame();
        game.setUsers(Users.getInstance());
        game.setDictionaryManager(DictionaryManager.getInstance());
        game.setItemShop(ItemShop.getInstance());
        game.setLanguageManager(LanguageManager.getInstance());
        game.createUser("senoritaAwesome", "password445");
        game.logout();
        return game;
    }
}
