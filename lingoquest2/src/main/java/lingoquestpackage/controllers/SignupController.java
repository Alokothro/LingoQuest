package lingoquestpackage.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lingoquestpackage.models.LanguageGame;

public class SignupController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private LanguageGame languageGame;

    public SignupController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
        } catch (Exception e) {
            e.printStackTrace();
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
}
