package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.TagEntry;
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
import java.util.ArrayList;
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
    private HTMLEditor notesOfEntryToView;

    //tableView with Columns
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TagEntry, String> tagColumn;
    @FXML
    private TableColumn<TagEntry, String> textColumn;
    @FXML
    private TableColumn<TagEntry, String> starsColumn;

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

    //this entry will be displayed
    static DiaryEntry entryToView;

    private WebViewController webViewController = new WebViewController();

    private ArrayList<TagEntry> tagEntryArrayListController = new ArrayList<>();

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
        notesOfEntryToView.setHtmlText(entryToView.getNotes());

        if (!(entryToView.getPathPicture1() == null)){
            imageView1.setImage(new Image(entryToView.getPathPicture1()));
        }
        if (!(entryToView.getPathPicture2() == null)){
            imageView2.setImage(new Image(entryToView.getPathPicture2()));
        }
        if (!(entryToView.getPathPicture3() == null)){
            imageView3.setImage(new Image(entryToView.getPathPicture3()));
        }

        ObservableList<TagEntry> list = FXCollections.observableArrayList(entryToView.getTagEntryArrayList());
        tableView.setItems(list);

        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("tagText"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("starString"));

        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));
    }

    //switch to the WebView of the selected entry in the tableview
    public void switchToWebView(ActionEvent event) throws IOException {

        //set the Location of the Entry which will be displayed
        webViewController.setLocationsOneEntry(entryToView.getLocation());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/WebView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void removeTagEntry(ActionEvent event) throws IOException {
        tagEntryArrayListController.addAll(entryToView.getTagEntryArrayList());
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tagEntryArrayListController.remove(selectedID);
        entryToView.setTagEntryArrayList(tagEntryArrayListController);
        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);

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

    // Methods to delete de selected picture from the image view
    public void handleDeletePicture1(ActionEvent actionEvent) {
        imageView1.setImage(null);
        entryToView.setPathPicture1(null);
    }

    public void handleDeletePicture2(ActionEvent actionEvent) {
        imageView2.setImage(null);
        entryToView.setPathPicture2(null);
    }
    public void handleDeletePicture3(ActionEvent actionEvent){
        imageView3.setImage(null);
        entryToView.setPathPicture3(null);
    }

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
            entryToView.setPathPicture1(imageView1.getImage().getUrl());
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
            entryToView.setPathPicture2(imageView2.getImage().getUrl());
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
            entryToView.setPathPicture3(imageView3.getImage().getUrl());
        } else {
            System.out.println("invalid file");
        }

    }

}

