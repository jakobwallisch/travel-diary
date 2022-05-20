package at.jku.se.diary.controller;

import at.jku.se.diary.AlertBox;
import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
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

    //image views for selected pictures
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;

    //buttons for adding a picture
    @FXML
    private Button btnOpenPicture1;
    @FXML
    private Button btnOpenPicture2;
    @FXML
    private Button btnOpenPicture3;
    //buttons to delete a picture
    @FXML
    private Button btnDeletePicture1;
    @FXML
    private Button btnDeletePicture2;
    @FXML
    private Button btnDeletePicture3;

    @FXML
    private Button createDiaryEntryButton;


    private Stage stage;
    static private Scene scene;
    private Parent root;

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


        //proofs if the title field is empty or not inkl. AlertBox
        if (diaryTitleTextfield.getText().isEmpty()){
            AlertBox.display("Error", "The title-field is empty!");
            return;
        }
        //proofs if the date field is empty or not inkl. AlertBox
        if (diaryDate.getValue() == null){
            AlertBox.display("Error", "The date-field is empty!");
            return;
        }

        //stores the URLs of the selected images
        if(!(imageView1.getImage() == null)){
            newEntry.setPathPicture1(imageView1.getImage().getUrl());
        }
        if(!(imageView2.getImage() == null)){
            newEntry.setPathPicture2(imageView2.getImage().getUrl());
        }
        if(!(imageView3.getImage() == null)){
            newEntry.setPathPicture3(imageView3.getImage().getUrl());
        }
        //stores new entry in database
        Application.getInstance().getEntryDatabase().storeEntryInDatabase(newEntry);

        switchToHomescreen(event);

    }

    // Get back to the Homescreen -Method
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //----------- file chooser implementation ----------------

    //create a file chooser object
    final FileChooser fileChooser = new FileChooser();

    public void initialiseFileChooser(){
        //Set the title of the displayed file dialog
        fileChooser.setTitle("Foto auswählen");

        //Set the initial directory for the displayed file dialog
        //user.home refers to the path to the user directory
        fileChooser.setInitialDirectory(new File(System.getProperty(("user.home"))));

        //Gets the extension filters used in the displayed file dialog
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files ", "*.png","*.jpg", "*.jpeg", "*.jfif"));

    }


    public void handleOpenPicture1(ActionEvent actionEvent){

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if(file != null){
            imageView1.setImage(new Image(file.toURI().toString()));
        }else {
            System.out.println("invalid file");
        }
    }

    public void handleOpenPicture2(ActionEvent actionEvent){

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if(file != null){
            imageView2.setImage(new Image(file.toURI().toString()));
        }else {
            System.out.println("invalid file");
        }
    }

    public void handleOpenPicture3(ActionEvent actionEvent){

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if(file != null){
            imageView3.setImage(new Image(file.toURI().toString()));
        }else {
            System.out.println("invalid file");
        }

    }

    // Methods to delete de selected picture from the image view
    public void handleDeletePicture1(ActionEvent actionEvent){
            imageView1.setImage(null);
    }
    public void handleDeletePicture2(ActionEvent actionEvent){
        imageView2.setImage(null);
    }
    public void handleDeletePicture3(ActionEvent actionEvent){
        imageView3.setImage(null);
    }

}
