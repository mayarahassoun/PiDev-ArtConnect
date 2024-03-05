package tn.esprit.gestionoffres.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.models.OffreDemploi;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.services.OffreDemploiService;
import tn.esprit.gestionoffres.test.HelloApplication;

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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/gestionoffres/AfficherOffreDemploi.fxml"));
        try {salaireTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* @FXML
    void ajouterOffreDemploi(ActionEvent event) throws SQLException {
        OffreDemploiService offreDemploiService = new OffreDemploiService();
        OffreDemploi offreDemploi = new OffreDemploi();
        offreDemploi.setTitre(titreTF.getText());
        offreDemploi.setDescription(descriptionTF.getText());
        offreDemploi.setSalaire((float) Double.parseDouble(salaireTF.getText()));
        // Conversion en double, veuillez gérer les exceptions si nécessaire
        offreDemploi.setStatus(statusCB.getValue()); // Récupération du statut sélectionné dans la ComboBox
        // Récupération de l'entreprise sélectionnée dans la ComboBox
        String nomEntreprise = entrepriseCB.getValue();
        Entreprise entreprise = entrepriseService.getEntrepriseByNom(nomEntreprise); // Supposons que vous avez une méthode pour récupérer l'entreprise par son nom depuis la base de données
        offreDemploi.setIdEntreprise(entreprise.getId());
        try {
            offreDemploiService.ajouter(offreDemploi);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } */
   @FXML
   void ajouterOffreDemploi(ActionEvent event) {
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
       }
   }
   }
