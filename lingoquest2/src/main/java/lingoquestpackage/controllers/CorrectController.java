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
import lingoquestpackage.models.FillInTheBlank;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.Matching;
import lingoquestpackage.models.MultipleChoice;
import lingoquestpackage.models.TrueOrFalse;
import lingoquestpackage.models.User;

public class CorrectController implements Initializable{

    private LanguageGame languageGame;
    private User user;
    private int questionAmount = 5;

    // FXML fields
    @FXML private Button homeButton;
    @FXML private Button practiceButton;
    @FXML private Button leaderboardButton;
    @FXML private Button shopButton;
    @FXML private Button profile;
    @FXML private Button logoutButton;
    @FXML private Button nextButton;
    @FXML private ProgressBar lessonProgress;
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
        this.user = languageGame.getUser();
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
    private void handleNextButton() throws Exception {
        //App.setRoot("/lingoquestpackage/defaultQuestion");
        makeQuestion(questionAmount);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize facade
        if(languageGame == null)
            try {
                this.languageGame = LanguageGame.getInstance();
                // set the user
                this.user = languageGame.getUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        // initialize progress bar
        double lesProgress = this.user.getCurrentLesson().getLessonProgress() / 1000; // divide by 100 to fix
        System.out.println(lesProgress + "les progress");
        lessonProgress.setProgress(lesProgress);

        // set the text on the top banner
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());
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


    /**
     * @author cade
     * @param number of questions desired
          * @throws Exception 
          */
         public void makeQuestion(int number) throws Exception {
        if(this.languageGame == null)
            this.languageGame = languageGame.getInstance();
        if(this.user == null)
            this.user = languageGame.getUser();
        if(languageGame.getCurrentQuestionNumber() == number) 
            App.setRoot("/lingoquestpackage/defaultQuestion");
        try {
            // param is the total # of questions, returns boolean of whether question was
            // created (won't be created if we've already answered more than total # we wanted
            if(languageGame.getQuestions(number)) {
                //System.out.println("question made");
                if(this.user.getCurrentLesson().getCurrentQuestion() instanceof Matching) {
                    System.out.println("Moving to matching question");
                    App.setRoot("/lingoquestpackage/matching");
                }
                if(this.user.getCurrentLesson().getCurrentQuestion() instanceof TrueOrFalse) {
                    System.out.println("Moving to true or false question");
                    App.setRoot("/lingoquestpackage/trueOrFalse");
                }
                if(this.user.getCurrentLesson().getCurrentQuestion() instanceof FillInTheBlank) {
                    System.out.println("Moving to fillintheblank question");
                    App.setRoot("/lingoquestpackage/fillInBlank");
                }
                if(this.user.getCurrentLesson().getCurrentQuestion() instanceof MultipleChoice) {
                    System.out.println("Moving to Multiple choice question");
                    App.setRoot("/lingoquestpackage/multipleChoice");
                }
            }
        //}
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
// The CorrectController class is a JavaFX controller that manages the interface displayed when a user answers a question correctly in the LingoQuest application. 
// It integrates with the backend logic via the LanguageGame and User models to provide feedback, update progress indicators, and allow the user to proceed to the next question or navigate to other sections of the application.
// During initialization, the initialize method sets up the user interface by displaying relevant user data, such as the username, coin balance, answer streak, and lesson progress. 
// The lesson progress is displayed using a progress bar that dynamically reflects the user's advancement. 
// The controller prepares the interface to show the user’s success and motivate continued participation.
// The handleNextButton method enables the user to proceed to the next question in the lesson. 
// It uses the makeQuestion method to determine the type of the next question (e.g., matching, true/false, fill-in-the-blank, or multiple-choice) and navigates to the appropriate interface. 
// This dynamic question handling ensures seamless progression through the lesson.
// Navigation methods such as goToHome, goToLeaderboard, and goToPractice allow users to move between different sections of the application, while handleLogout logs the user out and redirects them to the login page. 
// The controller ensures smooth navigation and feedback, keeping users engaged and motivated.
// By providing immediate feedback on correct answers and integrating navigation and progression functionality, the CorrectController supports a positive and engaging learning experience. 
// Its seamless integration with the backend ensures accurate tracking and smooth transitions, making it an essential component of the LingoQuest application's lesson flow.




