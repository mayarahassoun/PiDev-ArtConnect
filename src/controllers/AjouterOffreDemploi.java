package controllers;
import controllers.AfficherOffreDemploi;
import home.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import models.Entreprise;
import services.EntrepriseService;
import models.OffreDemploi;
import services.OffreDemploiService;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AjouterOffreDemploi {
    private EntrepriseService entrepriseService;

    @FXML
    private TextField descriptionTF;

    @FXML
    private ComboBox<String> entrepriseCB;

    @FXML
    private TextField salaireTF;

    @FXML
    private ComboBox<String> statusCB;

    @FXML
    private TextField titreTF;

    public AjouterOffreDemploi() {
        entrepriseService = new EntrepriseService();
    }

    @FXML
    public void initialize() {
        // Peupler la ComboBox des entreprises avec les noms des entreprises existantes
        try {
            List<Entreprise> entreprises = entrepriseService.recuperer();
            List<String> nomsEntreprises = new ArrayList<>();
            for (Entreprise entreprise : entreprises) {
                nomsEntreprises.add(entreprise.getNom());
            }
            ObservableList<String> entreprisesList = FXCollections.observableArrayList(nomsEntreprises);
            entrepriseCB.setItems(entreprisesList);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur de récupération des entreprises depuis la base de données
        }

        // Peupler la ComboBox du statut de l'offre d'emploi
        ObservableList<String> statutsList = FXCollections.observableArrayList(
                "En attente", "Publiée", "Clôturée");
        statusCB.setItems(statutsList);
    }

    @FXML
    void afficherOffreDemploi(ActionEvent event) {
        //navigation de scene ajout au sene affichage
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AfficherOffreDemploi.fxml"));
        try {
            titreTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterOffreDemploi(ActionEvent event) {
        // Vérifier que tous les champs sont remplis avant d'ajouter l'offre d'emploi
        if (verifierChamps()) {
            try {
                OffreDemploiService offreDemploiService = new OffreDemploiService();
                OffreDemploi offreDemploi = new OffreDemploi();
                offreDemploi.setTitre(titreTF.getText());
                offreDemploi.setDescription(descriptionTF.getText());
                offreDemploi.setSalaire((float) Double.parseDouble(salaireTF.getText()));
                offreDemploi.setStatus(statusCB.getValue()); // Récupération du statut sélectionné dans la ComboBox
                String nomEntreprise = entrepriseCB.getValue();
                Entreprise entreprise = entrepriseService.getEntrepriseByNom(nomEntreprise); // Supposons que vous avez une méthode pour récupérer l'entreprise par son nom depuis la base de données
                offreDemploi.setIdEntreprise(entreprise.getId());

                offreDemploiService.ajouter(offreDemploi);

                // Affichage de l'alerte en cas de succès
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Offre d'emploi ajoutée avec succès");
                alert.showAndWait();
            } catch (SQLException e) {
                // Affichage de l'alerte en cas d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Une erreur est survenue lors de l'ajout de l'offre d'emploi");
                alert.showAndWait();
                // Affichage de la pile d'exception dans la console
                e.printStackTrace();
            } catch (NumberFormatException e) {
                // Affichage de l'alerte si la conversion du salaire échoue
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le format du salaire est incorrect");
                alert.showAndWait();
            }
        } else {
            // Afficher un message d'erreur à l'utilisateur indiquant qu'un champ obligatoire est manquant
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
    }

    // Méthode pour vérifier que tous les champs sont remplis
    private boolean verifierChamps() {
        return !titreTF.getText().isEmpty() &&
                !descriptionTF.getText().isEmpty() &&
                !salaireTF.getText().isEmpty() &&
                statusCB.getValue() != null &&
                entrepriseCB.getValue() != null;
    }

    /*public void gestionEntreprises(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/AjouterEntreprise.fxml"));
        try {
            salaireTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gestionOffresDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/AjouterOffreDemploi.fxml"));
        try {
            salaireTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void statEntreprise(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/statOffre.fxml"));
        try {
            salaireTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menuOffreDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/dashbordOffre.fxml"));
        try {
            salaireTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

