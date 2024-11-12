module lingoquest {
    requires javafx.controls;
    requires javafx.fxml;
    //requires json.simple;

    opens lingoquest to javafx.fxml;
    exports lingoquest;
}
