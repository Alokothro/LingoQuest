package lingoquestpackage.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;

public class HomeController {
    
    private LanguageGame languageGame;

    public HomeController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            //messageLabel.setText("test!");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        App.setRoot("lingoquestpackage/login");
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //messageLabel.setText("test!");
    }
}
