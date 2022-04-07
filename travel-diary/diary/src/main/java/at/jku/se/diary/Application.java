/*
 * Source Code must not be used without permission of
 * Reinhold Plösch (reinhold.ploesch@gmail.com)
 * (c) Reinhold Plösch, 2020
 */
package at.jku.se.diary;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HomeScreen.fxml"));
            Parent root = loader.load();

            //Titel des Fensters (der Stage)
            stage.setTitle("Reisetagebuch");

            //Setzt Icon des Fensters (der Stage)
            Image icon = new Image("img.png");
            stage.getIcons().add(icon);

            stage.setScene(new Scene(root));

            // Methode, damit die Stage angezeigt wird
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
