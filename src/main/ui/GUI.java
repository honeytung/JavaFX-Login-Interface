package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Student;
import model.StudyRoom;
import model.exceptions.TimeInvalidException;
import network.TimeAPI;
import ui.layout.AlertBox;
import ui.layout.ConfirmBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class GUI extends Application {
    private AlertBox alertBox = new AlertBox();
    private ConfirmBox confirmBox = new ConfirmBox();
    private TimeAPI timeAPI = TimeAPI.getInstance();

    private Stage window;

    // Variables for LoginMenu
    @FXML
    private Button closeButton;
    @FXML
    private TextField nameField;
    @FXML
    private Button loginButton;
    private Scene loginScene;
    private String name;

    // Variables for StudyRoom
    @FXML
    private Button logoutButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button roomButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label welcomeMessage;
    @FXML
    private Label currentTime;
    @FXML
    private Label label09;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    @FXML
    private Label label12;
    @FXML
    private Label label13;
    @FXML
    private Label label14;
    @FXML
    private Label label15;
    @FXML
    private Label label16;
    @FXML
    private Label label17;
    @FXML
    private Label label18;
    @FXML
    private Label label19;
    @FXML
    private Label label20;
    @FXML
    private ChoiceBox selectRoom;
    @FXML
    private ChoiceBox selectBookTime;
    @FXML
    private ChoiceBox selectCancelTime;

    private HashMap<Integer, Label> labelMap = new HashMap<>();
    private HashMap<String, Integer> timeMap = new HashMap<>();
    private StudyRoom room;
    private Student student;
    private Scene studyRoomScene;
    private boolean roomSet = false;

    // EFFECTS:  Initialize javafx Application
    public static void main(String[] args) {
        launch(args);
    }

    // MODIFIES: This
    // EFFECTS:  Set up main ui and set primaryStage to Login Menu
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        initializeLoginMenu();
        setLoginButtons();
        initializeTimeMap();
        initializeStudyRoom();
        setStudyRoomButtons();

        // Display Login Scene
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(loginScene);
        window.show();
    }

    private void initializeLoginMenu() {
        try {
            window.setTitle("Study Room System Login");
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/layout/LoginMenu.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            loginScene = new Scene(root);

        } catch (IOException e) {
            System.out.println("Error: IOException at LoginMenu");
        }
    }

    private void setLoginButtons() {
        loginButton.setOnAction(event -> {
            name = nameField.getText();
            if (testString(name)) {
                welcomeMessage.setText("Welcome, " + name);
                student = new Student(name);
                updateTime();
                window.setScene(studyRoomScene);
            }
        });
        closeButton.setOnAction(event -> {
            window.close();
        });
    }

    private boolean testString(String name) {
        Pattern pattern = Pattern.compile("[$&+,:;=\\\\?@#|/'`<>.^*()%!-]");

        if (name == null || name.isEmpty() || name.contains(" ") || pattern.matcher(name).find()) {
            alertBox.alert("Error", "Name invalid");
            return false;
        }
        return true;
    }

    private void updateTime() {
        String timeString = "Date: ";
        timeString += timeAPI.getYear() + "/" + timeAPI.getMonth() + "/" + timeAPI.getDay();
        currentTime.setText(timeString);
    }

    private void initializeStudyRoom() {
        try {
            window.setTitle("Study Room Booking System");
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/layout/StudyRoom.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            studyRoomScene = new Scene(root);
            initializeChoiceBoxes();
            initializeLabelMap();
        } catch (IOException e) {
            System.out.println("Error: IOException at StudyRoom");
        }
    }

    private void setStudyRoomButtons() {
        logoutButton.setOnAction(event -> {
            if (confirmBox.confirm("Logout", "Are you sure you want to logout?")) {
                window.setScene(loginScene);
            }
        });
        refreshButton.setOnAction(event -> updateLabels());
        setSelectRoomButton();
        setBookRoomButton();
        setCancelRoomButton();
    }

    private void setSelectRoomButton() {
        roomButton.setOnAction(event -> {
            if (selectRoom.getValue() == null) {
                alertBox.alert("Error", "Error: Room not specified");
            } else {
                try {
                    room = new StudyRoom((int)selectRoom.getValue());
                    roomSet = true;
                    updateLabels();
                } catch (IOException e) {
                    System.out.println("IO Exception in StudyRoom");
                }
            }
        });
    }

    private void setBookRoomButton() {
        bookButton.setOnAction(event -> {
            if (!roomSet) {
                alertBox.alert("Error", "Error: Room not specified");
            } else {
                if (selectBookTime.getValue() != null) {
                    try {
                        room.bookRoom(timeMap.get(selectBookTime.getValue()), student);
                        alertBox.alert("Book Room", "Room Booked");
                        room.saveData();
                        updateLabels();
                    } catch (TimeInvalidException | FileNotFoundException e) {
                        System.out.println("Exception in StudyRoom");
                    }
                }
            }
        });
    }

    private void setCancelRoomButton() {
        cancelButton.setOnAction(event -> {
            if (!roomSet) {
                alertBox.alert("Error", "Error: Room not specified");
            } else {
                if (selectCancelTime.getValue() != null) {
                    try {
                        room.cancelRoom(timeMap.get(selectCancelTime.getValue()), student);
                        alertBox.alert("Cancel Room", "Room Cancelled");
                        room.saveData();
                        updateLabels();
                    } catch (TimeInvalidException | FileNotFoundException e) {
                        System.out.println("Exception in StudyRoom");
                    }
                }
            }
        });
    }

    private void updateLabels() {
        if (!roomSet) {
            alertBox.alert("Error", "Error: Room not specified");
        } else {
            for (int i = 9; i < 21; i++) {
                try {
                    if (room.checkNotBooked(i)) {
                        labelMap.get(i).setText("Available");
                    } else {
                        labelMap.get(i).setText("Occupied");
                    }
                } catch (TimeInvalidException e) {
                    System.out.println("Time Invalid Exception in StudyRoom");
                }
            }
        }
    }

    private void initializeChoiceBoxes() {
        selectRoom.getItems().addAll(200, 201, 202, 203, 204, 205);
        selectBookTime.getItems().add("09:00");
        selectCancelTime.getItems().add("09:00");
        for (int i = 10; i < 21; i++) {
            selectBookTime.getItems().add(i + ":00");
            selectCancelTime.getItems().add(i + ":00");
        }
    }

    private void initializeTimeMap() {
        timeMap.put("09:00", 9);
        timeMap.put("10:00", 10);
        timeMap.put("11:00", 11);
        timeMap.put("12:00", 12);
        timeMap.put("13:00", 13);
        timeMap.put("14:00", 14);
        timeMap.put("15:00", 15);
        timeMap.put("16:00", 16);
        timeMap.put("17:00", 17);
        timeMap.put("18:00", 18);
        timeMap.put("19:00", 19);
        timeMap.put("20:00", 20);
    }

    private void initializeLabelMap() {
        labelMap.put(9, label09);
        labelMap.put(10, label10);
        labelMap.put(11, label11);
        labelMap.put(12, label12);
        labelMap.put(13, label13);
        labelMap.put(14, label14);
        labelMap.put(15, label15);
        labelMap.put(16, label16);
        labelMap.put(17, label17);
        labelMap.put(18, label18);
        labelMap.put(19, label19);
        labelMap.put(20, label20);
    }
}
