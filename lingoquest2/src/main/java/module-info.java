module lingoquestpackage {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires json.simple;
    requires jlayer;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.services.polly;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.utils;
    requires org.slf4j;
    requires org.slf4j.simple;
    requires software.amazon.awssdk.awscore;
    requires software.amazon.eventstream;
    requires javafx.base;

    opens lingoquestpackage.controllers to javafx.fxml;
    exports lingoquestpackage.controllers;

    opens lingoquestpackage.lingoquest to javafx.fxml;
    exports lingoquestpackage.lingoquest;

    opens lingoquestpackage.models to javafx.fxml;
    exports lingoquestpackage.models;

    opens lingoquestpackage.narriator to javafx.fxml;
    exports lingoquestpackage.narriator;

    //opens lingoquestpackage.data to javafx.fxml;
    //exports lingoquestpackage.data;
}
