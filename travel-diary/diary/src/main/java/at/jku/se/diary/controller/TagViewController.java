package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TagViewController {

    @FXML
    private TextField addTagTextfield;

    private Stage stage;
    static private Scene scene;
    private Parent root;


    public void createTagEntry(ActionEvent event) throws IOException {

        // todo: check if tag already there
        //Application.getInstance().getEntryDatabase().
        Application.getInstance().getEntryDatabase().storeTagInDatabase(addTagTextfield.getText());
        addTagTextfield.clear();

    }

    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
