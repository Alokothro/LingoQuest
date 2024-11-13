package lingo_quest;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

public class Users {
    private ArrayList<User> users;
    private User guestUser;
    private static Users usersObject;

    private Users() {
        users = new ArrayList<User>();
    }

    /**
     * @author Wade Little
     * Returns the single instance of the Users class (Singleton)
     * @return the Users class
     */
    public static Users getInstance() {
        if (usersObject == null) {
            usersObject = new Users();
        }
        return usersObject;
    }

    /**
     * @author Wade Little
     * This returns the user that has the matching username and password. If
     * There is no match then it returns null and prints an error message.
     * @param username The username of the user you are getting
     * @param password The password of the user you are getting
     * @return The user with the matching username and password. Or null if the user
     *         can't be found
     */
    public User getUser(String username, String password) {
        // changed to a for-each loop - CADE
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Successfully Logged In, " + username+".");
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }

    public boolean containsDictionary(UUID id) {
        for(User u : users) {
            if(u.getUserDictionaryID().equals(id))
                return true;
        }
        return false;
    }

    /**
     * @author cade
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * @author cade
     * @param user list
     */
    public void setUsers(ArrayList<User> u) {
        this.users = u;
    }

    /**
     * @author cade
     * @param user that will be added if not already in list
     */
    public void addUser(User u) {
        if(u != null && users.contains(u) == false) {
            users.add(u);
        }
    }
    /**
     * @author Wade Little
     * Searches through the userlist for the specified UUID
     * @param userId the userid you are looking for
     * @return Null if the user isn't found, or the user with the matching UUID
     */
    public User getUserByUUID(UUID userId) {
        for (User user : users) {
            // System.out.println("TESTSTSTSTT " + user.getUUID());
            if (user.getUUID().equals(userId)) {
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }

    /**
     * @author Wade Little
     * Creates a user if they provide a valid username and password and adds
     * it to the user list
     * @param username The users desired username
     * @param password The users desired password
     * @return True if the user is created and added to userlist. False if the user
     *         has invalid credentials.
     */
    public boolean createUser(String username, String password) {
        User user = new User();
        if (user.setUsername(username) && user.setPassword(password)) {
            users.add(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author Wade Little
     * Checks the userlist and returns true if the username is contained,
     * false if the username isn't there.
     * @param username The username you are checking for
     * @param password Added this too - cade
     * @return User
     * Changed return type from boolean to User for create account purposes
     * Changed back to boolean 
     */
    public boolean containsUsername(String username) {
        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().trim().equalsIgnoreCase(username.trim())) {
                System.out.println("Username already exists");
                return true;
            }
        }
        return false;
    }

    /**
     * @author Cade
     * @param user
     * Save all users
     */
    public void saveUsers() {
        for (User u : users) {
            DataWriter.writeUsers(users, DataWriter.getUserFile());
            return;
        }
    }

    public void loadUsers() {
        if (this.users != null)
            try {
                if(DataLoader.loadUsers(DataLoader.getUserFile()) == null)
                    return;
                this.users = DataLoader.loadUsers(DataLoader.getUserFile());
                for (User u : this.users) {
                    // the user's dicionary has been loaded into dictionaryManager already, but this
                    // will assign the object to the user
                    UUID dictionaryID = u.getUserDictionaryID();
                    Dictionary d = DictionaryManager.getInstance().getDictionaryByID(dictionaryID);
                    u.setUserDictionary(d);
                }
            } catch (IOException | ParseException | org.json.simple.parser.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * @author Cade Stocker
     * @return a UUID
     * Originally debated between turning the UUID into a string,
     * but decided that the UUID class offers a lot of methods that may
     * be useful.
     */
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public void printUserList() {
        for (User u : users) {
            System.out.println(u.toString());
        }
    }

    public void sendFriendRequest(UUID sender, UUID receiver) {
        if(sender != null && receiver != null &&
         getUserByUUID(receiver) != null && getUserByUUID(sender) != null) {
            
        }
    }
}
