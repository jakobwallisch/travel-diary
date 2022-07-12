package at.jku.se.diary.controller;

import at.jku.se.diary.*;
import at.jku.se.diary.exceptions.DiaryEntryException;
import at.jku.se.diary.exceptions.TagEntryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is a controller class for the CreateDiaryEntry.fxml file
 */
public class CreateDiaryEntryController implements Initializable {

    @FXML
    TextField diaryTitleTextfield;
    @FXML
    TextField diaryLocationTextfield;
    @FXML
    HTMLEditor diaryNotesTextfield;
    @FXML
    DatePicker diaryDate;

    //image views for selected pictures
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;

    @FXML
    private ChoiceBox tagChoiceBox;

    @FXML
    private Rating tagRating;

    @FXML
    private TextField tagTextfield;

    //table View
    @FXML
    private TableView<TagEntry> tableView;
    @FXML
    private TableColumn<TagEntry, String> tagColumn;
    @FXML
    private TableColumn<TagEntry, String> textColumn;
    @FXML
    private TableColumn<TagEntry, String> starsColumn;


    private final ArrayList<TagEntry> tagEntryArrayListController = new ArrayList<>();

    /**
     * Getter method for the diaryDate field
     * @return returns the value of the diaryDate field
     */
    public LocalDate getDate(){
        return diaryDate.getValue();
    }

    //create diary entry, store to XML-File and stores to the listview on the homescreen

    /**
     * This method creates a new diary entry (calls DiaryEntry.createNewEntry method)
     * The new entry will be stored in the json file and will also be stored in the listView on the home-screen
     * @param event The button's action, which is invoked whenever the button is fired.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void createDiaryEntry(ActionEvent event) throws IOException {
        try {
            //create new entry by calling the method createNewEntry(with parameters title, location, notes and date)
            DiaryEntry newEntry = DiaryEntry.createNewEntry(
                    diaryTitleTextfield.getText(), diaryLocationTextfield.getText(),
                    diaryNotesTextfield.getHtmlText(), diaryDate.getValue(),
                    tagEntryArrayListController);

            //stores the URLs of the selected images
            if (!(imageView1.getImage() == null)) {
                newEntry.setPathPicture1(imageView1.getImage().getUrl());
            }
            if (!(imageView2.getImage() == null)) {
                newEntry.setPathPicture2(imageView2.getImage().getUrl());
            }
            if (!(imageView3.getImage() == null)) {
                newEntry.setPathPicture3(imageView3.getImage().getUrl());
            }
            //stores new entry in database
            Application.getInstance().getEntryDatabase().storeEntryInDatabase(newEntry);
            //switch to homescreen event is triggered
            switchToHomescreen(event);
        } catch (DiaryEntryException e) {
            AlertBox.display("Error", e.getMessage());
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

    //----------- file chooser implementation ----------------

    //create a file chooser object
    final FileChooser fileChooser = new FileChooser();

    /**
     * This method initializes the fileChooser object of the CreateDiaryEntry Screen
     */
    public void initialiseFileChooser() {
        //Set the title of the displayed file dialog
        fileChooser.setTitle("Foto auswählen");

        //Set the initial directory for the displayed file dialog
        //user.home refers to the path to the user directory
        fileChooser.setInitialDirectory(new File(System.getProperty(("user.home"))));

        //Gets the extension filters used in the displayed file dialog
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files ", "*.png", "*.jpg", "*.jpeg", "*.jfif"));

    }

    private final String errorMessage = "invalid file";

    /**
     * This method sets the selected picture to the imageview1
     */
    public void handleOpenPicture1() {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView1.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println(errorMessage);
        }
    }
    /**
     * This method sets the selected picture to the imageview2
     */
    public void handleOpenPicture2() {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView2.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println(errorMessage);
        }
    }
    /**
     * This method sets the selected picture to the imageview3
     */
    public void handleOpenPicture3() {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView3.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println(errorMessage);
        }

    }

    // Methods to delete de selected picture from the image view

    /**
     * This methods deletes the picture of the imageview1
     */
    public void handleDeletePicture1() {
        imageView1.setImage(null);
    }
    /**
     * This methods deletes the picture of the imageview2
     */
    public void handleDeletePicture2() {
        imageView2.setImage(null);
    }
    /**
     * This methods deletes the picture of the imageview3
     */
    public void handleDeletePicture3(){
        imageView3.setImage(null);
    }


    //Method to create TagEntry and add it to tagEntryArrayListController

    /**
     * This method creates an TagEntry and add it to the tagEntryArrayListController
     * @throws TagEntryException will be thrown if anything goes wrong while adding a tag to the entry
     */
    public void createTagEntry() throws TagEntryException {
        String tag = (String) tagChoiceBox.getValue();
        //GUI abhängig, deshalb nicht in methode ausgelagert
        if (tag == null) {
            System.out.println("No Tag select");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please select a tag to add a Entry");
            a.setTitle("Nothing selected");
            a.show();
            return;
        }
        TagEntry tagEntry = TagEntry.createNewTagEntry(tagTextfield.getText(), tag, (int) tagRating.getRating());

        tagEntryArrayListController.add(tagEntry);

        tagRating.setRating(2.0);
        tagTextfield.clear();
        tagChoiceBox.setValue(null);

        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);

        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("tagText"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("starString"));
    }

    /**
     * This method removes the selected tag from the tag-list of the created entry
     */
    public void removeTagEntry() {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tagEntryArrayListController.remove(selectedID);
        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);
    }

    /**
     * initializes the CreateDiaryEntryController (JavaFX Component)
     * initializes the tag-choice box and sets the tooltip
     * @param url JavaFX parameter
     * @param resourceBundle JavaFx parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));
    }
}
