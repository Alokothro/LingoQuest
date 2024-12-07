package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;

public class SignupController implements Initializable{
    
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
    public void initialize(URL url, ResourceBundle rb) {
        // set up the facade and the user
        if(languageGame == null) {
            try {
                languageGame = LanguageGame.getInstance();
                //this.user = languageGame.getUser();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void handleSignup() throws IOException {

        try {
            languageGame = LanguageGame.getInstance();
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
            messageLabel.setText("Account created successfully!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            App.setRoot("/lingoquestpackage/home");
            //this.user = languageGame.getUser();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("/lingoquestpackage/login");
    }
}
// This code defines the SignupController class, which serves as the controller for the sign-up functionality in the LingoQuest application. 
// It implements the Initializable interface, allowing the initialization of data and logic when the associated FXML file is loaded. 
// The class manages interactions between the user interface and the application's backend logic for creating new user accounts.
// The SignupController has three main UI components bound to the FXML file: a text field for the username, a password field for the password, and a label for displaying messages to the user. 
// The LanguageGame class acts as the backend logic facade to handle user creation and management.
// The initialize method ensures that the LanguageGame instance is properly set up when the controller is loaded. 
// The handleSignup method processes the user's sign-up request by validating the input fields. 
// If the username or password is empty, or if the password is less than six characters long, the method displays an appropriate error message in the message label. 
// If the input passes validation, the method calls the backend to create a new user, updates the message label to indicate success, and redirects the user to the home page.
// Additionally, the goToLogin method allows users to navigate to the login page if they already have an account. 
// Overall, the SignupController ensures seamless integration of user account creation with proper validation and navigation within the application.