package at.jku.se.diary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateDiaryEntryController {

    @FXML
    TextField diaryTitleTextfield;
    @FXML
    TextField diaryLocationTextfield;
    @FXML
    TextField diaryNotesTextfield;
    @FXML
    DatePicker diaryDate;

    private Stage stage;
    static private Scene scene;
    private Parent root;

    //FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));


// Get back to the Homescreen -Method
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }


    public void createDiaryEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        HomeScreenController homeScreenController = loader.getController();

        if (diaryTitleTextfield.getText().isEmpty()){
            System.out.println("Das Titelfeld ist leer.");
            return;
        }else{
            homeScreenController.addToDiaryEntries(diaryTitleTextfield.getText());
            homeScreenController.updateOverview(diaryTitleTextfield.getText());
        }

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
