/*
 * Source Code must not be used without permission of
 * Reinhold Plösch (reinhold.ploesch@gmail.com)
 * (c) Reinhold Plösch, 2020
 */
package at.jku.se.diary;

import at.jku.se.diary.database.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;



public class Application extends javafx.application.Application {

    private static Application instance;

    private Database database = new Database();

    public static Application getInstance() {
        return instance;
    }

    public Application() throws IOException {
        try {
            database.readEntriesFromDatabase();
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }

        try {
            database.readTagsFromDatabase();
        } catch (final IOException e) {
            e.printStackTrace(System.err);
        }

        // Initialize our Application Singleton
        if (instance != null) {
            throw new IllegalStateException("expected Application to be instantiated only once");
        }
        instance = this;
    }

    public Database getEntryDatabase() {
        return database;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HomeScreen.fxml"));
            Parent root = loader.load();

            //Titel des Fensters (der Stage)
            stage.setTitle("Travel Diary");

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
