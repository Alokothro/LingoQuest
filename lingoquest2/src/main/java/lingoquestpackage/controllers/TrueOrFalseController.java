package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;

public class TrueOrFalseController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    // need to figure out how to properly deal with this
    //private int numberOfQuestions = 2;

    @FXML
    private Label usernameField;

    @FXML
    private ProgressBar lessonProgress;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private Label lessonName;

    @FXML
    private Label question;


    // constructor
    public TrueOrFalseController() {
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

        // set the lesson progress
        double lesProgress = this.user.getCurrentLesson().getLessonProgress() / 1000; // divide by 100 to fix
        System.out.println(lesProgress + "les progress");
        lessonProgress.setProgress(lesProgress);

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

        // set the text to display the current question
        String questionString = languageGame.getCurrentQuestionString();
        question.setText(questionString);
        languageGame.speak(questionString);
    }

    public void handleTrue() throws IOException {
        // answer question and store result
        boolean correct = languageGame.answerQuestion("true");
        // go to correct or incorrect screen accordingly
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }

    public void handleFalse() throws IOException {
        // answer question and store result
        boolean correct = languageGame.answerQuestion("false");
        // go to correct or incorrect screen accordingly
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }
}
// This code defines the TrueOrFalseController class, which serves as a controller for the "True or False" functionality in a JavaFX-based application. 
// The class implements the Initializable interface, allowing it to set up and initialize data when the associated FXML file is loaded. 
// The class manages interactions with the user interface components, including labels, progress bars, and navigation buttons.
// It interacts with the application's backend logic via the LanguageGame and User models. 
// The constructor initializes the LanguageGame instance (the backend logic facade) and retrieves the current user object. 
// The initialize method sets up the initial state of the UI by populating fields like the username, coin balance, answer streak, lesson progress, and the current question. 
// If a lesson is not loaded, it redirects the user back to the home page. 
// The class also provides navigation methods (goToProfile, goToHome, etc.) that enable users to move between different views in the application. 
// Additionally, the handleLogout method logs out the current user and redirects to the login page.
// The handleTrue and handleFalse methods handle user responses to "True" or "False" questions. 
// These methods use the backend to verify the correctness of the user's answer and redirect them to either a "correct" or "incorrect" screen based on the result. 
// This class combines backend integration and user interaction to create a seamless and dynamic experience for users within the LingoQuest application.
