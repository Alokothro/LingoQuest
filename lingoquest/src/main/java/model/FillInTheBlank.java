package lingo_quest;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents a fill-in-the-blank question that tests a user's knowledge of word translations.
 */
public class FillInTheBlank extends Question {
    private String language;
    Word correctAnswer;
    private int coinValue;
    private int pointValue;
    public String userAnswer;

    /**
     * Constructs a fill-in-the-blank question with specified language and the correct answer.
     *
     * @param language The language of the question.
     * @param correctAnswer The word object that represents the correct answer.
     */
    public FillInTheBlank(String language, Word correctAnswer) {
        super(language);
        this.setLanguage(language);
        this.correctAnswer = correctAnswer;
        setCoinValue(100);  // Default coin value for correct answers.
        setPointValue(100); // Default point value for correct answers.
    }

    public String getLanguage() {
        return language;
        
    }

    public void setLanguage(String language) {
        this.language = language;
        
    }

    public int getPointValue() {
        return pointValue;
        
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
        
    }

    public int getCoinValue() {
        return coinValue;
        
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
        
    }

    /**
     * Sets the user's answer for this question.
     *
     * @param userAnswer The answer provided by the user.
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    /*
     * @author Wade Little
     * Concatanates the question and answer choices into an easy to read format
     * 
     * @return the question and its answer choices as a string
     */
    public String toString() {
        StringBuilder result = new StringBuilder("What is the spanish version of " + correctAnswer.getEnglishVersion() + "\nType your answer below\n");
        return result.toString();
    }

    public boolean isCorrect(User user) {
        if (userAnswer.toLowerCase().trim().equals(correctAnswer.getWordinLanguage().toLowerCase().trim())) {
            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(true);
            return true;
        } else {
            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(false);
            return false;
        }
    }
}
