package at.jku.se.diary.controller;

import at.jku.se.diary.Application;
import at.jku.se.diary.DiaryEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ViewEntryController viewEntryController = new ViewEntryController();

    //TableView on Homescreen
    @FXML
    private TableView<DiaryEntry> tableView;

    @FXML
    private TableColumn<DiaryEntry, String> titleColumn;
    @FXML
    private TableColumn<DiaryEntry, LocalDate> dateColumn;
    @FXML
    private TableColumn<DiaryEntry, String> locationColumn;

    // Wieseo FXML hier?
    // Deletes the selected entry in the tableview
    @FXML
    void removeDiaryEntry(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        Application.getInstance().getEntryDatabase().deleteEntryInDatabase(tableView.getItems().get(selectedID));
        tableView.getItems().remove(selectedID);
    }



    //the overview on the homescreen gets updated with new entries
    public void updateTableView(DiaryEntry entry){
        tableView.getItems().addAll(entry);
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


    //initialises the tableview with the entries which are stored in the database
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getItems().addAll(Application.getInstance().getEntryDatabase().getDiaryEntries());
        titleColumn.setCellValueFactory(new PropertyValueFactory<DiaryEntry, String>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<DiaryEntry, LocalDate>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<DiaryEntry, String>("location"));
    }


}

