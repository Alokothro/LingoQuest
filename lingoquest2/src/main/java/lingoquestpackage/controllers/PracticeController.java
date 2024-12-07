package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;

public class PracticeController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private ProgressBar languageCompletion;

    @FXML 
    private ProgressBar foodProgress;

    @FXML 
    private ProgressBar transportationProgress;

    @FXML
    private ProgressBar colorsProgress;

    @FXML
    private ProgressBar animalsProgress;

    @FXML
    private ProgressBar communicationProgress;
    public PracticeController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            this.user = languageGame.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        foodProgress.setProgress(languageGame.getSectionByUUID(UUID.fromString("95ed1ef6-9e42-48dc-9be5-bde79a9f4019")).getSectionProgress() / 1000.0);
        transportationProgress.setProgress(languageGame.getSectionByUUID(UUID.fromString("97754491-c2d6-4a57-a808-1f5b825610ab")).getSectionProgress() / 1000.0);
        colorsProgress.setProgress(languageGame.getSectionByUUID(UUID.fromString("0a406031-6361-4d4a-9bf3-c2174e1487f4")).getSectionProgress() / 1000.0);
        animalsProgress.setProgress(languageGame.getSectionByUUID(UUID.fromString("586e5fff-fe49-4ed8-9844-a1a4a591ad83")).getSectionProgress() / 1000.0);
        communicationProgress.setProgress(languageGame.getSectionByUUID(UUID.fromString("6d564000-db42-4aa3-b6ef-7779361b3038")).getSectionProgress() / 1000.0);
    }

    // navigation buttons
    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("/lingoquestpackage/profile");
    }

    @FXML
    public void goToLeaderboard() throws IOException {
        App.setRoot("/lingoquestpackage/leaderboard");
    }

    @FXML
    public void goToShop() throws IOException {
        App.setRoot("/lingoquestpackage/shop");
    }

    @FXML
    public void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
    }
}
// The PracticeController class is a JavaFX controller that manages the practice section of the LingoQuest application. 
// It implements the Initializable interface, allowing it to set up and populate user interface components when the associated FXML file is loaded. 
// The class bridges the gap between the application's backend, represented by the LanguageGame and User classes, and the frontend, providing users with a dynamic and interactive experience for monitoring their progress in various language sections.
// The controller includes several UI elements linked to the FXML file: labels for displaying the user's username, coin balance, and answer streak, as well as progress bars to visually represent the completion status of different language sections such as food, transportation, colors, animals, and communication. 
// These progress bars are updated during the initialize method by retrieving the progress of corresponding sections using their unique UUIDs from the LanguageGame backend.
// The initialize method ensures that the backend logic and user data are correctly loaded, and it updates the UI components with the user's current stats and progress.
// The handleLogout method provides functionality to log out the current user and redirect them to the login page. 
// Additionally, the class offers navigation methods (goToProfile, goToLeaderboard, goToShop, and goToHome) to transition between different sections of the application.
// By integrating backend data with a visually rich frontend, the PracticeController enhances the user experience by allowing users to track their progress across various sections and seamlessly navigate through the app. 
// This controller is essential for providing users with a clear understanding of their achievements and areas for improvement in their language learning journey.