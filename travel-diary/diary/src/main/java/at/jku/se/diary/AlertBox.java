package at.jku.se.diary;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;


public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        //the window is always in the foreground
        window.initModality(Modality.APPLICATION_MODAL);

        //set title of the window
        window.setTitle(title);

        window.setMinWidth(250);

        //label with the text of the message-paramter
        Label label = new Label();
        label.setText(message);

        //close-button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        //window needs to be closed
        window.showAndWait();
    }

}
