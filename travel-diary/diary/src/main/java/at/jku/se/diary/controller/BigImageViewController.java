package at.jku.se.diary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BigImageViewController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static List<String> locations = new ArrayList<>();

    @FXML
    private ImageView imageView;

    private static Image imageToView;


    public Image getImageToView() {
        return imageToView;
    }

    public void setImageToView(Image imageToView) {
        this.imageToView = imageToView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(getImageToView());
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

    // Get back to the Homescreen -Method
    public void backToCreateEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateDiaryEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Get back to the Homescreen -Method
    public void switchToViewEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
