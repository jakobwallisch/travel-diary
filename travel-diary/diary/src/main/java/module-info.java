module diary {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens at.jku.se.diary;
    opens at.jku.se.diary.controller;
}