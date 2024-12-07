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
        friendLabel.setText("Friends: " + user.getFriendsList().size());
        coinsEarnedLabel.setText("Coins Earned: " + user.getCoinsEarned());
        pointLabel.setText("Points: " + user.getCurrentLanguage().getPointsEarned());
        answerStreak2.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());
        currentLanguage.setValue("Current Language: " + user.getCurrentLanguage().getLanguageName());
        progressLabel.setText("Current Language Progress: " + user.getCurrentLanguageProgress() + "%");
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
