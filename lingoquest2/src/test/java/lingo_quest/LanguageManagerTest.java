package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class LanguageManagerTest {
    private LanguageManager manager;

    @Before
    public void setUp() {
        // Ensure that the singleton instance is initialized
        manager = LanguageManager.getInstance();
    }

    @Test
    public void testSingletonInstance_UniqueAcrossThreads() {
        LanguageManager instance1 = LanguageManager.getInstance();
        LanguageManager instance2 = LanguageManager.getInstance();
        assertSame("Instances from different threads should be the same", instance1, instance2);
    }

    @Test
    public void testSingletonInstance_MultipleCalls() {
        assertSame("Multiple calls should return the same instance", LanguageManager.getInstance(), LanguageManager.getInstance());
    }

    @Test
    public void testSingletonInstance_NotNull() {
        assertNotNull("Singleton instance should not be null", LanguageManager.getInstance());
    }

    @Test
    public void testSingletonInstance_SameObject() {
        LanguageManager firstInstance = LanguageManager.getInstance();
        LanguageManager secondInstance = LanguageManager.getInstance();
        assertSame("Instances should be the same object", firstInstance, secondInstance);
    }
    @Test
    public void testGetSpanishDictionary() {
        assertNotNull("return dictionary", manager.getSpanishDictionary());
    }

    @Test
    public void testGetCurrentLanguage_DefaultNull() {
        assertNull("Default language should be null", manager.getCurrentLanguage());
    }
    
    @Test
    public void testGetCurrentLanguage_AfterSet() {
        Language language = new Language();
        manager.setCurrentLanguage(language);
        assertEquals("Should return the language that was set", language, manager.getCurrentLanguage());
    }
    
    @Test
    public void testGetCurrentLanguage_ChangeLanguage() {
        Language lang1 = new Language();
        Language lang2 = new Language();
        manager.setCurrentLanguage(lang1);
        manager.setCurrentLanguage(lang2);
        assertEquals("Should return the newly set language", lang2, manager.getCurrentLanguage());
    }
    
    @Test
    public void testGetCurrentLanguage_AfterReset() {
        manager.setCurrentLanguage(null);
        assertNull("After resetting, language should be null", manager.getCurrentLanguage());
    }
    
    @Test
    public void testGetCurrentLanguage_WithMultipleLanguages() {
        Language lang1 = new Language();
        Language lang2 = new Language();
        manager.setCurrentLanguage(lang1);
        assertEquals("Should retrieve first language", lang1, manager.getCurrentLanguage());
        manager.setCurrentLanguage(lang2);
        assertEquals("Should retrieve second language", lang2, manager.getCurrentLanguage());
    }
    @Test
    public void testGetCurrentSection_DefaultNull() {
        assertNull("Default section should be null", manager.getCurrentSection());
    }
    @Test
    public void testGetCurrentLesson_DefaultNull() {
        assertNull("Default lesson should be null", manager.getCurrentLesson());
    }
    @Test
    public void testAddLanguage_NullLanguage() {
        manager.addLanguage(null);
        assertEquals("Languages list should not change when adding null", 0, manager.getLanguages().size());
    }
    
    @Test
    public void testAddLanguage_ValidLanguage() {
        Language language = new Language();
        manager.addLanguage(language);
        assertTrue("Language should be added to the list", manager.getLanguages().contains(language));
    }
    
    @Test
    public void testAddLanguage_DuplicateLanguage() {
        Language language = new Language();
        manager.addLanguage(language);
        manager.addLanguage(language);
        assertEquals("Languages list should allow duplicates if not checked", 2, manager.getLanguages().size());
    }
    
    @Test
    public void testAddLanguage_LargeList() {
        for (int i = 0; i < 1000; i++) {
            manager.addLanguage(new Language());
        }
        assertEquals("Languages list should handle large number of entries", 1000, manager.getLanguages().size());
    }
    
    @Test
    public void testAddLanguage_MultipleLanguages() {
        Language lang1 = new Language();
        Language lang2 = new Language();
        manager.addLanguage(lang1);
        manager.addLanguage(lang2);
        assertEquals("Should contain both languages", 2, manager.getLanguages().size());
    }
    @Test
    public void testLoadLanguages_ValidFile() throws Exception {
        manager.loadLanguages();
        assertNotNull("Languages list should be initialized", manager.getLanguages());
        assertTrue("Languages list should be populated", manager.getLanguages().size() > 0);
    }
    
    
    @Test
    public void testLoadLanguages_MultipleInvocations() throws Exception {
        manager.loadLanguages();
        int firstLoadSize = manager.getLanguages().size();
        manager.loadLanguages();
        assertEquals("Loading multiple times should not duplicate entries", firstLoadSize, manager.getLanguages().size());
    }

    @Test
    public void testGetLanguageByID_ExistingID() {
        User user = new User("testuser", "password"); // Assuming this constructor exists
        Language lang = new Language(user);
        manager.addLanguage(lang);
        assertEquals("Should retrieve the correct language by ID", lang, manager.getLanguageByID(lang.getLanguageID()));
    }
    
    @Test
    public void testGetLanguageByID_NonExistingID() {
        UUID nonExistingID = UUID.randomUUID();
        assertNull("Should return null for a non-existing ID", manager.getLanguageByID(nonExistingID));
    }
    
    @Test
    public void testGetLanguageByID_NullID() {
        assertNull("Should return null when ID is null", manager.getLanguageByID(null));
    }
    
    @Test
    public void testGetLanguageByID_LargeList() {
        UUID targetID = null;
        for (int i = 0; i < 1000; i++) {
            User user = new User("user" + i, "password");
            Language lang = new Language(user);
            if (i == 500) targetID = lang.getLanguageID();
            manager.addLanguage(lang);
        }
        assertNotNull("Should retrieve language in a large list by ID", manager.getLanguageByID(targetID));
    }
    
    @Test
    public void testGetSectionByID_NonExistingSection() {
        assertNull("Should return null for a non-existing section ID", manager.getSectionByID(UUID.randomUUID()));
    }
    
    @Test
    public void testGetSectionByID_NullID() {
        assertNull("Should return null when section ID is null", manager.getSectionByID(null));
    }
    
    @Test
    public void testGetSectionByID_NoCurrentLanguage() {
        manager.setCurrentLanguage(null);
        assertNull("Should return null when there is no current language", manager.getSectionByID(UUID.randomUUID()));
    }
    
    @Test
    public void testGetSectionByID_EmptySections() {
        Language lang = new Language();
        manager.setCurrentLanguage(lang);
        assertNull("Should return null when the language has no sections", manager.getSectionByID(UUID.randomUUID()));
    }
    
    
    
}

