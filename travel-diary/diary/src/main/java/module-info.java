/**
 * module-info
 */
module diary {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.web;
    requires org.controlsfx.controls;

    opens at.jku.se.diary;
    opens at.jku.se.diary.controller;
    opens at.jku.se.diary.database;
    opens at.jku.se.diary.exceptions;
}