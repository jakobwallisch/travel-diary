package at.jku.se.diary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This is a controller class for the WebView.fxml file
 */
public class WebViewController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static List<String> locations = new ArrayList<>();

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    /**
     * initializes the webEngine and the webView (JavaFX Components)
     * @param url JavaFX parameter
     * @param resourceBundle JavaFx parameter
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        loadPage(generateStringForURL());
    }

    /**
     * This method assembles a string which can be used to initialize Google Maps in the webView with the locations to view
     * @return returns the final String which contains all locations to view on the map
     */
    public String generateStringForURL(){

        String result = "https://www.google.com/maps/dir/";

        for (String location : locations) {
            result = result + location + "/+/";
        }
        return result;
    }

    /**
     * This method loads the webEngine
     * @param url this is the URL of the website which will be loaded in the webEngine
     */
    public void loadPage(String url){
        webEngine.load(url);
    }

    /**
     * This methods sets the locations which will be shown on the map
     * @param locations1 locations which will be shown on the map
     */
    public static void setLocations(List<String> locations1) {
        locations = locations1;
    }


    /**
     * Set the location to show on the map
     * @param locationToStore the location which will be shown on the map
     */
    public void setLocationsOneEntry(String locationToStore){
        locations.clear();
        locations.add(locationToStore);
    }

    /**
     *The switch to Homescreen method switches from any screen on the GUI to the home-screen by loading and showing a new Stage
     * @param event event is used to trigger the switch after pressing the button in the GUI
     * @throws IOException ioexception
     */
    public void switchToHomescreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Switch to the entry-view screen of the selected entry
     * @param event The button's action, which is invoked whenever the button is fired.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void switchToViewEntry(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewEntry.fxml"));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
