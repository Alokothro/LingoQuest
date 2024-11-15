package lingo_quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lingoquestpackage.models.*;


import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {
    private Language language;
    private User user;
    private Section section1;
    private Section section2;

    @BeforeEach
    void setUp() {
        user = new User(); // Assume User has a default constructor
        language = new Language(user);
        section1 = new Section();
        section2 = new Section();
        
        // Add sections to the language for testing
        language.setSections(new ArrayList<>(List.of(section1, section2)));
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(language.getSections());
        assertNotNull(language.getSectionsComplete());
      
        assertNotNull(language.getLanguageID());
        assertEquals(0, language.getPointsEarned());
        assertEquals(0, language.getTotalPoints());
        assertEquals(0.0, language.getProgress());
        assertEquals(0, language.getAnswerStreak());
        assertFalse(language.takenPlacementTest());
    }

    @Test
    void testParameterizedConstructor() {
        Language langWithUser = new Language(user);
        assertEquals(user, langWithUser.getUser());
        assertNotNull(langWithUser.getLanguageID());
        assertFalse(langWithUser.takenPlacementTest());
    }

    @Test
    void testSetAndGetPlacementTestID() {
        UUID testID = UUID.randomUUID();
        language.setPlacementTestID(testID);
        assertEquals(testID, language.getPlacementTestID());
    }

    @Test
    void testSetAndGetUserID() {
        UUID userID = UUID.randomUUID();
        language.setUserID(userID);
        assertEquals(userID, language.getUserID());
    }

    @Test
    void testSetAndGetPointsEarned() {
        language.setPointsEarned(100);
        assertEquals(100, language.getPointsEarned());
    }

    @Test
    void testSetAndGetTotalPoints() {
        language.setTotalPoints(200);
        assertEquals(200, language.getTotalPoints());
    }

    @Test
    void testUpdateProgress() {
        language.setTotalPoints(200);
        language.setPointsEarned(100);
        assertEquals(0.5, language.getProgress()); // 100/200 = 0.5

        language.setPointsEarned(0);
        assertEquals(0.0, language.getProgress()); // 0/200 = 0.0

        language.setTotalPoints(0);
        assertEquals(0.0, language.getProgress()); // 0/0 = 0.0
    }

    @Test
    void testIncreaseAnswerStreak() {
        language.increaseAnswerStreak();
        assertEquals(1, language.getAnswerStreak());
        language.increaseAnswerStreak();
        assertEquals(2, language.getAnswerStreak());
    }

    @Test
    void testResetAnswerStreak() {
        language.increaseAnswerStreak();
        language.resetAnswerStreak();
        assertEquals(0, language.getAnswerStreak());
    }

    @Test
    void testSetLanguageName() {
        language.setLanguageName(Languages.SPANISH);
        assertEquals(Languages.SPANISH, language.getLanguageName());
        assertNotNull(language.getDictionary());
    }



    @Test
    void testSetSectionsComplete() {
        section1.setSectionComplete(true); // Assume there's a method to set completion status
        language.setSections(new ArrayList<>(List.of(section1, section2)));
        language.setSectionsComplete();
        assertTrue(language.getSectionsComplete().contains(section1));
        assertFalse(language.getSectionsComplete().contains(section2)); // section2 should not be completed
    }

    @Test
    void testSetSectionsCompleteWithNull() {
        language.setSections(null);
        language.setSectionsComplete();
        assertTrue(language.getSectionsComplete().isEmpty()); // Should not throw an exception and remain empty
    }

    
    @Test
    void testSetAndGetDictionaryID() {
        UUID dictID = UUID.randomUUID();
        language.setDictionaryID(dictID);
        assertEquals(dictID, language.getDictionaryID());
    }

    @Test
    void testSetNullDictionaryID() {
        language.setDictionaryID(null);
        assertNull(language.getDictionaryID()); // Should remain null when set to null
    }

   

    @Test
    void testInvalidTotalPoints() {
        language.setTotalPoints(-50); // Assuming this is an invalid operation
        assertEquals(0, language.getTotalPoints()); // Should remain at 0
    }

    @Test
    void testInvalidPointsEarned() {
        language.setPointsEarned(-10); // Assuming negative points are invalid
        assertEquals(0, language.getPointsEarned()); // Should remain at 0
    }
}
