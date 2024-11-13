package lingo_quest;

import java.util.ArrayList;
import java.util.UUID;
/**
 * Manages languages, sections, and lessons within a language learning application.
 * Implements Singleton pattern to ensure only one instance of this manager exists.
 */
public class LanguageManager {
    private ArrayList<Language> languages;  // Corrected to match UML
    private static LanguageManager languageManager;
    private Language currentLanguage;
    private Section currentSection;
    private Lesson currentLesson;
    private final String spanishDictionary = "eeaaed42-a1be-4477-bc7c-2f6f2be2993b";
    /**
     * Provides access to the singleton instance of the LanguageManager.
     * 
     * @return the singleton instance of LanguageManager
     */
    public static LanguageManager getInstance() {
        if (languageManager == null) {
            languageManager = new LanguageManager();
        }
        return languageManager;
    }
    
    /**
     * Retrieves the ID of the predefined Spanish dictionary.
     * 
     * @return String representing the UUID of the Spanish dictionary.
     */
    public String getSpanishDictionary() {
        return this.spanishDictionary;
    }
    /**
     * Sets the current language for the language manager session.
     * 
     * @param language The Language object to set as the current language.
     */
    // THIS PROBABLY ISN'T RIGHT AND SHOULD BE FIXED
    public void setCurrentLanguage(Language language) {
        this.currentLanguage = getLanguageByID(language.getLanguageID());
    }
    /**
     * Retrieves the current active language.
     * 
     * @return the currently active Language object.
     */

    public Language getCurrentLanguage() {
        return currentLanguage;
    }
    /**
     * Retrieves the current active section.
     * 
     * @return the currently active Section object.
     */
    public Section getCurrentSection() {
        return currentSection;
    }
    /**
     * Retrieves the current active lesson.
     * 
     * @return the currently active Lesson object.
     */
    public Lesson getCurrentLesson() {
        return currentLesson;
    }
    /**
     * Retrieves the list of available languages.
     * 
     * @return ArrayList of Language objects.
     */
    // Method to get the list of languages
    public ArrayList<Language> getLanguages() {
        return languages;  // Stub return to match expected behavior
    }

    // Method to add a section to a language
    public void addSection(Section section, Language language) {
        // Stub implementation
    }

    // Method to add a lesson to a section in a language
    public void addLesson(Lesson lesson, Section section, Language language) {
        // Stub implementation
    }
    /**
     * Adds a new language to the list of available languages.
     * 
     * @param language The Language object to be added.
     */
    // Method to add a language
    public void addLanguage(Language language) {
        if(language != null)
            languages.add(language);
    }
    /**
     * Loads languages from a data source.
     * 
     * @throws Exception if there is an issue in loading languages.
     */
    // Method to load languages
    public void loadLanguages() throws Exception {
        languages = DataLoader.loadLanguages(DataLoader.getLanguageFile());
    }

    /**
     * @author cade
     * calls on datawriter
     */
    public void saveLanguages() {
        DataWriter.writeLanguages(languages,DataWriter.getLanguageFile());
    }
    
    /**
     * Retrieves a language by its UUID.
     * 
     * @param id UUID of the language to retrieve.
     * @return the Language object if found, null otherwise.
     */
    public Language getLanguageByID(UUID id) {
        if(this.languages != null) {
            for(Language l : languages) {
                if(l.getLanguageID().equals(id)) {
                    this.currentLanguage = l;
                    return l;
                }
            }
            //System.out.println("Language not found.");
            return null;
        }
        //System.out.println("List of languages is empty in LanguageManager");
        this.languages = new ArrayList<Language>();
        return null;
    }
    /**
     * Retrieves a section by its UUID.
     * 
     * @param sectionId UUID of the section to retrieve.
     * @return the Section object if found, null otherwise.
     */
    public Section getSectionByID(UUID sectionId) {
        if (this.currentLanguage != null && this.currentLanguage.getSections() != null) {
            for (Section section : this.currentLanguage.getSections()) {
                if (section.getID().equals(sectionId)) {
                    this.currentSection = section;
                    return section; 
                }
            }
            System.out.println("Section not found in the current language.");
            return null;
        }
        System.out.println("Current language is not set or has no sections.");
        return null;
    }
    /**
     * Retrieves a lesson by its UUID and sets up topic words based on user's dictionary.
     * 
     * @param lessonId UUID of the lesson to retrieve.
     * @param u User object to fetch topic words from user's dictionary.
     * @return the Lesson object if found, null otherwise.
     */
    public Lesson getLessonByID(UUID lessonId, User u) {
        if (this.currentSection != null && this.currentSection.getAllLessons() != null) {
            
            for(Lesson l : this.currentSection.getAllLessons()) {
                l.setTopicWords(u);
            }


            for (Lesson lesson : this.currentSection.getAllLessons()) {
                if (lesson.getLessonID().equals(lessonId)) {
                    this.currentLesson = lesson; 
                    return lesson;  
                }
            }
            System.out.println("Lesson not found in the current section.");
            return null;
        }
        System.out.println("Current section is not set or has no lessons.");
        return null;
    }
}

