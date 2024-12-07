package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;

public class ShopController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    // constructor
    public ShopController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            this.user = languageGame.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // navigation buttons
    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("/lingoquestpackage/profile");
    }

    @FXML
    public void goToPractice() throws IOException {
        App.setRoot("/lingoquestpackage/practice");
    }

    @FXML
    public void goToLeaderboard() throws IOException {
        App.setRoot("/lingoquestpackage/leaderboard");
    }

    @FXML
    public void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
    }

    @FXML
    public void handleLogout() throws Exception {
        // instance of the facade
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        // log the current user out
        languageGame.logout();
        // go back to login page
        App.setRoot("/lingoquestpackage/login");
    }

    // initialize the data
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        if(languageGame == null) {
            try {
                languageGame = LanguageGame.getInstance();
                this.user = languageGame.getUser();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // set the text on the top to hold the current user's username
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());
    }
}
// The ShopController class serves as the controller for the shop functionality in the LingoQuest application. 
// It implements the Initializable interface, enabling it to set up and initialize data and UI components when the associated FXML file is loaded. 
// This controller integrates the application's backend logic, represented by the LanguageGame and User classes, with the user interface elements to display and manage shop-related information.
// The class has three primary UI components bound to the FXML file: usernameField (to display the username), answerStreak (to show the user's current answer streak), and coinLabel (to display the user's coin balance). 
// These labels are updated during the initialize method, which sets their values using data retrieved from the User and LanguageGame instances. 
// The backend is initialized in the constructor to ensure seamless data retrieval and interaction.
// The class also includes navigation methods (goToProfile, goToPractice, goToLeaderboard, and goToHome) that allow users to move between different sections of the application. 
// The handleLogout method logs the user out via the backend and redirects them to the login page.
// By connecting the backend logic to the UI, the ShopController ensures that users can view their shop-related statistics, such as coins and streaks, while also providing navigation options to access other parts of the application. 
// This controller plays a key role in maintaining a dynamic and user-friendly experience within the shop section of the LingoQuest application.