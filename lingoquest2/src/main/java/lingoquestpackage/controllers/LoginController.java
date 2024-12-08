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
// The LoginController class is a JavaFX controller for the login functionality in the LingoQuest application. 
// This controller connects the user interface to the backend logic, enabling users to log in to their accounts or navigate to the account creation screen. 
// It integrates with the LanguageGame backend to validate credentials and manage user sessions.
// The controller features three main UI components: a text field for entering the username, a password field for the password, and a label for displaying messages to the user. 
// The handleLogin method processes login attempts by validating user input, checking the credentials against the backend, and updating the messageLabel with the result. 
// If the credentials are valid, it transitions the user to the home screen using the goToHome method. 
// If the credentials are invalid or the input fields are empty, appropriate error messages are displayed.
// The goToCreateAccount method allows users to navigate to the account creation screen, making it easy to register if they don’t already have an account. 
// The constructor ensures the LanguageGame instance is initialized, providing access to the backend's user management features.
// By combining user interaction, navigation, and backend validation, the LoginController plays a critical role in securing access to the application and providing a seamless user experience.
// It ensures that users can log in efficiently and receive feedback on their login attempts.