package controllers;


import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import models.Entreprise;
import services.EntrepriseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.*;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AfficherEntreprises.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void ajouterEntrepriseAction(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (verifierChamps()) {
            // Vérifier si le champ de contact se termine par "@gmail.tn"
            if (contactTF.getText().endsWith("@gmail.tn")) {
                EntrepriseService entrepriseService = new EntrepriseService();
                Entreprise entreprise = new Entreprise();
                entreprise.setNom(nomTF.getText());
                entreprise.setAdresse(adresseTF.getText());
                entreprise.setContact(contactTF.getText());

                try {
                    entrepriseService.ajouter(entreprise);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Entreprise ajoutée avec succès");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Erreur lors de l'ajout de l'entreprise : " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Afficher un message d'erreur si le champ de contact n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le contact de l'entreprise doit se terminer par '@gmail.tn'");
                alert.showAndWait();
            }
        } else {
            // Afficher un message d'erreur si tous les champs ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
    }


    // Méthode pour vérifier si tous les champs sont remplis
    private boolean verifierChamps() {
        return !nomTF.getText().isEmpty() &&
                !adresseTF.getText().isEmpty() &&
                !contactTF.getText().isEmpty();
    }

    public void gestionEntreprises(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterEntreprise.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gestionOffresDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterOffreDemploi.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void statEntreprise(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/statOffre.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menuOffreDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/dashbordOffre.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

