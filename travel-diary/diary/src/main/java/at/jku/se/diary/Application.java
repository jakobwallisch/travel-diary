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

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;


public class Application extends javafx.application.Application {

    //private static File database = new File("/.database.xml");
    private static File database = new File("D:\\travel-diary SE PR\\database.xml");


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

    public void storeDiaryEntry(DiaryEntry newEntry){
        try {
            FileOutputStream fos = new FileOutputStream(database);
            XMLEncoder encoder = new XMLEncoder(fos);


            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });



            encoder.writeObject(newEntry);
            encoder.close();
            fos.close();
        }catch (IOException ex){
            System.out.println("-------------------Error while storing in XML-File--------------------");
            ex.printStackTrace();
        }
    }
}
