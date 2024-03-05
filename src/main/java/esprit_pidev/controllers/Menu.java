package esprit_pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.example.MainFX;

import java.io.IOException;


public class Menu {
    @FXML
    private TextField aff;
    @FXML
    void ConsulterEvents(ActionEvent event) {
        {

            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/AfficherEvent.fxml"));
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

            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ConsulterReservation.fxml"));
            try {
                aff.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }

}
