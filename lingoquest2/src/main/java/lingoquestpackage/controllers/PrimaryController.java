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
