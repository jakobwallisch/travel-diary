package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.TagEntry;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HomeScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ViewEntryController viewEntryController = new ViewEntryController();
    private WebViewController webViewController = new WebViewController();

    //TableView on Homescreen
    @FXML
    private TableView<DiaryEntry> tableView;
    @FXML
    private TableColumn<DiaryEntry, String> titleColumn;
    @FXML
    private TableColumn<DiaryEntry, LocalDate> dateColumn;
    @FXML
    private TableColumn<DiaryEntry, String> locationColumn;
    @FXML
    private ChoiceBox tagChoiceBox;

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
        //this annotation is needed
        // Deletes the selected entry in the tableview
    void removeDiaryEntry(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        Application.getInstance().getEntryDatabase().deleteEntryInDatabase(tableView.getItems().get(selectedID));
        viewEntryController.switchToHomescreen(event);
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

        //creating a observableList for filtering purposes
        ObservableList<DiaryEntry> list = FXCollections.observableArrayList(Application.getInstance().getEntryDatabase().getDiaryEntries());
        //creating a filteredList with the items of the observableList for filtering purposes
        FilteredList<DiaryEntry> filterList = new FilteredList<>(list);
        //set the Items of the tableView
        tableView.setItems(filterList);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        //initialises the datePicker fields
        startDatePicker.setValue(LocalDate.of(2022, 01, 01));
        endDatePicker.setValue(LocalDate.now());
        //initialises tagChoiceBox
        tagChoiceBox.getItems().addAll(Application.getInstance().getEntryDatabase().getTagEntries());
        tagChoiceBox.setValue("all");
        tagChoiceBox.setTooltip(new Tooltip("Please choose a Tag"));

        //filtering logic
        filterList.predicateProperty().bind(Bindings.createObjectBinding(()
                        -> entry
                        -> entry.getTitle().toLowerCase().contains(titleFilterTextfield.getText().toLowerCase())
                        && entry.getLocation().toLowerCase().contains(locationFilterTextfield.getText().toLowerCase())
                        && (((entry.getDate().isAfter(startDatePicker.getValue())) || entry.getDate().isEqual(startDatePicker.getValue()))
                        && ((entry.getDate().isBefore(endDatePicker.getValue())) || (entry.getDate().isEqual(endDatePicker.getValue()))))
                        && entry.getNotes().toLowerCase().contains(notesFilterTextfield.getText().toLowerCase())
                        && (entry.containsTagFilter(entry.getTagEntryArrayList(), tagChoiceBox.getValue().toString())) || (tagChoiceBox.getValue().equals("all")),

                titleFilterTextfield.textProperty(),
                locationFilterTextfield.textProperty(),
                startDatePicker.converterProperty(),
                endDatePicker.converterProperty(),
                notesFilterTextfield.textProperty(),
                tagChoiceBox.converterProperty()
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
        endDatePicker.setValue(LocalDate.now());
        tagChoiceBox.setValue("all");
        refreshDate(event);
    }
}

