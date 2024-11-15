package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import lingoquestpackage.models.*;


import java.util.UUID;

public class PlacementTestTest {
    private PlacementTest placementTest;

    @Before
    public void setUp() {
        placementTest = new PlacementTest();
    }

    @Test
    public void testConstructorAndInitialization() {
        assertNotNull("UUID should not be null", placementTest.getID());
        assertFalse("New test should not be marked as taken", placementTest.getTaken());
        assertNotNull("Questions list should be initialized and not null", placementTest.getQuestions());
        assertEquals("Questions list should be empty initially", 0, placementTest.getQuestions().size());
    }

    @Test
    public void testSetAndGetID() {
        UUID newId = UUID.randomUUID();
        placementTest.setID(newId);
        assertEquals("The retrieved ID should match the set ID", newId, placementTest.getID());
    }

    @Test
    public void testGetTaken() {
        assertFalse("Test should not be marked as taken initially", placementTest.getTaken());
        // Simulate marking the test as taken if such functionality exists
        // If not, you can skip testing changes to this state unless there's a way to set it
    }

    @Test
    public void testQuestionListAccess() {
        // This test ensures that the questions list is accessible and can be manipulated
        // No need to add actual questions since we're not testing Question functionality
        assertNotNull("Question list should not be null", placementTest.getQuestions());
        assertEquals("Question list should initially be empty", 0, placementTest.getQuestions().size());

        // Example of adding a null which should not be done in production but can test list's behavior
        placementTest.getQuestions().add(null);
        assertEquals("Question list should contain one entry", 1, placementTest.getQuestions().size());
    }
}
