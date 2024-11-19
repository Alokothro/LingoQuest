package lingoquestpackage.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lingoquestpackage.models.*;
import lingoquestpackage.narriator.Narriator;

/**
 * @author cade
 */
public class LoginSignupController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private LanguageGame languageGame;

    public LoginSignupController() {
        try {
            this.languageGame = new LanguageGame(); // initialize backend
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() throws Exception {
        if(languageGame == null)
            languageGame = new LanguageGame();
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

    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty.");
            return;
        }

        if (password.length() < 6) {
            messageLabel.setText("Password must be at least 6 characters long.");
            return;
        }

        languageGame.createUser(username, password);
        messageLabel.setText("Account created successfully! You can now log in.");
        messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
    }

    @FXML
    private void handleSpeak() {
        Narriator.playSound("Hola Mundo");
    }
}
