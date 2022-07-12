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

/**
 * This is a controller class for the TagViewController.fxml file
 */
public class TagViewController implements Initializable {

    @FXML
    private TextField addTagTextfield;
    @FXML
    private ChoiceBox<String> tagChoiceBox;

    /**
     * This method creates a new tag object and stores it in the database (json-file)
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void createTagEntry() throws IOException {

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

    /**
     * Get back to the home-screen
     * @param event The button's action, which is invoked whenever the button is fired.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * initializes the choice box component on the the tag view screen (JavaFX Component)
     * @param url JavaFX parameter
     * @param resourceBundle JavaFx parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
    }

    @FXML
    private void removeTag() throws IOException {
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
