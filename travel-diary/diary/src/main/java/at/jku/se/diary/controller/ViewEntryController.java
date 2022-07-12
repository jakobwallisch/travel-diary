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

/**
 * This is a controller class for the ViewEntryController.fxml file
 */
public class ViewEntryController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label titleOfEntry;
    @FXML
    private TextField titleOfEntryTextfield;
    @FXML
    private DatePicker dateOfTitleToView;
    @FXML
    private TextField locationOfTitleToView;
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

    private final WebViewController webViewController = new WebViewController();

    public final BigImageViewController bigImageViewController = new BigImageViewController();

    private ArrayList<TagEntry> tagEntryArrayListController;


    /**
     * Sets the entry which will be viewed
     * @param entryToView1 the entry which will be viewed
     */
    public static void setEntryToView(DiaryEntry entryToView1) {
        entryToView = entryToView1;
    }


    /**
     *The switch to Homescreen method switches from any screen on the GUI to the home-screen by loading and showing a new Stage
     * @param event event is used to trigger the switch after pressing the button in the GUI
    */
    public void switchToHomescreen(ActionEvent event) throws IOException {
        entryToView.setNotes(notesOfEntryToView.getHtmlText());
        entryToView.setDate(dateOfTitleToView.getValue());
        entryToView.setLocation(locationOfTitleToView.getText());
        entryToView.setTagEntryArrayList(tagEntryArrayListController);
        Application.getInstance().getEntryDatabase().updateEntryInDatabase(entryToView);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Method to delete the displayed entry (Delete Button in ViewEntry-Window)
    @FXML
    private void removeDiaryEntry(ActionEvent event) throws IOException {
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

    /**
     * selects/ initializes the entry to view
     * @param url JavaFX parameter
     * @param resourceBundle JavaFx parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleOfEntry.setText(entryToView.getTitle());
        titleOfEntryTextfield.setText(entryToView.getTitle());
        dateOfTitleToView.setValue(entryToView.getDate());
        locationOfTitleToView.setText(entryToView.getLocation());
        notesOfEntryToView.setHtmlText(entryToView.getNotes());
        tagEntryArrayListController = entryToView.getTagEntryArrayList();

        if (!(entryToView.getPathPicture1() == null)){
            imageView1.setImage(new Image(entryToView.getPathPicture1()));
        }
        if (!(entryToView.getPathPicture2() == null)){
            imageView2.setImage(new Image(entryToView.getPathPicture2()));
        }
        if (!(entryToView.getPathPicture3() == null)){
            imageView3.setImage(new Image(entryToView.getPathPicture3()));
        }

        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);

        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("tagText"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("starString"));

        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));
    }

    /**
     * The switch to web view method is used to switch the web view to a selected entry in the tableView
     * @param event event is used to trigger the switch after pressing the button in the GUI
     */
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

    /**
     *This method is used to remove the selected entry from the table view
     */
    public void removeTagEntry(){
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tagEntryArrayListController.remove(selectedID);
        ObservableList<TagEntry> list = FXCollections.observableArrayList(tagEntryArrayListController);
        tableView.setItems(list);

    }

    /**
     *The create tag entry method is used to create a tag entry and add it to the tag entry array list controller
     * create tag entry uses the method create new tag entry from the class TagEntry
     */
    //Method to create TagEntry and add it to tagEntryArrayListController
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
     * This method is used to delete a selected picture from the imageView1
     */
    public void handleDeletePicture1() {
        imageView1.setImage(null);
        entryToView.setPathPicture1(null);
    }
    /**
     * This method is used to delete a selected picture from the imageView2
     */
    public void handleDeletePicture2() {
        imageView2.setImage(null);
        entryToView.setPathPicture2(null);
    }
    /**
     * This method is used to delete a selected picture from the imageView3
     */
    public void handleDeletePicture3(){
        imageView3.setImage(null);
        entryToView.setPathPicture3(null);
    }

    //create a file chooser object
    final FileChooser fileChooser = new FileChooser();

    /**
     * This method is used to initialize the fileChooser for handling pictures
     *
     */
    public void initialiseFileChooser() {

        fileChooser.setTitle("Foto auswählen");
        fileChooser.setInitialDirectory(new File(System.getProperty(("user.home"))));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Image Files ", "*.png", "*.jpg", "*.jpeg", "*.jfif"));
    }

    /**
     * This method is used to set Pictures or null if no file has been selected
     * @throws IOException if the selected picture is null then an Exception will be thrown
     */
    public void handleOpenPicture1() throws IOException {

        initialiseFileChooser();

        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView1.setImage(new Image(file.toURI().toString()));
            entryToView.setPathPicture1(imageView1.getImage().getUrl());
            Application.getInstance().getEntryDatabase().updateEntryInDatabase(entryToView);
        } else {
            System.out.println("invalid file");
        }
    }
    /**
     * This method is used to set Pictures or null if no file has been selected
     * @throws IOException if the selected picture is null then an Exception will be thrown
     */
    public void handleOpenPicture2() throws IOException {

        initialiseFileChooser();

        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView2.setImage(new Image(file.toURI().toString()));
            entryToView.setPathPicture2(imageView2.getImage().getUrl());
            Application.getInstance().getEntryDatabase().updateEntryInDatabase(entryToView);
        } else {
            System.out.println("invalid file");
        }
    }
    /**
     * This method is used to set Pictures or null if no file has been selected
     * @throws IOException if the selected picture is null then an Exception will be thrown
     */
    public void handleOpenPicture3() throws IOException {

        initialiseFileChooser();
        //Set the selected file or null if no file has been selected
        File file = fileChooser.showOpenDialog(null); //shows a new file open dialog

        if (file != null) {
            imageView3.setImage(new Image(file.toURI().toString()));
            entryToView.setPathPicture3(imageView3.getImage().getUrl());
            Application.getInstance().getEntryDatabase().updateEntryInDatabase(entryToView);
        } else {
            System.out.println("invalid file");
        }
    }

    /**
     * This method is used to switch to the big image view
     * @param event event is necessary for the communication when the user clicks on an image to show it on the screen
     * @throws IOException If the FXMLLoader cannot load the big image view an exception will be thrown
     */
    public void viewImage3(ActionEvent event) throws IOException {

        bigImageViewController.setImageToView(imageView3.getImage());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BigImageView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method is used to switch to the big image view
     * @param event event is necessary for the communication when the user clicks on an image to show it on the screen
     * @throws IOException If the FXMLLoader cannot load the big image view an exception will be thrown
     */
    public void viewImage2(ActionEvent event) throws IOException {

        //sets the picture to view
        bigImageViewController.setImageToView(imageView2.getImage());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BigImageView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to switch to the big image view
     * @param event event is necessary for the communication when the user clicks on an image to show it on the screen
     * @throws IOException If the FXMLLoader cannot load the big image view an exception will be thrown
     */
    public void viewImage1(ActionEvent event) throws IOException {

        //sets the picture to view
        bigImageViewController.setImageToView(imageView1.getImage());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BigImageView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

