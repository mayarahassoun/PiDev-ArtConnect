package controllers;



import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class dashbordOffre {




    @FXML
    private TextField aff;
    public void gestionOffresDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterOffreDemploi.fxml"));
        try {
            aff.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gestionEntreprises(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterEntreprise.fxml"));
        try {
            aff.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void statEntreprise(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/statOffre.fxml"));
        try {
            aff.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void retour_offre(ActionEvent event) {
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

