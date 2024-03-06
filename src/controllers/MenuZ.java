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


public class MenuZ {
    @FXML
    private TextField aff;
    @FXML
    void ConsulterEvents(ActionEvent event) {
        {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AfficherEvent.fxml"));
            try {
                aff.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }


    @FXML
    void consulterReservations(ActionEvent event) {
        {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/ConsulterReservation.fxml"));
            try {
                aff.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }


    @FXML
    void retourevent(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu2.fxml"));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }}
}
