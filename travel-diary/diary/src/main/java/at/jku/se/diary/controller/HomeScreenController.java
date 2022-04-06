package at.jku.se.diary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HomeScreenController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    // Listview on the Homescreen
    @FXML
    private ListView<String> diaryEntryListOverview;

    //in this list the titles of the diary entries get stored for the listview on the homescreen
    private static ArrayList<String> diaryEntries = new ArrayList<>();


    //title gets stored in the diaryEntries list
    public void addToDiaryEntries(String title){
        diaryEntries.add(title);
    }
    //the overview on the homescreen gets updated with new titles of entries
    public void updateOverview(String title){
        diaryEntryListOverview.getItems().addAll(diaryEntries);

    }


    // Get to the CreateDiaryEntry Screen - Method --- for the "zurück zum Homescreen" button
    public void switchToCreateDiaryEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CreateDiaryEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

