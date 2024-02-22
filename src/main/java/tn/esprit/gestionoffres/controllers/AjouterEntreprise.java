package tn.esprit.gestionoffres.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterEntreprise {
    @FXML
    private TextField adresseTF;

    @FXML
    private TextField contactTF;

    @FXML
    private TextField nomTF;

    @FXML
    void afficherEntrepriseAction(ActionEvent event) {
        //navigation de scene ajout au sene affichage
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/gestionoffres/AfficherEntreprises.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterEntrepriseAction(ActionEvent event) {
        EntrepriseService entrepriseService = new EntrepriseService();
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(nomTF.getText());
        entreprise.setAdresse(adresseTF.getText());
        entreprise.setContact(contactTF.getText());

        try {
            entrepriseService.ajouter(entreprise);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("succés");
            alert.setContentText("entreprise ajoutée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
}
