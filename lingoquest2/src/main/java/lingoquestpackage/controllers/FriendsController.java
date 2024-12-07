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

        // load the table
        //System.out.println("Friends" + this.user.getFriendsList().get(0));
        table.setPlaceholder(new Label("No friends to display"));
        // make the columns
        TableColumn<MiniUser,String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<MiniUser,String>("username"));
        /*usernameColumn.setCellValueFactory(
            new PropertyValueFactory<>("username")
            );*/
        TableColumn<User,String> coinsColumn = new TableColumn<>("Coins Earned");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<MiniUser,String>("coinsEarned"));

        /*usernameColumn.setCellValueFactory(
            new PropertyValueFactory<>("coinsEarned")
            );*/
        table.getColumns().addAll(usernameColumn, coinsColumn);

        // data for the table
        for(UUID id : this.user.getFriendsList()) {
            // get the friend by their UUID
            User u = Users.getInstance().getUserByUUID(id);
            // add user, the factory should extract their info
            // make a mini user for the table
            MiniUser mU = new MiniUser(u.getUsername(), u.getCoinsEarned());
            // add to table
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