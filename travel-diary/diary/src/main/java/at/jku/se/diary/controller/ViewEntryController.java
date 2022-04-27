package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewEntryController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label titleOfEntry;
    @FXML
    private Label dateOfTitleToView;
    @FXML
    private Label locationOfTitleToView;
    @FXML
    private Label notesOfTitleToView;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;

    //this entry will be displayed
    static DiaryEntry entryToView;

    public DiaryEntry getEntryToView() {
        return entryToView;
    }

    public void setEntryToView(DiaryEntry entryToView) {
        this.entryToView = entryToView;
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

    //Method to delete the displayed entry (Delete Button in ViewEntry-Window)
    @FXML
    void removeDiaryEntry(ActionEvent event) throws IOException {
        //Delete the entry from the database
        Application.getInstance().getEntryDatabase().deleteEntryInDatabase(entryToView);

        //Load the Homescreen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //shows the clicked entry in the entry view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleOfEntry.setText(entryToView.getTitle());
        dateOfTitleToView.setText(entryToView.getDate().toString());
        locationOfTitleToView.setText(entryToView.getLocation());
        notesOfTitleToView.setText(entryToView.getNotes());

        if (!(entryToView.getPathPicture1() == null)){
            imageView1.setImage(new Image(entryToView.getPathPicture1()));
        }
        if (!(entryToView.getPathPicture2() == null)){
            imageView2.setImage(new Image(entryToView.getPathPicture2()));
        }
        if (!(entryToView.getPathPicture3() == null)){
            imageView3.setImage(new Image(entryToView.getPathPicture3()));
        }
    }
}

