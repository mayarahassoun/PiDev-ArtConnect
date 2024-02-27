package com.example.pi_salma.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    public void openModifInterface3(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pi_salma/AffichageReclamationBack.fxml"));
            Parent root = loader.load();
            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("KOOLart");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openModifInterface2(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pi_salma/AffichageReponse.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("KOOLart");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void openModifInterface5(ActionEvent event) {
        try {
            // = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pi_salma/Chart.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("KOOLart");

            stage.show();
            //.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
