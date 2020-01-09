package ui.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AlertBox {

    @FXML
    private Label alertMessage;
    @FXML
    private Button button;

    // REQUIRES: AlertBox.fxml exists in same folder
    // MODIFIES: This
    // EFFECTS:  Display alert messages with title and messages as input
    public void alert(String title, String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/layout/AlertBox.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage window = new Stage();
            Scene scene = new Scene(root);

            alertMessage.setText(message);
            button.setOnAction(event -> window.close());

            window.setTitle(title);
            window.initStyle(StageStyle.UNDECORATED);
            window.setMinWidth(300);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            System.out.println("Error: IOException at AlertBox Class");
        }
    }
}
