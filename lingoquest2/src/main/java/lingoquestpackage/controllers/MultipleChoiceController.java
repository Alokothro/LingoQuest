package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;
import lingoquestpackage.models.Word;

public class MultipleChoiceController implements Initializable {

    private User user;
    private LanguageGame languageGame;
    private ArrayList<Word> choices;

    // need to figure out how to properly deal with this
    private int numberOfQuestions = 2;

    @FXML
    private Label usernameField;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Button optionOne;

    @FXML
    private Button optionTwo;

    @FXML
    private Button optionThree;

    @FXML
    private Button optionFour;

    @FXML
    private ProgressBar lessonProgress;

    @FXML
    private Label lessonName;


    // constructor
    public MultipleChoiceController() {
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
                e.printStackTrace();
            }
        }

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

        // get the progress percentage
        double lesProgress = this.user.getCurrentLesson().getLessonProgress() / 1000; // divide by 100 to fix
        System.out.println(lesProgress + "les progress");
        lessonProgress.setProgress(lesProgress);

        // set the text on the top banner
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        // set the string of the question
        String questionString = languageGame.getCurrentQuestionString();
        questionLabel.setText(questionString);
        languageGame.speak(questionString);

        // initialize to be safe
        this.choices = new ArrayList<>();
        // get the answer choices
        this.choices = languageGame.getQuestionWords();
        // shuffle choices
        //Collections.shuffle(choices); // REMOVED, DONT NEED TO SHUFFLE
        // there should only be 4 answer choices for multiple choice
        if(choices.size() > 4) {
            System.out.println("More than 4 multiple choice answers in mc controller");
            return;
        }
        // set each option button
        optionOne.setText(choices.get(0).getWordinLanguage());
        optionTwo.setText(choices.get(1).getWordinLanguage());
        optionThree.setText(choices.get(2).getWordinLanguage());
        optionFour.setText(choices.get(3).getWordinLanguage());
    }

    public void handleOne() throws Exception {
        // make sure you have the facade
        if(languageGame == null)
            languageGame = LanguageGame.getInstance();
        // answer the question and store if it was correct
        boolean correct = languageGame.answerQuestion("1");
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }

    public void handleTwo() throws Exception {
        // make sure you have the facade
        if(languageGame == null)
            languageGame = LanguageGame.getInstance();
        // answer the question and store if it was correct
        boolean correct = languageGame.answerQuestion("2");
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }

    public void handleThree() throws Exception {
        // make sure you have the facade
        if(languageGame == null)
            languageGame = LanguageGame.getInstance();
        // answer the question and store if it was correct
        boolean correct = languageGame.answerQuestion("3");
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }

    public void handleFour() throws Exception {
        // make sure you have the facade
        if(languageGame == null)
            languageGame = LanguageGame.getInstance();
        // answer the question and store if it was correct
        boolean correct = languageGame.answerQuestion("4");
        // go to either correct or incorrect screen
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }
}