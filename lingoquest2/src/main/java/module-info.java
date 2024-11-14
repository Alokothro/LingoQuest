module lingoquest_package {
    requires javafx.controls;
    requires javafx.fxml;

    opens lingoquest_package to javafx.fxml;
    exports lingoquest_package;
}
