package lingo_quest;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Leaderboard class maintains a list of users and their rankings based on
 * points.
 * It supports adding/removing users and sorting the leaderboard by the user's
 * total points.
 */
public class LeaderBoard {

    // List to store users
    private ArrayList<User> users;
    private static LeaderBoard leaderboardInstance;

    /**
     * Private constructor for the Leaderboard class.
     * Initializes the list of users.
     */
    private LeaderBoard() {
        users = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the Leaderboard.
     *
     * @return The singleton Leaderboard instance.
     */
    public static LeaderBoard getInstance() {
        if (leaderboardInstance == null) {
            leaderboardInstance = new LeaderBoard();
        }
        return leaderboardInstance;
    }

    /**
     * Adds a user to the leaderboard.
     *
     * @param user The user to be added to the leaderboard.
     * 
     * this method will be called from languagegame to avoid confusion - cade
     */
    public void addUser(User user) {
        if(user != null) {
            users.add(user);
            sortUsers();
        }
    }

    /**
     * Removes a user from the leaderboard.
     *
     * @param user The user to be removed from the leaderboard.
     */
    public void removeUser(User user) {
        if(user != null && users.contains(user)) {
            users.remove(user);
            sortUsers();
        }
    }

    /**
     * @author cade
     * @param list
     */
    public void setUsers(ArrayList<User> list) {
        if(list.isEmpty() == false && list != null) {
            this.users = list;
            sortUsers();
        }
    }

    /**
     * @author cade
     * method to sort the users in the list by the amount of coins they've earned on the app
     * tested and working - cade
     */
    public void sortUsers() {
        for (int i = 0; i < users.size() - 1; i++) {
            boolean swapped = false;
    
            for (int j = 0; j < users.size() - i - 1; j++) {
                if (users.get(j).getCoinsEarned() > users.get(j + 1).getCoinsEarned()) {
                    // Swap users based on coinsEarned
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                    swapped = true;
                }
            }
    
            if (!swapped) {
                break;
            }
        }
    }


    /**
     * Prints the leaderboard in a formatted way, showing the username and total
     * coins earned of each user.
     */
    public void printLeaderboard() {
        System.out.println("Leaderboard:");
        if(users.isEmpty() == false && users != null) {
            for (int i = 0; i < users.size(); i++) {
                System.out.println(
                        (i + 1) + ". " + users.get(i).getUsername() + " - " + users.get(i).getCoinsEarned() + " Total Coins Earned");
            }
        }
    }

    // TODO - PORTIA SAID IT WOULD BE COOL IF YOU CAN SEE YOURSELF RISE OR FALL ON THE LEADERBOARD ONCE YOU OPEN IT

    /**
     * Gets the list of users in the leaderboard.
     *
     * @return The list of users in the leaderboard.
     */
    public ArrayList<User> getUsers() {
        sortUsers();
        return users;
    }
}
