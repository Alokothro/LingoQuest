package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;
import lingoquestpackage.models.Word;

public class MatchingController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    // need to figure out how to properly deal with this

    @FXML
    private Label usernameField;

    @FXML
    private Label answerStreak;
    @FXML TextField answerOne;
    @FXML TextField answerTwo;
    @FXML TextField answerThree;
    @FXML TextField answerFour;
    @FXML
    private Label coinLabel;
    @FXML Button submit1;
    @FXML Button submit2;
    @FXML Button submit3;
    @FXML Button submit4;
    

    @FXML
    private Label lessonName;

    @FXML
    private Label wordOne;

    @FXML
    private Label wordTwo;

    @FXML
    private Label wordThree;

    @FXML
    private Label wordFour;

    @FXML
    private Label options;

    private ArrayList<Word> words;

    // constructor
    public MatchingController() {
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
        // set up the facade and the user
        if(languageGame == null) {
            try {
                languageGame = LanguageGame.getInstance();
                this.user = languageGame.getUser();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // set the text on the top banner
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        // if a lesson isn't loaded, there can't be any questions
        if(this.user.getCurrentLesson() == null) {
            System.out.println("current lesson is null in questionController");
            // go back to home page
            try {
                App.setRoot("/lingoquestpackage/home");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }
        words = languageGame.getQuestionWords();
        // make list to hold the words
        ArrayList<Word> words = languageGame.getQuestionWords();

        
        wordOne.setText(words.get(0).getWordinLanguage());
        wordTwo.setText(words.get(1).getWordinLanguage());
        wordThree.setText(words.get(2).getWordinLanguage());
        wordFour.setText(words.get(3).getWordinLanguage());

        //options
        ArrayList<Word> shuffledWords = new ArrayList<>();
        for (Word word : words) {
    shuffledWords.add(word);
    }
    Collections.shuffle(shuffledWords);
        options.setText("" + shuffledWords.get(0).getEnglishVersion() + "\n" + shuffledWords.get(1).getEnglishVersion() + "\n" + shuffledWords.get(2).getEnglishVersion() + "\n" + shuffledWords.get(3).getEnglishVersion());
    }

    public void handleOne() throws Exception {
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        languageGame.setCorrectAnswer(words.get(0));
        boolean isCorrect =  languageGame.answerQuestion(answerOne.getText());

        if(isCorrect) {
            submit1.setStyle("-fx-background-color: green");
        } else {
            submit2.setStyle("-fx-background-color: red");
        }
    }
    public void handleTwo() throws Exception {
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        languageGame.setCorrectAnswer(words.get(1));
        boolean isCorrect =  languageGame.answerQuestion(answerTwo.getText());

        if(isCorrect) {
            submit2.setStyle("-fx-background-color: green");
        } else {
            submit2.setStyle("-fx-background-color: red");
        }
    }
    public void handleThree() throws Exception {
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        languageGame.setCorrectAnswer(words.get(2));
        boolean isCorrect =  languageGame.answerQuestion(answerThree.getText());

        if(isCorrect) {
            submit3.setStyle("-fx-background-color: green");
        } else {
            submit3.setStyle("-fx-background-color: red");
        }
    }
    public void handleFour() throws Exception {
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        languageGame.setCorrectAnswer(words.get(3));
        boolean isCorrect =  languageGame.answerQuestion(answerFour.getText());

        if(isCorrect) {
            submit4.setStyle("-fx-background-color: green");
        } else {
            submit4.setStyle("-fx-background-color: red");
        }
    }
}
