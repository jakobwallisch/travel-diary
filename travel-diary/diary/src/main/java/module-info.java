module diary {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;

    opens at.jku.se.diary;
    opens at.jku.se.diary.controller;
    opens at.jku.se.diary.database;
}