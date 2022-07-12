package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class HomeScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ViewEntryController viewEntryController = new ViewEntryController();
    private WebViewController webViewController = new WebViewController();

    @FXML
    private Button btnDirectory;

    //TableView on Homescreen
    @FXML
    private TableView<DiaryEntry> tableView;
    @FXML
    private TableColumn<DiaryEntry, String> titleColumn;
    @FXML
    private TableColumn<DiaryEntry, LocalDate> dateColumn;
    @FXML
    private TableColumn<DiaryEntry, String> locationColumn;

    //for filtering
    @FXML
    TextField titleFilterTextfield;
    @FXML
    TextField locationFilterTextfield;
    @FXML
    TextField notesFilterTextfield;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    private Rating tagRating;
    @FXML
    private ChoiceBox tagChoiceBox;
    @FXML
    private TextField tagTextTextfield;



    @FXML
        //this annotation is needed
        // Deletes the selected entry in the tableview
    void removeDiaryEntry(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        Application.getInstance().getEntryDatabase().deleteEntryInDatabase(tableView.getItems().get(selectedID));
        switchToHomescreen(event);
    }

    // Get to the CreateDiaryEntry Screen - Method --- for the "zurück zum Homescreen" button
    public void switchToCreateDiaryEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CreateDiaryEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //switches to the TagView window
    public void switchTagView(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/TagView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    //switch to the entry-view of the selected entry in the tableview
    public void switchToViewEntry(ActionEvent event) throws IOException {

        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        //sets the entry to view
        viewEntryController.setEntryToView(tableView.getItems().get(selectedID));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //switch to the WebView of the selected entry in the tableview
    public void switchToWebView(ActionEvent event) throws IOException {

        webViewController.setLocations(Application.getInstance().getEntryDatabase().getLocationsOfDiaryEntries());

        System.out.println(webViewController.getLocations().toString());
        System.out.println(webViewController.getLocations().size());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/WebView.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //This window initialises the tableview on the HomeScreen
    //also the filtering logic is stored in this method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnDirectory.setTooltip(new Tooltip("Click to choose a Folder where you want store diary entries. You must restart for these changes to take effect."));

        //creating a observableList for filtering purposes
        ObservableList<DiaryEntry> list = FXCollections.observableArrayList(
                Application.getInstance().getEntryDatabase().getDiaryEntries());
        //creating a filteredList with the items of the observableList for filtering purposes
        FilteredList<DiaryEntry> filterList = new FilteredList<>(list);
        //set the Items of the tableView
        tableView.setItems(filterList);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        //initialises the datePicker fields
        startDatePicker.setValue(LocalDate.of(2022, 1, 1));
        endDatePicker.setValue(LocalDate.now());
        startDatePicker.setValue(LocalDate.of(2022, 1, 1));
        endDatePicker.setValue(LocalDate.of(2022,8,1));
        //initialises tagChoiceBox
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setValue("all");
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));
        //initialises tagRating
        tagRating.setRating(0);
        //initialise tagTextField
        tagTextTextfield.setText("");

        //filtering logic
        filterList.predicateProperty().bind(Bindings.createObjectBinding(()
                        -> entry
                        -> entry.getTitle().toLowerCase().contains(titleFilterTextfield.getText().toLowerCase())
                        && ((entry.containsTagTextFilter(entry.getTagEntryArrayList(), tagTextTextfield.getText().toLowerCase()))
                        || tagTextTextfield.getText().equalsIgnoreCase(""))
                        && entry.getLocation().toLowerCase().contains(locationFilterTextfield.getText().toLowerCase())
                        && (((entry.getDate().isAfter(startDatePicker.getValue())) || entry.getDate().isEqual(startDatePicker.getValue()))
                        && ((entry.getDate().isBefore(endDatePicker.getValue())) || (entry.getDate().isEqual(endDatePicker.getValue()))))
                        && entry.getNotes().toLowerCase().contains(notesFilterTextfield.getText().toLowerCase())
                        && ((entry.containsTagFilter(entry.getTagEntryArrayList(), tagChoiceBox.getValue().toString()))
                        || (tagChoiceBox.getValue().equals("all")))
                        && ((entry.containsTagRatingFilter(entry.getTagEntryArrayList(), (int) tagRating.getRating(), tagChoiceBox.getValue().toString()))
                        || (tagRating.getRating() == 0)),


                titleFilterTextfield.textProperty(),
                locationFilterTextfield.textProperty(),
                startDatePicker.converterProperty(),
                endDatePicker.converterProperty(),
                notesFilterTextfield.textProperty(),
                tagChoiceBox.converterProperty(),
                tagRating.ratingProperty(),
                tagTextTextfield.textProperty()
        ));
    }

    //this method refreshes the datePicker fields
    public void refreshDate(ActionEvent event) throws IOException {
        titleFilterTextfield.setText("");
    }

    //resets the filter paramter to a default value
    public void resetFilter(ActionEvent event) throws IOException {
        titleFilterTextfield.setText("");
        locationFilterTextfield.setText("");
        startDatePicker.setValue(LocalDate.of(2021, 12, 1));
        endDatePicker.setValue(LocalDate.of(2022,8,1));
        tagChoiceBox.setValue("all");
        tagRating.setRating(0);
        tagTextTextfield.setText("");
        refreshDate(event);
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

    DirectoryChooser directoryChooser = new DirectoryChooser();

    public void initialiseDirectoryChooser() {
        //Set the title of the displayed file dialog
        directoryChooser.setTitle("Choose Folder");

        //Set the initial directory for the displayed file dialog
        //user.home refers to the path to the user directory
        directoryChooser.setInitialDirectory(new File(System.getProperty(("user.home"))));

    }
    @FXML
    public void chooseDirectory(ActionEvent event) throws IOException {

        initialiseDirectoryChooser();

        //Set the selected directory or null if no directory has been selected
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            System.out.println(selectedDirectory.getAbsolutePath());
            Application.getInstance().getEntryDatabase().storePathInDatabase(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("invalid directory");
        }
    }

}

