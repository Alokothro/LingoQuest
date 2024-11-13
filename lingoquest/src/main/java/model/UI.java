package lingo_quest;

import java.util.Scanner;
import java.util.UUID;
/**
 * This class serves as a user interface for the LanguageGame, handling user input
 * and facilitating user interactions such as logging in, creating accounts, and performing
 * language learning activities.
 */
public class UI {
    private static LanguageGame lg;
    /**
     * Handles user login by prompting for username and password.
     */
    public static void login() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = keyboard.nextLine();
        System.out.println("Enter Password: ");
        String password = keyboard.nextLine();
        lg.login(username, password);
        if (lg.getUser() != null)
            lg.getUser().setCoinsEarned(40);
    }
    /**
     * Handles user logout.
     */
    public static void logout() {
        lg.logout();
    }
    /**
     * Facilitates the creation of a new user account by interacting with the user to get a valid username and password.
     */
    public static void createAccount() {
        String username;
        String password;
        boolean repeat = false;
        do {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter username you would like to use");
            username = keyboard.nextLine();
            System.out.println("Enter password you would like to use");
            password = keyboard.nextLine();
            // Checking password and username length here to avoid crash
            if (username.length() <= 8 || password.length() <= 8 || password.contains(" ")) {
                repeat = true;
                System.out.println("Invalid username or password");
            } else
                repeat = false;
        } while (repeat);
        lg.createUser(username, password);
        lg.startLanguage(Languages.SPANISH);
        lg.getUser().getUserDictionary().getWordByString("Galleta").setPoints(100);
    }
    /**
     * The main method to simulate various user interactions and operations of the LanguageGame.
     * This includes creating user accounts, logging in, picking languages, and more, based on
     * pre-defined scenarios and command line interactions.
     *
     * @param args Command line arguments, not used in this implementation.
     * @throws Exception General exception catch for simplicity, should ideally handle specific exceptions.
     */
    public static void main(String[] args) throws Exception {
        // CODE FOR OUR SCENARIO
        Scanner keyboard = new Scanner(System.in);
        // start the facade
        lg = new LanguageGame();

        // SETTING UP TAMMY'S ACCOUNT

        /*lg.createUser("TTomacka", "securePassword");
        lg.login(("TTomacka"), "securePassword");
        lg.pickALanguageByUUID(UUID.fromString("b2657c2b-56ef-45b1-a794-97eda7a32bd4"));
        lg.pickASectiotruen(UUID.fromString("bb8832c1-3f9c-4796-84e2-d8a41ff22d14"));
        lg.pickALesson(UUID.fromString("a5d8b523-68e9-46c0-ba96-63a244127868"));
        // generate questions from the current lesson
        for(int i =0; i< 5;i++) {
            lg.getAQuestion();
            lg.answerQuestion(keyboard);
        }
        lg.logout();*/
        //System.exit(0);


        // tammy tomacka already exists in the json
        // attempt to make account with existing username
        lg.createUser("TTomacka", "Password1234");

        // make actual account
        lg.createUser("TTT131j3yk1d31313d", "PASSWORDPASSWORD333");

        // set the language and pick a section
        // will be moved to work in the Facade instead of UI
        // lg.login("TTomacka445","PASSWORDPASSWORD333");
        // needs to deny attempts to do stuff if create user fails TODO
        lg.pickALanguageByUUID(UUID.fromString("b2657c2b-56ef-45b1-a794-97eda7a32bd4"));
        lg.pickASection(UUID.fromString("bb8832c1-3f9c-4796-84e2-d8a41ff22d14"));
        lg.pickALesson(UUID.fromString("a5d8b523-68e9-46c0-ba96-63a244127868"));

        // generate questions from the current lesson
        for(int i =0; i< 1;i++) {
            lg.getAQuestion();
            lg.answerQuestion(keyboard);
        }
        // end cade speaking

        // display the performance from the lesson
        lg.getProgressScreen();

        // log out
        lg.logout();

        // tammy logs in
        lg.login("TTomacka", "Password1234");

        // set the language, section, and lesson
        lg.pickALanguageByUUID(UUID.fromString("b2657c2b-56ef-45b1-a794-97eda7a32bd4"));
        lg.pickASection(UUID.fromString("bb8832c1-3f9c-4796-84e2-d8a41ff22d14"));
        lg.pickALesson(UUID.fromString("a5d8b523-68e9-46c0-ba96-63a244127868"));

        // she checks her progress
        lg.getProgressScreen();

        // tammy creates a txt file study sheet
        lg.makeStudySheet();

        // tammy chooses to answer some questions on material she needs to review
        // load the material
        lg.logout();
        System.exit(0);
        lg.practiceLowUnderstanding();

        // generate questions
        for(int i =0; i< 5;i++) {
            lg.getAQuestion();
            lg.answerQuestion(keyboard);
        }

        // see results
        lg.getProgressScreen();

        // tammy logs out
        lg.logout();
    }
}
