package controllers;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {
    @FXML
    private Button afficherreclamtion;

    @FXML
    private Button ajouterreclamtion;
    @FXML
    private Label homeLB;
    @FXML
    private TextField homeTF;

    @FXML
    void afficherreclamtion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AFFichageReclamtaionFront.fxml"));
            //FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/AFFichageReclamtaionFront.fxml"));
        try {
            homeTF.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void ajouterreclamtion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateReclamation.fxml"));
        //FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/CreateReclamation.fxml"));
        try {
            homeTF.getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void retourreclamation(ActionEvent event) {
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
