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
import javafx.scene.control.*;
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

    public LocalDate getDate(){
        return diaryDate.getValue();
    }

    //create diary entry, store to XML-File and stores to the listview on the homescreen
    public void createDiaryEntry(ActionEvent event) throws IOException {
        try {
            //create new entry by calling the method createNewEntry(with parameters title, location, notes and date)
            DiaryEntry newEntry = DiaryEntry.createNewEntry(diaryTitleTextfield.getText(), diaryLocationTextfield.getText(), diaryNotesTextfield.getHtmlText(), diaryDate.getValue(), tagEntryArrayListController);

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

        //Read data from FXML File
        //newEntry.setTitle(diaryTitleTextfield.getText());

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
    public void handleDeletePicture3(ActionEvent actionEvent){
        imageView3.setImage(null);
    }


    //Method to create TagEntry and add it to tagEntryArrayListController
    public void createTagEntry(ActionEvent event) throws IOException, TagEntryException {
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
