package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.User;

public class FillInTheBlankController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    // need to figure out how to properly deal with this
    private int numberOfQuestions = 2;

    @FXML
    private Label usernameField;
    @FXML ProgressBar lessonProgress;
    @FXML
    private Label question;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    // table is unused currently
    @FXML
    private TableView table;

    @FXML
    private Label lessonName;

    @FXML
    private Button nextButton;

    @FXML
    private TextField answerField;

    @FXML
    private Button submitButton;


    // constructor
    public FillInTheBlankController() {
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


        double lesProgress = this.user.getCurrentLesson().getLessonProgress() / 1000; // divide by 100 to fix
        System.out.println(lesProgress + "les progress");
        lessonProgress.setProgress(lesProgress);

        // set the question text
        String questionString = languageGame.getCurrentQuestionString();
        question.setText(questionString);
        languageGame.speak(questionString);

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

        
    }

    public void handleSubmit() throws Exception {
        // make sure to have access to the facade
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }

        // get the answer that was typed
        String answer = answerField.getText().trim();
        // answer the question and store if the answer was right
        boolean correct = languageGame.answerQuestion(answer);

        // go to either correct or incorrect screen
        if(correct)
            App.setRoot("/lingoquestpackage/correct");
        else
            App.setRoot("/lingoquestpackage/incorrect");
    }
}
// The FillInTheBlankController class is a JavaFX controller for managing the "Fill in the Blank" question interface in the LingoQuest application. 
// It integrates with the backend logic via the LanguageGame and User models to present questions, accept user input, and provide feedback on whether the user's answers are correct.
// During initialization, the initialize method sets up the user interface by populating labels with the current user's information, such as their username, coin balance, and answer streak. 
// The lesson progress is displayed as a progress bar, and the current question is retrieved from the backend and displayed in the question label. 
// The method also uses the languageGame.speak method to audibly present the question for an enhanced learning experience. 
// If no lesson is loaded, the user is redirected to the home page.
// The user submits their answer through the handleSubmit method, which retrieves the input from the answerField text field. 
// The input is validated by the backend, and based on whether the answer is correct, the user is redirected to either the "correct" or "incorrect" feedback page. 
// This functionality ensures immediate feedback, enhancing the learning process.
// The controller also includes navigation methods (goToProfile, goToHome, goToPractice, etc.) that allow users to seamlessly move between different sections of the application. 
// The handleLogout method logs out the current user and redirects them to the login page.
// By dynamically setting up the UI and providing immediate feedback on user input, the FillInTheBlankController supports an interactive and engaging learning experience. 
// Its integration with the backend ensures accurate validation and smooth navigation, making it an essential component of the LingoQuest application.

