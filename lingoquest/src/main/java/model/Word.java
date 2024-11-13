package lingo_quest;

import java.util.UUID;
import java.util.function.BooleanSupplier;

/**
 * The {@code Word} class represents a word and tracks how many times it has
 * been presented and answered correctly by a user. It also calculates the
 * user's understanding based on their performance.
 * 
 * @author Alok Patel
 */
public class Word {
    private int timesPresented;
    private String word;
    private int points;
    private int timesCorrect;
    private double userUnderstanding;
    private Languages language;
    private String englishVersion;
    public UUID wordUUID;
    public UUID lessonUUID;

    /**
     * Default constructor that initializes the word and other fields to default
     * values.
     */
    public Word() {
        this.word = "";
        this.timesPresented = 0;
        this.timesCorrect = 0;
        this.userUnderstanding = 0.0;
        this.language = Languages.DEFAULT;
        this.englishVersion = "";
        this.wordUUID = UUID.randomUUID();
        this.lessonUUID = UUID.randomUUID();
        this.points = 0;
    }

    /**
     * Is the paramaterized constructor and sets default values as well as the items
     * being read in from the JSON file
     * 
     * @param language       The language that this word is a part of
     * @param word           The word in string format
     * @param englishVersion The english translation of the word
     * @param lessonUUID     The UUID for the lesson this word is related to
     * @param wordUUID       The words UUID (mostly used for matching)
     */
    public Word(Languages language, String word, String englishVersion, UUID lessonUUID, UUID wordUUID) {
        this.points = 0;
        this.language = language;
        this.word = word;
        this.englishVersion = englishVersion;
        this.timesPresented = 0;
        this.timesCorrect = 0;
        this.userUnderstanding = 0.0;
        this.lessonUUID = lessonUUID;
        this.wordUUID = wordUUID;
    }

    public int getPoints() {
        return this.points;
    }

    public UUID getWordUUID() {
        return this.wordUUID;
    }

    /**
     * Sets the word.
     * 
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @author cade
     * @param id
     */
    public void setLessonID(UUID id) {
        if (id != null)
            this.lessonUUID = id;
    }

    /**
     * @author cade
     * @return the lessonid
     */
    public UUID getLessonID() {
        return this.lessonUUID;
    }

    /**
     * @author cade
     * @param p points
     */
    public void setPoints(int p) {
        this.points = p;
    }

    /**
     * @author cade
     * @return englishWord
     */
    public String getEnglishVersion() {
        return this.englishVersion;
    }

    /**
     * @author
     * @param englishWord
     */
    public void setEnglishVersion(String englishWord) {
        this.englishVersion = englishWord;
    }

    /**
     * @author cade
     * @param times
     */
    public void setTimesPresented(int times) {
        this.timesPresented = times;
    }

    /**
     * @author cade
     * @param times
     */
    public void setTimesCorrect(int times) {
        this.timesCorrect = times;
    }

    /**
     * @author cade
     * @param understanding
     */
    public void setUserUnderstanding(double understanding) {
        this.userUnderstanding = understanding;
    }

    /**
     * Gets the number of times the word has been presented to the user.
     * 
     * @return the number of times the word has been presented
     */
    public int getTimesPresented() {
        return timesPresented;
    }

    /**
     * Gets the number of times the word has been answered correctly by the user.
     * 
     * @return the number of correct answers
     */
    public int getTimesCorrect() {
        return timesCorrect;
    }

    /**
     * Gets the user's understanding of the word based on their performance.
     * 
     * @return the user's understanding as a percentage
     */
    public double getUserUnderstanding() {
        return userUnderstanding;
    }

    /**
     * Updates the word's presentation status and whether the user answered it
     * correctly. Also updates the user's understanding.
     * 
     * @param correct a boolean indicating whether the user's response was correct
     */
    public void wordPresented(boolean correct) {
        updateTimesPresented();

        if (correct) {

            points = Math.min(points + 100, 400);
            System.out.println("You are correct");
            updateTimesCorrect();
        } else {

            points = Math.max(points - 100, 0);
            System.out.println("You are incorrect");
        }

        updateUserUnderstanding();
    }

    /**
     * Increments the count of how many times the word has been presented.
     */
    private void updateTimesPresented() {
        this.timesPresented++;
    }

    /**
     * Increments the count of how many times the word has been answered correctly.
     */
    private void updateTimesCorrect() {
        this.timesCorrect++;
    }

    /**
     * Updates the user's understanding of the word as a percentage of correct
     * answers over total presentations.
     */
    private void updateUserUnderstanding() {
        if (timesPresented > 0) {
            this.userUnderstanding = ((double) timesCorrect / timesPresented) * 100;
        } else {
            this.userUnderstanding = 0.0;
        }
    }

    /**
     * Returns a string representation of the word, showing how many times it was
     * presented, answered correctly, and the user's understanding.
     * 
     * @return a string with details of the word
     */
    @Override
    public String toString() {
        return "Word Details:\n" +
                "------------------------\n" +
                "Word: " + word + " (" + language + ")\n" +
                "English Version: " + englishVersion + "\n" +
                "Points: " + points + "\n" +
                "Times Presented: " + timesPresented + "\n" +
                "Times Correct: " + timesCorrect + "\n" +
                "User Understanding: " + String.format("%.2f", userUnderstanding) + "%\n" +
                "------------------------";
    }

    /**
     * Gets the word as a string.
     * 
     * @return the word
     */
    public String getWordinLanguage() {
        return this.word;
    }

    /**
     * Compares this word to another word, ignoring case.
     * 
     * @param otherWord the word to compare with
     * @return {@code true} if the words are equal (case-insensitive),
     *         {@code false} otherwise
     */
    public boolean isEqualTo(Word otherWord) {
        return this.word.equalsIgnoreCase(otherWord.getWordinLanguage());
    }

    /**
     * @author CADE STOCKER
     * @return the language the word belongs to
     */
    public Languages getLanguage() {
        return this.language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public BooleanSupplier isWordPresented() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWordPresented'");
    }
}
