package esprit_pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.example.MainFX;

import java.io.IOException;

public class dashbord {
    @FXML
    private TextField aff;

    @FXML
    void ConsulterEvents(ActionEvent event)
        {

            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/DisplayEvent.fxml"));
            try {
                aff.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }



    @FXML
    void consulterReservations(ActionEvent event)
        {

            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/DisplayReservation.fxml"));
            try {
                aff.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




    }
}
