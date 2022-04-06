package at.jku.se.diary.controller;

import at.jku.se.diary.DiaryEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//imports for storing in XML-File
import java.beans.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;

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

    public LocalDate getDate(){
        return diaryDate.getValue();
    }

    //create diary entry, store to XML-File and stores to the listview on the homescreen
    public void createDiaryEntry(ActionEvent event) throws IOException {

        DiaryEntry newEntry = new DiaryEntry();

        newEntry.setTitle(diaryTitleTextfield.getText());
        newEntry.setLocation(diaryLocationTextfield.getText());
        newEntry.setNotes(diaryNotesTextfield.getText());
        newEntry.setDate(getDate());


        try {
            FileOutputStream fos = new FileOutputStream(new File("/.database.xml"));
            XMLEncoder encoder = new XMLEncoder(fos);


            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });



            encoder.writeObject(newEntry);
            encoder.close();
            fos.close();
        }catch (IOException ex){
            System.out.println("-------------------Error while storing in XML-File--------------------");
            ex.printStackTrace();
       }

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
