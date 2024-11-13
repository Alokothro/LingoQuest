package lingo_quest;

import java.util.Random;

/**
 * Represents a true or false question about word translations in a specific language.
 */
public class TrueOrFalse extends Question {
    private String language;        // The language of the question.
    public Word displayedWord;     // The word displayed in the question.
    public String answer;          // The correct answer ("true" or "false").
    private Word correctWord;       // The word that is actually correct.
    private int coinValue = 100;    // The coin value awarded for a correct answer.
    private int pointValue = 100;   // The point value awarded for a correct answer.
    public String userAnswer;       // The user's answer to the question.

    public TrueOrFalse() {
        super("spanish");
    }
    /**
     * Constructs a TrueOrFalse question that randomly decides the truth value of the translation provided.
     *
     * @param language The language of the word.
     * @param aWord The first word option which might be the correct translation.
     * @param anotherWord The second word option which might be the incorrect translation.
     */
    public TrueOrFalse(String language, Word aWord, Word anotherWord) {
        super(language);
        this.language = language;

        Random rand = new Random();
        if (rand.nextBoolean()) {
            this.displayedWord = aWord;
            this.correctWord = aWord;
            this.answer = "true";
        } else {
            this.displayedWord = anotherWord;
            this.correctWord = aWord; // correct answer remains aWord
            this.answer = "false";
        }
    }

    /**
     * Sets the user's answer for this question.
     *
     * @param userAnswer The answer provided by the user.
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    /**
     * Returns a string representation of the question.
     *
     * @return The question formatted as a true or false question about word translation.
     */
    @Override
    public String toString() {
        String s = ("Is " + displayedWord.getWordinLanguage() + " the " + "Spanish" + " word for "
                + correctWord.getEnglishVersion() + "? (true/false)");
        return s;
    }

    /**
     * Evaluates if the user's answer is correct.
     *
     * @param user The user answering the question.
     * @return true if the user's answer matches the correct answer, otherwise false.
     */
    public boolean isCorrect(User user) {
        if (userAnswer != null && userAnswer.toLowerCase().trim().equals(answer)) {
            user.getUserDictionary().getWordByUUID(correctWord.getWordUUID()).wordPresented(true);
            return true;
        } else {
            user.getUserDictionary().getWordByUUID(correctWord.getWordUUID()).wordPresented(false);
            return false;
        }
    }
}
