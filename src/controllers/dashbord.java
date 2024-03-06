package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class dashbord {

    @FXML
    private ImageView aa;

    @FXML
    private TextField aff;

    @FXML
    private ImageView bb;

    @FXML
    void ConsulterEvents(ActionEvent event) {

    }

    @FXML
    void consulterReservations(ActionEvent event) {

    }

    @FXML
    void emploies(ActionEvent event) {

    }

    @FXML
    void evenements(ActionEvent event) {

    }

    @FXML
    void oeuvres(ActionEvent event) {

    }

    @FXML
    void partenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }

    @FXML
    void profile(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

}
