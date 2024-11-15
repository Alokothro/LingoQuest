package lingo_quest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import lingoquestpackage.models.*;


public class LeaderBoardTest {

    private LeaderBoard leaderboard;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        leaderboard = LeaderBoard.getInstance();
        leaderboard.getUsers().clear(); // Clear the leaderboard before each test

        // Set up some test users
        user1 = new User();
        user2 = new User();
        user3 = new User();

        leaderboard.addUser(user1);
        leaderboard.addUser(user2);
        leaderboard.addUser(user3);
    }

    @Test
    public void testGetInstance_returnsSameInstance() {
        LeaderBoard anotherInstance = LeaderBoard.getInstance();
        assertEquals(leaderboard, anotherInstance);
    }

    @Test
    public void testAddUser_addNewUser() {
        User newUser = new User();
        leaderboard.addUser(newUser);
        assertTrue(leaderboard.getUsers().contains(newUser));
    }

    @Test
    public void testAddUser_addExistingUser() {
        leaderboard.addUser(user1); // Add user1 again
        assertEquals(3, leaderboard.getUsers().size()); // Should not add duplicates
    }

    @Test
    public void testSortUsersByCoinsEarnedAscendingOrder() {
        LeaderBoard leaderboard = LeaderBoard.getInstance();
        leaderboard.getUsers().clear();
        User user1 = new User("User1111", "password11");
        User user2 = new User("User2222", "password11");
        User user3 = new User("User3333", "password11");

        user1.setCoinsEarned(300);
        user2.setCoinsEarned(100);
        user3.setCoinsEarned(200);

        leaderboard.addUser(user1);
        leaderboard.addUser(user2);
        leaderboard.addUser(user3);

        leaderboard.sortUsers();

        assertEquals(user2, leaderboard.getUsers().get(0), "Users should be sorted by coins earned in ascending order.");
        assertEquals(user3, leaderboard.getUsers().get(1));
        assertEquals(user1, leaderboard.getUsers().get(2));
    }

    @Test
    public void testAddUser_addNullUser() {
        leaderboard.addUser(null);
        assertEquals(3, leaderboard.getUsers().size()); // Null should not be added
    }

    @Test
    public void testAddUser_checkUserInList() {
        assertTrue(leaderboard.getUsers().contains(user1));
        assertTrue(leaderboard.getUsers().contains(user2));
    }

    @Test
    public void testRemoveUser_existingUser() {
        leaderboard.removeUser(user1);
        assertFalse(leaderboard.getUsers().contains(user1));
    }

    @Test
    public void testRemoveUser_nonExistentUser() {
        User nonExistentUser = new User();
        leaderboard.removeUser(nonExistentUser); // Removing a user not in the leaderboard
        assertEquals(3, leaderboard.getUsers().size()); // Size should remain the same
    }

    @Test
    public void testRemoveUser_nullUser() {
        leaderboard.removeUser(null); // Removing null should do nothing
        assertEquals(3, leaderboard.getUsers().size());
    }

    @Test
    public void testRemoveUser_afterAddingAndRemoving() {
        User newUser = new User();
        leaderboard.addUser(newUser);
        leaderboard.removeUser(newUser);
        assertFalse(leaderboard.getUsers().contains(newUser));
    }

    @Test
    public void testRemoveUser_checkRemainingUsers() {
        leaderboard.removeUser(user2);
        assertTrue(leaderboard.getUsers().contains(user1));
        assertTrue(leaderboard.getUsers().contains(user3));
        assertFalse(leaderboard.getUsers().contains(user2));
    }

    @Test
    public void testPrintLeaderboard_outputFormat() {
        // Note: This test assumes that you have a way to capture console output.
        // Here, we're just checking for no exceptions and basic functionality.
        leaderboard.printLeaderboard();
        assertEquals(3, leaderboard.getUsers().size());
    }

    @Test
    public void testPrintLeaderboard_emptyLeaderboard() {
        leaderboard.getUsers().clear();
        leaderboard.printLeaderboard(); // Should handle empty leaderboard without errors
        assertTrue(leaderboard.getUsers().isEmpty());
    }

    @Test
    public void testPrintLeaderboard_withMultipleUsers() {
        leaderboard.printLeaderboard(); // Ensures output prints all users correctly
        assertEquals(3, leaderboard.getUsers().size());
    }

    @Test
    public void testPrintLeaderboard_withSingleUser() {
        leaderboard.getUsers().clear();
        leaderboard.addUser(user1);
        leaderboard.printLeaderboard(); // Check output for a single user
        assertEquals(1, leaderboard.getUsers().size());
    }

    @Test
    public void testPrintLeaderboard_withNullUser() {
        leaderboard.getUsers().add(null); // Adding a null user to test handling
        leaderboard.printLeaderboard();
        assertTrue(leaderboard.getUsers().contains(null));
    }

    @Test
    public void testGetUsers_returnsUserList() {
        ArrayList<User> users = leaderboard.getUsers();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testGetUsers_containsAddedUsers() {
        ArrayList<User> users = leaderboard.getUsers();
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
    }

    @Test
    public void testGetUsers_afterRemovingUser() {
        leaderboard.removeUser(user2);
        ArrayList<User> users = leaderboard.getUsers();
        assertEquals(2, users.size());
        assertFalse(users.contains(user2));
    }

    @Test
    public void testGetUsers_emptyList() {
        leaderboard.getUsers().clear();
        assertTrue(leaderboard.getUsers().isEmpty());
    }

    @Test
    public void testGetUsers_immutableList() {
        ArrayList<User> users = leaderboard.getUsers();
        users.clear();
        assertNotEquals(0, leaderboard.getUsers().size()); // Original list should remain unchanged
    }

    @Test
    public void testGetUsers_duplicateEntries() {
        leaderboard.addUser(user1);
        ArrayList<User> users = leaderboard.getUsers();
        assertEquals(3, users.size()); // Should not allow duplicate entries
    }
}
