package com.example.pi_salma.controllers;

import com.example.pi_salma.models.Reclamation;
import com.example.pi_salma.services.ServiceReclamation;
import com.example.pi_salma.test.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateReclamationControllers implements Initializable {
    @FXML
    private TextArea msg;

    @FXML
    private TextField type;


    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) type.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/pi_salma/User.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onKeyReleasedHandler2(KeyEvent event) {
        // Handle key released event for 'msg' TextArea
        if (msg.getText().isEmpty()) {
            msg.setStyle("-fx-border-color: red;");
        } else {
            msg.setStyle("-fx-border-color: green;");
        }
    }

    @FXML
    void onKeyReleasedHandler1(KeyEvent event) {
        // Handle key released event for 'type' TextField
        if (type.getText().isEmpty()) {
            type.setStyle("-fx-border-color: red;");
        } else {
            type.setStyle("-fx-border-color: green;");
        }
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {

        if (!msg.getText().isEmpty() && !type.getText().isEmpty()) {

    String message = msg.getText();
    String type1 = type.getText();
    String stat = "non Traitee";
    Date currentDate = new Date();

    // Create a SimpleDateFormat object with the desired format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the date to a string
    String dateString = dateFormat.format(currentDate);
    Reclamation r = new Reclamation(message, type1, stat,dateString);
    ServiceReclamation s = new ServiceReclamation();
            if (r.getMessage().equals(null) || r.getType().equals(null)) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Check Fields Again");
        alert.showAndWait();
    }
    else {
        s.Create(r);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Reclamation Added Successfully.");
        alert.showAndWait();

        Stage stage = (Stage) msg.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/pi_salma/User.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    }
else {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("ERROR");
    alert.setHeaderText(null);
    alert.setContentText("Check Fields Again");
    alert.showAndWait();

}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}





