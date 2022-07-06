package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TagViewController implements Initializable {

    @FXML
    private TextField addTagTextfield;

    @FXML
    private ChoiceBox<String> tagChoiceBox;


    private Stage stage;
    static private Scene scene;
    private Parent root;


    public void createTagEntry(ActionEvent event) throws IOException {

        if (addTagTextfield.getText().isBlank()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION );
            a.setContentText("Write Tag in Textfield to add");
            a.setTitle("Empty Textfield");
            a.show();
            return;
        }
        if (!Application.getInstance().getEntryDatabase().getTagEntries().contains(addTagTextfield.getText())) {
            Application.getInstance().getEntryDatabase().storeTagInDatabase(addTagTextfield.getText());
            addTagTextfield.clear();
            tagChoiceBox.getItems().clear();
            tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        }
        else {
            addTagTextfield.clear();
            Alert a = new Alert(Alert.AlertType.INFORMATION );
            a.setContentText("This tag is already exists");
            a.setTitle("Tag already exists");
            a.show();

        }

    }

    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    // Maybe use ComboBox => has a setPromptText property
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
    }

    @FXML
    void removeTag(ActionEvent event) throws IOException {
        String tag = tagChoiceBox.getValue();
        if (tag==null) {
            System.out.println("leerer String");
            Alert a = new Alert(Alert.AlertType.INFORMATION );
            a.setContentText("Please select a tag to remove");
            a.setTitle("Nothing selected");
            a.show();
            return;

        }
        Application.getInstance().getEntryDatabase().deleteTagInDatabase(tag);
        tagChoiceBox.getItems().clear();
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
    }

}
