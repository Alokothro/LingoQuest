package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.Lesson;
import lingoquestpackage.models.Section;
import lingoquestpackage.models.User;

public class HomeController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private ProgressBar languageCompletion;

    // constructor
    public HomeController() {
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
    public void goToFriends() throws IOException {
        App.setRoot("/lingoquestpackage/friends");
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
        languageCompletion.setProgress(user.getCurrentLanguageProgress());


        // Root container
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // ScrollPane to contain the sections and lessons
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // Mock data
        List<Section> sections = languageGame.getAllSections();

        // Populate sections and lessons
        for (Section section : sections) {
            // Section container holds the sections
            VBox sectionBox = new VBox(5);
            // set the styling
            sectionBox.setPadding(new Insets(10));
            sectionBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #f0f0f0;");

            // section title
            Label sectionLabel = new Label(section.getName());
            // style of the label
            sectionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            sectionBox.getChildren().add(sectionLabel);

            // lessons in an HBox
            HBox lessonBox = new HBox(10);
            for (Lesson lesson : section.getAllLessons()) {
                Button lessonButton = new Button(lesson.getLessonName());
                lessonButton.setPrefSize(50, 50);
                // make the lesson green if completed, black if not
                lessonButton.setStyle("-fx-shape: 'M50,0A50,50,0,1,1,49.999,-0.001Z'; " +
                        (lesson.isCompleted() ? "-fx-background-color: green;" : "-fx-background-color: black;") +
                        "-fx-text-fill: white;");

                // Click event for starting a lesson
                lessonButton.setOnAction(e -> {
                    System.out.println("Starting: " + lesson.getLessonName());
                    languageGame.pickASection(section.getID());
                    languageGame.pickALesson(lesson.getLessonID());
                    System.out.println("Going to " + lesson.getLessonName());
                });

                lessonBox.getChildren().add(lessonButton);
            }

            sectionBox.getChildren().add(lessonBox);
            root.setPadding(new Insets(10));
            root.getChildren().add(sectionBox);
        }
    }

    /*private List<Section> getSections() {
        List<Section> sections = new ArrayList<>();
        /*for (int i = 1; i <= 3; i++) {
            Section section = new Section();
            section.setName("section"+ (i+1));
            ArrayList<Lesson> lessons = new ArrayList<Lesson>();
            // make lessons
            for (int j = 1; j <= 5; j++) {
                Lesson les = new Lesson();
                les.setLessonName("lesson"+ (j+1));
                lessons.add(les);
            }
            section.setLessons(lessons);
            sections.add(section);
        }
        return sections;*/
        //languageGame.getAvailableSections()
    //}
    
}
