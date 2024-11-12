module lingoquest {
    requires javafx.controls;
    requires javafx.fxml;

    opens lingoquest to javafx.fxml;
    exports lingoquest;
}
