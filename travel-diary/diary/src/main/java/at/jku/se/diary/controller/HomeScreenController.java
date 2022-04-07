package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.database.EntryDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    EntryDatabase entryDatabase = new EntryDatabase();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public HomeScreenController() throws IOException {
    }


    // Listview on the Homescreen
    @FXML
    private ListView<String> diaryEntryListOverview;


    //in this list the titles of the diary entries get stored for the listview on the homescreen
    private  ArrayList<String> diaryEntriesTitles = entryDatabase.getTitlesOfAllDiaryEntries();


    //title gets stored in the diaryEntries list
    public void addToDiaryEntries(String title){
        diaryEntriesTitles.add(title);
    }
    //the overview on the homescreen gets updated with new titles of entries
    public void updateOverview(String title){
        diaryEntryListOverview.getItems().addAll(diaryEntriesTitles);

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     /*   ArrayList<String> entries = new ArrayList<>();

        for (DiaryEntry e: application.readDiaryEntries()) {
            entries.add(e.getTitle());
        }

        diaryEntryListOverview.getItems().addAll(entries);

      */
    }

}

