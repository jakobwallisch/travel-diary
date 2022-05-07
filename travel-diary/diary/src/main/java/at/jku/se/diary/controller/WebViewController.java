package at.jku.se.diary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

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
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class WebViewController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static List<String> locations = new ArrayList<>();

    @FXML
    private WebView webView;
    @FXML
    private TextField webViewTextField;


    private WebEngine webEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        webEngine = webView.getEngine();

        loadPage(generateStringForURL());
    }

    public String generateStringForURL(){

        String result = "https://www.google.com/maps/dir/";

        System.out.println("Das ist die Länge der Liste mit Locations:"+locations.size());

        for(int i = 0; i < locations.size(); i++){

            result = result+locations.get(i)+"/+/";

            System.out.println(result);
        }

        return result;
    }

    public void loadPage(String url){
        //webEngine.load("https://www.google.com/maps/dir/Linz/+/Sch%C3%A4rding");
        webEngine.load(url);
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public void setLocationsOneEntry(String locationToStore){
        locations.clear();
        locations.add(locationToStore);
    }

    public List<String> getLocations(){
        return locations;
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
}
