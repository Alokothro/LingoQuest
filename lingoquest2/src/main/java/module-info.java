module lingoquestpackage {
    requires javafx.controls;
    requires javafx.fxml;

    opens lingoquestpackage to javafx.fxml;
    exports lingoquestpackage;
}
