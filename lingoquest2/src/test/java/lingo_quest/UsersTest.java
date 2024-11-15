package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.UUID;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class UsersTest {

    private Users usersInstance;

    @Before
    public void setUp() {
        // Reset the singleton instance before each test
        usersInstance = Users.getInstance();
        usersInstance.setUsers(new ArrayList<>()); // Empty the user list for a clean state
    }

    // Test for Singleton Instance
    @Test
    public void testSingletonInstance() {
        Users instance1 = Users.getInstance();
        Users instance2 = Users.getInstance();
        assertSame("Singleton instance should be the same", instance1, instance2);
    }
    @Test
    public void testSingletonRetainsState() {
        Users instance1 = Users.getInstance();
        instance1.createUser("user1", "password1");

        Users instance2 = Users.getInstance();
        assertEquals("Singleton instance should retain state across different calls", 
                     1, instance2.getUsers().size());
    }

    // Test: Singleton with Multiple Modifications
    @Test
    public void testSingletonWithMultipleModifications() {
        Users instance1 = Users.getInstance();
        instance1.createUser("user1", "password1");
        
        Users instance2 = Users.getInstance();
        instance2.createUser("user2", "password2");

        assertEquals("Singleton instance should contain all added users", 
                     2, instance1.getUsers().size());
        assertEquals("Usernames should match for the first user", "user1", instance1.getUsers().get(0).getUsername());
        assertEquals("Usernames should match for the second user", "user2", instance1.getUsers().get(1).getUsername());
    }

    // Test: Singleton is not affected by external references
    @Test
    public void testSingletonNotAffectedByExternalReference() {
        Users instance1 = Users.getInstance();
        ArrayList<User> externalUsers = instance1.getUsers();

        // Modify the external list reference
        externalUsers.add(new User("tempUser", "tempPass"));

        Users instance2 = Users.getInstance();
        assertEquals("Singleton should not be affected by external references",
                     0, instance2.getUsers().size());
    }

    // Test: Singleton Reset in Testing Environment
    @Test
    public void testSingletonResetInTesting() {
        Users instance1 = Users.getInstance();
        instance1.createUser("user1", "password1");

        // Reset singleton for test isolation
        instance1.setUsers(new ArrayList<>());

        Users instance2 = Users.getInstance();
        assertEquals("Singleton reset should result in empty user list",
                     0, instance2.getUsers().size());
    }

    // Test for getUser() Method
    @Test
    public void testGetUserValidCredentials() {
        User user = new User("testUser", "testPass");
        usersInstance.getUsers().add(user);

        User retrievedUser = usersInstance.getUser("testUser", "testPass");
        assertNotNull("User should be found with valid credentials", retrievedUser);
        assertEquals("Retrieved user should match", user, retrievedUser);
    }

    @Test
    public void testGetUserInvalidUsername() {
        User user = new User("testUser", "testPass");
        usersInstance.getUsers().add(user);

        User retrievedUser = usersInstance.getUser("invalidUser", "testPass");
        assertNull("Should return null for invalid username", retrievedUser);
    }

    @Test
    public void testGetUserInvalidPassword() {
        User user = new User("testUser", "testPass");
        usersInstance.getUsers().add(user);

        User retrievedUser = usersInstance.getUser("testUser", "wrongPass");
        assertNull("Should return null for invalid password", retrievedUser);
    }

    @Test
    public void testGetUserEmptyList() {
        User retrievedUser = usersInstance.getUser("testUser", "testPass");
        assertNull("Should return null when no users exist", retrievedUser);
    }

    // Test for containsDictionary() Method
    @Test
    public void testContainsDictionaryValidID() {
        User user = new User();
        UUID dictionaryID = UUID.randomUUID();
        user.setUserDictionaryID(dictionaryID);
        usersInstance.getUsers().add(user);

        assertTrue("Should return true if dictionary ID exists", usersInstance.containsDictionary(dictionaryID));
    }

    @Test
    public void testContainsDictionaryInvalidID() {
        UUID nonExistentID = UUID.randomUUID();
        assertFalse("Should return false if dictionary ID does not exist", usersInstance.containsDictionary(nonExistentID));
    }

    @Test
    public void testContainsDictionaryEmptyList() {
        UUID dictionaryID = UUID.randomUUID();
        assertFalse("Should return false when no users exist", usersInstance.containsDictionary(dictionaryID));
    }

    // Test for getUserByUUID() Method

    @Test
    public void testGetUserByUUIDInvalid() {
        UUID nonExistentID = UUID.randomUUID();
        User retrievedUser = usersInstance.getUserByUUID(nonExistentID);
        assertNull("Should return null for non-existent UUID", retrievedUser);
    }

    @Test
    public void testGetUserByUUIDEmptyList() {
        UUID userID = UUID.randomUUID();
        User retrievedUser = usersInstance.getUserByUUID(userID);
        assertNull("Should return null when no users exist", retrievedUser);
    }

    // Test for createUser() Method
    @Test
    public void testCreateUserValid() {
        boolean created = usersInstance.createUser("newUser", "newPass");
        assertTrue("User creation should succeed with valid credentials", created);
        assertEquals("User list should contain the new user", 1, usersInstance.getUsers().size());
    }

    @Test
    public void testCreateUserInvalidUsername() {
        boolean created = usersInstance.createUser("", "newPass");
        assertFalse("User creation should fail with an empty username", created);
        assertEquals("User list should be empty", 0, usersInstance.getUsers().size());
    }

    @Test
    public void testCreateUserInvalidPassword() {
        boolean created = usersInstance.createUser("newUser", "");
        assertFalse("User creation should fail with an empty password", created);
        assertEquals("User list should be empty", 0, usersInstance.getUsers().size());
    }

    @Test
    public void testCreateUserDuplicateUsername() {
        usersInstance.createUser("existingUser", "pass123");
        boolean created = usersInstance.createUser("existingUser", "newPass");
        assertFalse("User creation should fail with a duplicate username", created);
        assertEquals("User list should contain only one user", 1, usersInstance.getUsers().size());
    }

    // Test for containsUsername() Method
    @Test
    public void testContainsUsernameExisting() {
        usersInstance.createUser("testUser", "testPass");
        assertTrue("Should return true if username exists", usersInstance.containsUsername("testUser"));
    }

    @Test
    public void testContainsUsernameNonExisting() {
        assertFalse("Should return false if username does not exist", usersInstance.containsUsername("nonExistentUser"));
    }

    @Test
    public void testContainsUsernameEmptyList() {
        assertFalse("Should return false when no users exist", usersInstance.containsUsername("anyUser"));
    }

    // Test for saveUsers() Method
    @Test
    public void testSaveUsers() {
        User user = new User("saveTestUser", "saveTestPass");
        usersInstance.getUsers().add(user);
        
        usersInstance.saveUsers();
        
    }

    // Test for loadUsers() Method
    @Test
    public void testLoadUsers() {
        usersInstance.loadUsers();
        
        // Check if users list is loaded (requires DataLoader to return expected users for test file)
        // As with save, we would mock this in a real scenario
        // Validate based on the mock data returned from DataLoader
    }

    // Test for generateUUID() Method
    @Test
    public void testGenerateUUIDUniqueness() {
        UUID id1 = usersInstance.generateUUID();
        UUID id2 = usersInstance.generateUUID();
        assertFalse("Generated UUIDs should be unique", id1.equals(id2));
    }
    

    @Test
    public void testGenerateUUIDNotNull() {
        UUID id = usersInstance.generateUUID();
        assertNotNull("Generated UUID should not be null", id);
    }

    // Test for printUserList() Method
    @Test
    public void testPrintUserList() {
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        usersInstance.getUsers().add(user1);
        usersInstance.getUsers().add(user2);
        
        usersInstance.printUserList();
        
    }
}

