/*
 * Source Code must not be used without permission of
 * Reinhold Plösch (reinhold.ploesch@gmail.com)
 * (c) Reinhold Plösch, 2020
 */
package at.jku.se.diary;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.File;


public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

/*        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        fxmlLoader.setRoot(new AnchorPane());
        Parent root = fxmlLoader.load();*/

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomeScreen.fxml"));
        Parent root = loader.load();

        //Parent root = new AnchorPane();


     //Titel des Fensters (der Stage)
        stage.setTitle("Reisetagebuch");

        //Setzt Icon des Fensters (der Stage)
        Image icon = new Image("img.png");
        stage.getIcons().add(icon);

        // -----Größe der Stage festlegen-----
        //stage.setHeight(420);
        //stage.setWidth(420);

        // -----Resizable true or false-----
        //stage.setResizable(false);

        // -----Vollbild ein- oder ausschalten
        //stage.setFullScreen(true);

        stage.setScene(new Scene(root));

        // Methode, damit die Stage angezeigt wird
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
