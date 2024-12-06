package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.FillInTheBlank;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.Matching;
import lingoquestpackage.models.MultipleChoice;
import lingoquestpackage.models.TrueOrFalse;
import lingoquestpackage.models.User;

public class QuestionController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    // need to figure out how to properly deal with this
    private int numberOfQuestions = 5;

    //@FXML
    //private Label usernameField;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    // unused currently
    @FXML
    private TableView table;

    @FXML
    private Label lessonName;

    @FXML
    private Label sectionName;

    @FXML
    private Button nextButton;


    // constructor
    public QuestionController() {
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
        // initialize
        //nextButton = new Button();

        // set the text on the top banner
        //usernameField.setText(user.getUsername());
        //coinLabel = new Label();

        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());

        // if a lesson isn't loaded, there can't be any questions
        if(this.user.getCurrentLesson() == null) {
            System.out.println("current lesson is null in questionController");
            // go back to home page
            try {
                goToHome();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }

        // question # cannot be negative
        if(languageGame.getCurrentQuestionNumber() < 0) {
            System.out.println("negative question number");
            return;
        }

        // set the text of the button based on the current question number
        if(languageGame.getCurrentQuestionNumber() == 0) {
            System.out.println("current question number = " + languageGame.getCurrentQuestionNumber() + "\n\n\n");
            nextButton.setText("Begin");
        }
        // if its a positive number and is less than the # of questions desired
        else if(languageGame.getCurrentQuestionNumber() > 0 && languageGame.getCurrentQuestionNumber() < numberOfQuestions) {
            System.out.println("current question number = " + languageGame.getCurrentQuestionNumber() + "\n\n\n");
            nextButton.setText("Next");
        }
        // else it must be >= # of questions
        else {
            System.out.println("current question number = " + languageGame.getCurrentQuestionNumber() + "\n\n\n");
            nextButton.setText("Exit");
        }

        // initialize to fix bug
        //lessonName = new Label();
        // set the label to display the current question
        lessonName.setText("Lesson - " + this.user.getCurrentLesson().getLessonName());

        // initialize to fix bug
        //sectionName = new Label();
        // set the label to display the current question
        sectionName.setText("Section - " + languageGame.getCurrentSection().getName());
    }

    public void handleNextButton() throws IOException {
        // button's action depends on current state
        switch(nextButton.getText()) {
            case "Begin": {
                // if beginning, then call this cleanup method
                languageGame.prepareForQuestions();
                this.makeQuestion(numberOfQuestions);
                break;
            }
            case "Next": {
                this.makeQuestion(numberOfQuestions);
                break;
            }
            case "Exit": {
                App.setRoot("/lingoquestpackage/home");
                break;
            }
        }
    }

    /**
     * @author cade
     * @param number of questions desired
     */
    public void makeQuestion(int number) {
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