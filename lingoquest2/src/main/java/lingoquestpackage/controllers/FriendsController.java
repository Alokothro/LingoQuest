package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.MiniUser;
import lingoquestpackage.models.User;
import lingoquestpackage.models.Users;

public class FriendsController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;
    @FXML TableColumn usernameColumn;
    @FXML TableColumn coinsColumn;
    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private TableView table;

    @FXML
    private TextField friendName;

    @FXML
    private Label requestName;

    // constructor
    public FriendsController() {
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

    // Set up the columns
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    coinsColumn.setCellValueFactory(new PropertyValueFactory<>("coinsEarned"));

    // Set placeholder text
    table.setPlaceholder(new Label("No friends to display"));

    // Populate the table
    for (UUID id : user.getFriendsList()) {
        User u = Users.getInstance().getUserByUUID(id);
        MiniUser mU = new MiniUser(u.getUsername(), u.getCoinsEarned());
        table.getItems().add(mU);
    }
       

        if(languageGame.getUser().getFriendRequests().isEmpty() == false) {
            UUID reqID = languageGame.getUser().getFriendRequests().get(0);
            User u = Users.getInstance().getUserByUUID(reqID);
            requestName.setText(u.getUsername());
        }
    }

    @FXML
    public void handleSend() {
        String name = friendName.getText().trim();
        // get the user by their name, find their uuid, then send a friend request
        languageGame.getUser().sendFriendRequest(Users.getInstance().getUserByName(name).getUUID());
        // empty out the text field
        friendName.setText("");
    }

    @FXML
    public void handleAccept() {
        if(languageGame.getUser().getFriendRequests().get(0) == null)
            return;
        // get the id of the requester
        UUID requestID = languageGame.getUser().getFriendRequests().get(0);
        languageGame.getUser().acceptFriendRequest(requestID);
        // empty the label
        requestName.setText("");
    }

    @FXML
    public void handleDeny() {
        if(languageGame.getUser().getFriendRequests().get(0) == null)
            return;
        // remove the request
        languageGame.getUser().getFriendRequests().remove(0);
        // empty the label
        requestName.setText("");
    }
}
// The FriendsController class is a JavaFX controller for managing the friends list and friend requests in the LingoQuest application. 
// This controller integrates with the backend logic, represented by the LanguageGame and User models, to allow users to view their friends, send friend requests, and manage incoming requests. 
// It dynamically populates the user interface with data and provides navigation and interaction functionality.
// The class initializes the user interface in the initialize method, which retrieves the current user's data and populates the friends table with their friends' usernames and earned coins. 
// The usernameColumn and coinsColumn in the TableView are dynamically populated using the MiniUser model, which holds essential information about each friend. 
// If the user has no friends, a placeholder message is displayed.
// The controller also handles incoming friend requests. If a friend request exists, the requester's username is displayed in a label. 
// The user can accept the request using the handleAccept method, which adds the requester to the user's friends list, or deny it using the handleDeny method, which removes the request from the list.
// The handleSend method allows the user to send a friend request by entering another user's username in the text field.
// The method retrieves the corresponding UUID and sends the request via the backend.
// Additionally, the class provides navigation methods (goToProfile, goToHome, goToPractice, etc.) for transitioning between different sections of the application and a handleLogout method for logging out the user and returning to the login page.
// The FriendsController enhances the social functionality of the LingoQuest application, providing a user-friendly interface for managing friendships and engaging with other users. 
// By dynamically updating the UI and integrating seamless navigation, it fosters interaction and community building within the app.
