package tn.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tn.models.Partenaire;

import java.awt.*;

public class Home {

    @FXML
    private TableView<Partenaire> table;

    @FXML
    void gotopartenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpartenaire.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }


    @FXML
    void gotopointvente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpointvente.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }

}