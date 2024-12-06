package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;

public class CorrectController implements Initializable{

    private LanguageGame languageGame;

    // FXML fields
    @FXML private Button homeButton;
    @FXML private Button practiceButton;
    @FXML private Button leaderboardButton;
    @FXML private Button shopButton;
    @FXML private Button profile;
    @FXML private Button logoutButton;
    @FXML private Button nextButton;

    @FXML private Label usernameField;
    @FXML private Label coinLabel;
    @FXML private Label answerStreak;
    @FXML private Label lessonName;
    @FXML private Label pointsAdded;
    @FXML private Label coinsAdded;
    @FXML private Label corrrectAnswer; // Note: "corrrectAnswer" matches FXML typo; correct it in the FXML if needed.

    @FXML private ProgressBar progressBar;

    public CorrectController() throws Exception {
        this.languageGame = LanguageGame.getInstance();
    }

    // navigation buttons
    @FXML
    private void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
    }

    @FXML
    private void goToLeaderboard() throws IOException {
        App.setRoot("/lingoquestpackage/leaderboard");
    }

    @FXML
    private void goToPractice() throws IOException {
        App.setRoot("/lingoquestpackage/practice");
    }

    @FXML
    private void goToShop() throws IOException {
        App.setRoot("/lingoquestpackage/shop");
    }

    @FXML
    private void goToProfile() throws IOException {
        App.setRoot("/lingoquestpackage/profile");
    }

    @FXML
    private void handleLogout() throws Exception {
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
    private void handleNextButton() throws IOException {
        App.setRoot("/lingoquestpackage/defaultQuestion");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize facade
        if(languageGame == null)
            try {
                this.languageGame = LanguageGame.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        // initialize progress bar
        progressBar = new ProgressBar();
        // make sure there's a lesson to access
        if(languageGame.getUser().getCurrentLesson() == null)
            return;
        // get the lesson progress
        double progress = languageGame.getLessonProgress(languageGame.getUser().getCurrentLesson());
        // if it's above zero, animate the bar going down (incorrect answer)
        if(progress > 0.0) {
            // i want to animate, but will save for later TODO
            this.progressBar.setProgress(progress);
        }
    }

    /**
     * Animates the ProgressBar from a start value to an end value over a given duration.
     *
     * @param startProgress The starting progress value (e.g., 0.0 for 0%).
     * @param endProgress   The ending progress value (e.g., 1.0 for 100%).
     * @param duration      The duration of the animation in seconds.
     */
    /*private void animateProgressBar(double startProgress, double endProgress, int duration) {
        // Create a timeline with duration 5 seconds and update progress every 100ms

        Timeline timeline = new Timeline(new KeyFrame(Util.Duration.millis(100), event -> {

            double progress = progressBar.getProgress(); 

            // Update progress based on your logic, e.g., increment by 0.01

            progress += 0.01;

            progressBar.setProgress(progress);

        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat animation indefinitely

        timeline.play(); 
    }*/

}

