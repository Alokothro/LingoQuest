package lingo_quest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a multiple choice question with various answer choices for language learning.
 */
public class MultipleChoice extends Question {
    private String language;                  // Language of the question.
    private ArrayList<Word> answerChoices;    // List of possible answers.
    private Word correctAnswer;               // The correct answer to the question.
    private int correctAnswerIndex;           // Index of the correct answer within the list (1-indexed).
    private int coinValue;                    // Coin value awarded for correct answer.
    private int pointValue;                   // Point value awarded for correct answer.
    public String userAnswer;                 // User's selected answer as a string.

    /**
     * Constructs a MultipleChoice question with specified language, answer choices, and the correct answer.
     * The list of answer choices is shuffled to prevent predictable answer patterns.
     *
     * @param language Language in which the question is presented.
     * @param answerChoices List of all possible answers.
     * @param correctAnswer The correct answer to the question.
     */
    public MultipleChoice(String language, ArrayList<Word> answerChoices, Word correctAnswer) {
        super(language);
        this.answerChoices = answerChoices;
        this.correctAnswer = correctAnswer;
        Collections.shuffle(answerChoices);
        this.correctAnswerIndex = getCorrectAnswerIndexPlusOne();
        coinValue = 100;
        pointValue = 100;
    }

    /**
     * Retrieves the index of the correct answer within the list of answer choices.
     * The index is 1-based for user-friendly display.
     *
     * @return The index (1-based) of the correct answer within the answer choices.
     */

    public int getCorrectAnswerIndexPlusOne() {
        for (int i = 0; i < answerChoices.size(); i++) {
            if (answerChoices.get(i).wordUUID.equals(correctAnswer.wordUUID)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Sets the user's answer to the question.
     *
     * @param userAnswer The answer choice provided by the user.
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
    /**
     * Generates a string representation of the multiple choice question, including the query and all possible answers.
     *
     * @return A formatted string with the question and answer choices.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("What is the spanish equivalent to " + correctAnswer.getEnglishVersion() + "\nPlease select your answer choice:\n");
        for (int i = 0; i < answerChoices.size(); i++) {
            result.append(i + 1).append(". ").append(answerChoices.get(i).getWordinLanguage()).append("\n");
        }
        return result.toString();
    }
    /**
     * Checks whether the user's answer is correct.
     * It converts the user's answer to an index and compares it against the correct answer's index.
     *
     * @param user The user who answered the question.
     * @return true if the user's answer is correct, false otherwise.
     */
    @Override
    public boolean isCorrect(User user) {
        try {
            int userAnswerIndex = Integer.parseInt(userAnswer.trim());
            boolean isCorrect = (userAnswerIndex == correctAnswerIndex);

            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(isCorrect);
            return isCorrect;
        } catch (NumberFormatException e) {
            // Handle invalid user input
            user.getUserDictionary().getWordByUUID(correctAnswer.getWordUUID()).wordPresented(false);
            return false;
        }
    }
}