package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
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
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    // Listview on the Homescreen
    @FXML
    private ListView<String> diaryEntryListOverview;

    //the overview on the homescreen gets updated with new titles of entries
    public void updateOverview(String title){
        diaryEntryListOverview.getItems().add(title);
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
        diaryEntryListOverview.getItems().addAll(
                Application.getInstance().getEntryDatabase().getTitlesOfAllDiaryEntries());
    }

}

