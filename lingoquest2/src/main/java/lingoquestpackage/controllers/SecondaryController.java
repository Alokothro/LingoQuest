package lingoquestpackage.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import lingoquestpackage.lingoquest.*;
import lingoquestpackage.narriator.*;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("/lingoquestpackage/primary");
    }
}