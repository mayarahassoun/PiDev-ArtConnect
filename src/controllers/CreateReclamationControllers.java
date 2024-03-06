package controllers;

import home.Main;
import models.Reclamation;
import services.ServiceReclamation;
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

public class CreateReclamationControllers  {
    @FXML
    private TextArea msg;

    @FXML
    private TextField type;





    @FXML
    private TextArea msg1;



    @FXML
    private TextField type1;


    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) type1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/MenuSalma.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void onKeyReleasedHandler2(KeyEvent event) {
        // Handle key released event for 'msg1' TextArea
        if (msg1.getText().isEmpty()) {
            msg1.setStyle("-fx-border-color: red;");
        } else {
            msg1.setStyle("-fx-border-color: green;");
        }
    }

    @FXML
    void onKeyReleasedHandler1(KeyEvent event) {
        // Handle key released event for 'type' TextField
        if (type1.getText().isEmpty()) {
            type1.setStyle("-fx-border-color: red;");
        } else {
            type1.setStyle("-fx-border-color: green;");
        }
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {

        if (!msg1.getText().isEmpty() && !type1.getText().isEmpty()) {

    String message = msg1.getText();
    String type11 = type1.getText();
    String stat = "non Traitee";
    Date currentDate = new Date();

    // Create a SimpleDateFormat object with the desired format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Format the date to a string
    String dateString = dateFormat.format(currentDate);
    Reclamation r = new Reclamation(message, type11, stat,dateString);
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

        Stage stage = (Stage) msg1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/MenuSalma.fxml"));
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


}





