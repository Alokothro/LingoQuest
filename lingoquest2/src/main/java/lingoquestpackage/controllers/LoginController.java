package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.*;
import lingoquestpackage.narriator.Narriator;

/**
 * @author cade
 */
public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private LanguageGame languageGame;

    public LoginController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            //messageLabel.setText("test!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //messageLabel.setText("test!");
    }

    @FXML
    private void goToCreateAccount() throws IOException {
        App.setRoot("/lingoquestpackage/signup");
    }

    @FXML
    private void handleLogin() throws Exception {
        //messageLabel.setText("Inside of handleLogin");
        if(languageGame == null) {
            //messageLabel.setText("languageGameNull");
            languageGame = LanguageGame.getInstance();
        }
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        messageLabel.setText(username);

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty.");
            return;
        }

        languageGame.login(username, password);

        if (languageGame.hasCurrentUser()) {
            messageLabel.setText("Login successful!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            // go to home screen
            goToHome();
        } else {
            messageLabel.setText("Invalid username or password.");
            //messageLabel.setText(username);
        }
    }

    @FXML
    public void goToHome() throws IOException {
        App.setRoot("/lingoquestpackage/home");
    }
}
