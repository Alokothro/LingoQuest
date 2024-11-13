package lingo_quest;

import java.util.ArrayList;
/**
 * Abstract class that serves as a base for different types of questions in a language learning application.
 * This class defines the structure and behaviors that all specific question types must implement.
 */
abstract class Question {
  protected String language;      // Language in which the question is presented.
  protected String userAnswer;    // User's answer to the question.
  protected Word correctAnswer;   // Correct answer to the question.

    /**
     * Constructs a question in the specified language or defaults to Spanish if none is provided.
     * 
     * @param language The language in which the question should be presented, defaults to "Spanish" if null.
     */
  public Question(String language) {
    if(language == null)
      this.language = "Spanish";
    else
      this.language = language;
  }

    /**
     * Retrieves the correct answer for the question.
     * 
     * @return The correct answer as a {@link Word} object.
     */
  public Word getCorrectAnswer() {
    return this.correctAnswer;
  }

    /**
     * Sets the user's answer to this question.
     * 
     * @param userAnswer The answer provided by the user.
     */
  public void setUserAnswer(String userAnswer) {
    this.userAnswer = userAnswer;
  }
    /**
     * Abstract method to determine whether the user's answer is correct.
     * This method must be implemented by subclasses to provide specific logic for checking answers.
     * 
     * @param user The user whose answer is to be validated.
     * @return true if the user's answer is correct, false otherwise.
     */
  public abstract boolean isCorrect(User user);
    /**
     * Abstract method to get a string representation of the question.
     * This method must be implemented by subclasses to format the question for display.
     * 
     * @return A string representation of the question.
     */
  public abstract String toString();
}
