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

public class ConfirmBox {

    @FXML
    private Label messageQuestion;

    @FXML
    private Button buttonYes;

    @FXML
    private Button buttonNo;

    private boolean answer;

    // REQUIRES: ConfirmBox.fxml exists in same folder
    // MODIFIES: This
    // EFFECTS:  Display messages with title and messages as input
    //           Prompt user to click Yes or No to proceed and return result back
    public boolean confirm(String title, String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/layout/ConfirmBox.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage window = new Stage();
            Scene scene = new Scene(root);

            setMessageButton(window, message);

            window.setTitle(title);
            window.initStyle(StageStyle.UNDECORATED);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            System.out.println("Error: IOException at ConfirmBox Class");
        }
        return answer;
    }

    private void setMessageButton(Stage window, String message) {
        messageQuestion.setText(message);
        buttonYes.setOnAction(event -> {
            window.close();
            answer = true;
        });
        buttonNo.setOnAction(event -> {
            window.close();
            answer = false;
        });
        window.setOnCloseRequest(event -> {
            event.consume();
            window.close();
            answer = false;
        });
    }
}
