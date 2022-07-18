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

/**
 * This is the main class of the program
 * it contains the main method to start the program
 */
public class Application extends javafx.application.Application {

    private static Application instance;

    private final Database database = new Database();

    /**
     * return the instance of the application
     * @return instance of the application class
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * Constructor of Application class
     */
    public Application() {
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

    /**
     * returns the entry database
     * @return the database object
     */
    public Database getEntryDatabase() {
        return database;
    }

    /**
     * start method to start the applicaiton
     * @param stage the stage objcet to be loaded
     * @throws Exception if the loader cannot load the root an exception will be thrown
     */
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

    /**
     * main method
     * @param args don't know how to descripe this
     */
    public static void main(String[] args) {
        launch();
    }
}
