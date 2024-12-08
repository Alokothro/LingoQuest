package lingoquestpackage.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import lingoquestpackage.lingoquest.*;
import lingoquestpackage.narriator.*;

public class PrimaryController {
// plays sound
    @FXML
    private void switchToSecondary() throws IOException {
        Narriator.playSound("me llamo cade!");
        App.setRoot("/lingoquestpackage/secondary");
    }
}
// The PrimaryController class is a JavaFX controller for the primary view in the LingoQuest application. 
// This class facilitates navigation to the secondary view and integrates functionality for playing sound when the transition occurs.
// The controller defines a single method, switchToSecondary, which is annotated with @FXML to link it to a button or action in the associated FXML file. 
// When this method is called, it plays a predefined sound using the Narriator.playSound method, with the phrase "me llamo cade!" as its input. 
// After playing the sound, the method uses App.setRoot to change the scene to the secondary view by loading the secondary.fxml file.
// The PrimaryController is simple yet functional, combining multimedia feedback with scene transitions to enhance user interactivity and engagement in the application.
// It demonstrates how controllers can extend beyond basic navigation to provide a richer user experience.


