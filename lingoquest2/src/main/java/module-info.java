module lingoquestpackage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires json.simple;
    requires jlayer;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.services.polly;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.utils;
    requires software.amazon.awssdk.awscore;
    requires software.amazon.eventstream;
    requires org.slf4j;
    requires org.slf4j.simple;
    
    opens lingoquestpackage.controllers to javafx.fxml;
    opens lingoquestpackage.lingoquest to javafx.fxml;
    opens lingoquestpackage.models to javafx.fxml;
    opens lingoquestpackage.narriator to javafx.fxml;
    
    exports lingoquestpackage.controllers;
    exports lingoquestpackage.lingoquest;
    exports lingoquestpackage.models;
    exports lingoquestpackage.narriator;
}