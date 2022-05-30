package at.jku.se.diary.controller;

import at.jku.se.diary.AlertBox;
import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.TagEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import org.controlsfx.*;

public class CreateDiaryEntryController implements Initializable {

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

    //Button TagEntry
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



    private ArrayList<TagEntry> tagEntryArrayListController = new ArrayList<>();



    private Stage stage;
    static private Scene scene;
    private Parent root;

    public LocalDate getDate() {
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
        newEntry.setTagEntryArrayList(tagEntryArrayListController);


        //proofs if the title field is empty or not inkl. AlertBox
        if (diaryTitleTextfield.getText().isEmpty()) {
            AlertBox.display("Error", "The title-field is empty!");
            return;
        }
        //proofs if the date field is empty or not inkl. AlertBox
        if (diaryDate.getValue() == null) {
            AlertBox.display("Error", "The date-field is empty!");
            return;
        }

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

        switchToHomescreen(event);

    }

    // Get back to the Homescreen -Method
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //----------- file chooser implementation ----------------

    //create a file chooser object
    final FileChooser fileChooser = new FileChooser();

    public void initialiseFileChooser() {
        //Set the title of the displayed file dialog
        fileChooser.setTitle("Foto auswählen");

        //Set the initial directory for the displayed file dialog
        //user.home refers to the path to the user directory
        fileChooser.setInitialDirectory(new File(System.getProperty(("user.home"))));

        //Gets the extension filters used in the displayed file dialog
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files ", "*.png", "*.jpg", "*.jpeg", "*.jfif"));

    }


    public void handleOpenPicture1(ActionEvent actionEvent) {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView1.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("invalid file");
        }
    }

    public void handleOpenPicture2(ActionEvent actionEvent) {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView2.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("invalid file");
        }
    }

    public void handleOpenPicture3(ActionEvent actionEvent) {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView3.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("invalid file");
        }

    }

    // Methods to delete de selected picture from the image view
    public void handleDeletePicture1(ActionEvent actionEvent) {
        imageView1.setImage(null);
    }

    public void handleDeletePicture2(ActionEvent actionEvent) {
        imageView2.setImage(null);
    }

    public void handleDeletePicture3(ActionEvent actionEvent) {
        imageView3.setImage(null);
    }


    //Method to create TagEntry and add it to tagEntryArrayListController
    public void createTagEntry(ActionEvent event) throws IOException {
        String tag = (String) tagChoiceBox.getValue();

        if (tag == null) {
            System.out.println("No Tag select");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please select a tag to add a Entry");
            a.setTitle("Nothing selected");
            a.show();
            return;
        }
        TagEntry tagEntry = new TagEntry();
        tagEntry.setTagText(tagTextfield.getText());
        tagEntry.setTag(tag);
        tagEntry.setRating((int)tagRating.getRating());
        tagEntry.setStarString("");
        for(int i = 0; i< tagEntry.getRating();i++) {
            tagEntry.setStarString(tagEntry.getStarString() + '★');
        }

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


    public void removeTagEntry(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tagEntryArrayListController.remove(selectedID);
        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));






    }
}
