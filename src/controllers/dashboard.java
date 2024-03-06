//package esprit_pidev.controllers;
package controllers;


import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboard {
    @FXML
    private TextField aff;
    @FXML
    private TextField textTF;

    @FXML
    void afficherreclamtion(ActionEvent event) {



        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/AffichageReclamationBack.fxml"));
        try {
            textTF.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherreponse(ActionEvent event) {



        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/AffichageReponse.fxml"));
        try {
            textTF.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void statistique(ActionEvent event) {




        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/Chart.fxml"));
        try {
            textTF.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void AjouterEvent(ActionEvent event) {

    }
    @FXML
    void retour_reclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu1.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }
}
