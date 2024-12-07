package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;

/**
 * @author cade
 */
public class ProfileController implements Initializable{

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private Label usernameLabel2;

    @FXML
    private Label friendLabel;

    @FXML
    private Label coinsEarnedLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private Label answerStreak2;

    @FXML
    private ChoiceBox currentLanguage;

    @FXML
    private Label progressLabel;

    @FXML
    private ProgressBar progressBar;

    public ProfileController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            //messageLabel.setText("test!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // set languagemanager and user
        if(languageGame == null) {
            try {
                languageGame = LanguageGame.getInstance();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(this.user == null) {
            this.user = languageGame.getUser();
        }

        // set the text on the top banner
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        // set the labels for the page
        usernameLabel2.setText(user.getUsername());
        welcomeLabel.setText("Welcome Back, " + user.getUsername());
        friendLabel.setText("Friends: " + user.getFriendsList().size());
        coinsEarnedLabel.setText("Coins Earned: " + user.getCoinsEarned());
        pointLabel.setText("Points: " + user.getCurrentLanguage().getPointsEarned());
        answerStreak2.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());
        currentLanguage.setValue(user.getCurrentLanguage().getLanguageName());
        //progressLabel.setText("Current Language Progress: " + user.getCurrentLanguageProgress() + "%");
        double progressPercentage = user.getCurrentLanguageProgress();
        String formattedProgress = String.format("Current Language Progress: %.2f%%", progressPercentage);
        progressLabel.setText(formattedProgress);
        progressBar.setProgress(user.getCurrentLanguageProgress() / 1000);
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


    // navigation buttons
    @FXML
    public void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
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
    public void goToShop() throws IOException {
        App.setRoot("/lingoquestpackage/shop");
    }

}
// The ProfileController class is a JavaFX controller for managing the profile view in the LingoQuest application. 
// It implements the Initializable interface, allowing it to set up and initialize user interface components when the associated FXML file is loaded. 
// This controller connects the application's backend, represented by the LanguageGame and User classes, with the user interface to dynamically display and update the user's profile data.
// The class has several UI components linked to the FXML file, including labels to display the user's username, coins, answer streak, friends count, total coins earned, and points. 
// It also features a ChoiceBox for displaying the user's current language and a ProgressBar to represent the user's progress in their current language. 
// During initialization, the initialize method retrieves the user data from the backend and updates these UI elements with the user's profile information, such as username, language progress, and other statistics. 
// The method also formats the language progress percentage for better readability.
// The handleLogout method logs the user out using the backend and redirects them to the login page. 
// Additionally, the class provides navigation methods (goToHome, goToPractice, goToLeaderboard, and goToShop) for transitioning between different sections of the application.
// By linking the backend data to the user interface, the ProfileController ensures that the profile view provides an accurate and comprehensive representation of the user's progress and achievements. 
// It enables seamless navigation and interaction, enhancing the overall user experience within the LingoQuest application.