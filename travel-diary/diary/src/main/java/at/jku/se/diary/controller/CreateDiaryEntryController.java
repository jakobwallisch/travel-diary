package at.jku.se.diary.controller;

import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.database.EntryDatabase;
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
import java.time.LocalDate;



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

    EntryDatabase entryDatabase = new EntryDatabase();

    public CreateDiaryEntryController() throws IOException {
    }

    //FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));

    public LocalDate getDate(){
        return diaryDate.getValue();
    }

    //create diary entry, store to XML-File and stores to the listview on the homescreen
    public void createDiaryEntry(ActionEvent event) throws IOException {

        DiaryEntry newEntry = new DiaryEntry();

        //Read data from FXML File
        newEntry.setTitle(diaryTitleTextfield.getText());
        newEntry.setLocation(diaryLocationTextfield.getText());
        newEntry.setNotes(diaryNotesTextfield.getText());
        newEntry.setDate(diaryDate.getValue());

        //only for testing
        System.out.println(diaryDate.getValue());

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

        //stores new entry in database
        entryDatabase.storeEntryInDatabase(newEntry);

        //entryDatabase.readEntriesFromDatabase();

        //switch to Homescreen
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Get back to the Homescreen -Method
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}