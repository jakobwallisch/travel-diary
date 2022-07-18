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
import java.util.ResourceBundle;

/**
 * This is a controller class for the BigImageView.fxml file
 */
public class BigImageViewController implements Initializable {

    /**
     * constructor of BigImageViewController class
     */
    public BigImageViewController() {

    }

    @FXML
    private ImageView imageView;
    private static Image imageToView;

    /**
     * getter for the image to view
     * @return the image to view
     */
    public Image getImageToView() {
        return imageToView;
    }

    /**
     * Setter for the image which will be viewed
     * @param imageToView image which will be viewed
     */
    public void setImageToView(Image imageToView) {
        BigImageViewController.imageToView = imageToView;
    }

    /**
     * initializes the BigImageViewController (JavaFX Component)
     * @param url JavaFX parameter
     * @param resourceBundle JavaFx parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(getImageToView());
    }


    /**
     * Get back to the ViewEntry Screen
     * @param event The button's action, which is invoked whenever the button is fired.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void switchToViewEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewEntry.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
