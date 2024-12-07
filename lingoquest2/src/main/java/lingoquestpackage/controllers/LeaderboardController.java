package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.LeaderboardEntry;
import lingoquestpackage.models.User;

public class LeaderboardController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;
    @FXML private Label firstPlace;
    @FXML private Label secondPlace;
    @FXML private Label thirdPlace;
    @FXML private TableView leaderboardTable;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, Integer> coinsColumn;

    public LeaderboardController() {
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
        // set the text on the top to hold the current user's username

        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        // get the leaderboard
        firstPlace.setText(languageGame.getLeaderBoard().getUsers().get(0).getUsername());
        secondPlace.setText(languageGame.getLeaderBoard().getUsers().get(1).getUsername());
        thirdPlace.setText(languageGame.getLeaderBoard().getUsers().get(2).getUsername());

        //fill the table with the leaderboard
         usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
         coinsColumn.setCellValueFactory(new PropertyValueFactory<>("coins"));

        // Set placeholder text
        leaderboardTable.setPlaceholder(new Label("No leaderboard data to display"));

        // Populate the table
        ArrayList<User> leaderboardUsers = languageGame.getLeaderBoard().getUsers();
        for (int i = leaderboardUsers.size() - 1; i >= 0; i--) {
            User user = leaderboardUsers.get(i);
            LeaderboardEntry entry = new LeaderboardEntry(user.getUsername(), user.getCoinsEarned());
            leaderboardTable.getItems().add(entry);
        }
    }

    // navigation buttons
    @FXML
    public void goToShop() throws IOException {
        App.setRoot("/lingoquestpackage/shop");
    }

    @FXML
    public void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
    }

    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("/lingoquestpackage/profile");
    }

    @FXML
    public void goToPractice() throws IOException {
        App.setRoot("/lingoquestpackage/practice");
    }
}
