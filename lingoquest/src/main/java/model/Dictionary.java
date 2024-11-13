package lingo_quest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The Dictionary class provides functionality for storing and managing word
 * translations
 * between English and another language. It maintains two HashMaps for
 * bidirectional translation.
 * 
 * 10/22 We have changed the design of the Word class, meaning that dictionary
 * will now be restructured as an ArrayList of words
 */
class Dictionary {
    private ArrayList<Word> words;
    private UUID dictionaryID;
    private int numberOfWords;

    /**
     * Constructs an empty Dictionary with no words.
     * creates empty arraylist
     * 
     * @author Preston Willis
     */
    public Dictionary() {
        this.words = new ArrayList<Word>();
        this.dictionaryID = UUID.randomUUID();
        numberOfWords = 0;
    }

    /**
     * @author cade
     * @param words
     */
    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    /**
     * @author cade
     * @param i
     */
    public void setNumberOfWords(int i) {
        this.numberOfWords = i;
    }

    /**
     * @author cade
     * @return the arraylist of words
     */
    public ArrayList<Word> getWords() {
        return this.words;
    }

    /**
     * @author Cade
     * @return the dictionary's id
     */
    public UUID getID() {
        return this.dictionaryID;
    }

    /**
     * @author cade
     * @param id
     */
    public void setID(UUID id) {
        this.dictionaryID = id;
    }

    /**
     * @author cade
     * @param maxPointsInclusive
     * @return list of words that are equal to or below the specified number
     *         of points
     */
    public ArrayList<Word> getWordsByUnderstanding(double maxUnderstandingInclusive) {
        ArrayList<Word> words = new ArrayList<>();
        for (Word w : this.words) {
            if (w.getPoints() <= maxUnderstandingInclusive)
                words.add(w);
        }
        return words;
    }

    /**
     * @author cade
     * @param id
     */
    public void setID(String id) {
        this.dictionaryID = UUID.fromString(id);
    }

    /**
     * Constructs a Dictionary with predefined word mappings and a word count.
     *
     * @param word1         HashMap for translating from English to another
     *                      language.
     * @param word2         HashMap for translating from another language to
     *                      English.
     * @param numberOfWords Number of words initially present in the dictionary.
     */
    public Dictionary(ArrayList<Word> words, int numberOfWords) {
        this.words = words;
        this.numberOfWords = numberOfWords;
    }

    /**
     * Adds a new word translation to the dictionary.
     * Updates both the fromEnglish and toEnglish maps and increments the word
     * count.
     *
     * @param english       The Word object representing the English word.
     * @param otherLanguage The Word object representing the translation in another
     *                      language.
     */
    public void addWord(Word word) {
        if(word != null) {
            this.words.add(word);
            numberOfWords++;
        }
    }

    /**
     * Removes a word translation from the dictionary.
     * Deletes entries from both fromEnglish and toEnglish maps and decrements the
     * word count.
     *
     * @param word The string representing the word to be removed.
     */
    public void removeWord(String word) {
        for (Word w : this.words) {
            if (w.getWordinLanguage().equalsIgnoreCase(word)) {
                this.words.remove(w);
                this.numberOfWords--;
                return;
            }
        }
    }

    /**
     * Prints the contents of the given HashMap representing word translations.
     * 
     * @param map A HashMap representing a dictionary for translation (either from
     *            or to English).
     */
    public void printDictionary() {
        for (Word w : this.words) {
            System.out.println(w.toString());
        }
    }

    /**
     * Checks if the dictionary contains a specific word in either the fromEnglish
     * or toEnglish maps.
     *
     * @param word The String of the word to check for in the dictionary.
     * @return true if the word exists in the dictionary, false otherwise.
     *         edited by cade
     */
    public boolean contains(String word) {
        if(word == null)
            return false;
        for (Word w : this.words) {
            if (w.getWordinLanguage().equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the total number of words in the dictionary.
     *
     * @return The number of word translations stored in the dictionary.
     */
    public int getNumberOfWords() {
        return numberOfWords;
    }

    /**
     * @author cade
     * @param id
     * @return list of words in that lesson
     */
    public ArrayList<Word> getWordsByTopicID(UUID id) {
        ArrayList<Word> ret = new ArrayList<>();
        for (Word w : this.words) {
            if (w.getLessonID().equals(id))
                ret.add(w);
        }
        return ret;
    }

    /**
     * @author Cade
     * @param word
     * @return the word object
     *         changed this to work with the new way we are using words (got rid of
     *         hashmaps)
     */
    public Word getWordByString(String word) {
        for (Word w : this.words) {
            if (word.equalsIgnoreCase(w.getWordinLanguage())) {
                return w;
            }
        }
        return null;
    }

    public Word getWordByUUID(UUID uuid) {
        for (Word w : this.words) {
            if (w.getWordUUID().equals(uuid)) {
                return w;
            }
        }
        return null;
    }

}
