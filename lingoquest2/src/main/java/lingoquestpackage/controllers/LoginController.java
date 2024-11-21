package lingoquestpackage.controllers;

import java.io.IOException;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToCreateAccount() throws IOException {
        App.setRoot("/lingoquestpackage/signup");
    }

    @FXML
    private void handleLogin() throws Exception {
        if(languageGame == null)
            languageGame = LanguageGame.getInstance();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty.");
            return;
        }

        languageGame.login(username, password);

        if (languageGame.hasCurrentUser()) {
            messageLabel.setText("Login successful!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            // Transition to the main application screen
            // TODO: Load the main dashboard or next screen
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }
}
