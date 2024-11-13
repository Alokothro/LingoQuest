package lingo_quest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a placement test containing a set of questions to determine the user's proficiency.
 * The test tracks the user's score, the number of correct answers, and whether the test has been taken.
 */
public class PlacementTest {
    private UUID testID;                   // Unique identifier for the test.
    private ArrayList<Question> questions; // List of questions contained in the test.
    private int correctAnswers;            // Count of correctly answered questions.
    private int score;                     // Numerical score based on correct answers.
    private boolean taken;                 // Flag indicating if the test has been completed by the user.

    /**
     * Constructs a new PlacementTest with an empty list of questions.
     * Initializes the test as not taken and assigns a unique identifier.
     */
    public PlacementTest() {
        this.questions = new ArrayList<Question>();
        this.taken = false;
        this.testID = UUID.randomUUID();
        
    }

    /**
     * Returns the unique identifier of the test.
     * 
     * @return The UUID of this placement test.
     */
    public UUID getID() {
        return this.testID;
    }

    /**
     * Sets the unique identifier for the test.
     * 
     * @param id The new UUID to be set as the test identifier.
     */
    public void setID(UUID id) {
        this.testID = id;
    }

    /**
     * Retrieves the current score of the test.
     * The score is typically calculated based on the number of correct answers.
     * 
     * @return The score of the placement test.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Checks whether the placement test has been taken.
     * 
     * @return True if the test has been taken, otherwise false.
     */
    public boolean getTaken() {
        return this.taken;
    }

    /**
     * Returns a list of questions for the placement test.
     * 
     * @return An ArrayList of Question objects contained in the test.
     */
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    /**
     * Retrieves the count of correct answers from the test.
     * 
     * @return The number of questions answered correctly.
     */
    public int getCorrectAnswers() {

        return correctAnswers;
    }

}

