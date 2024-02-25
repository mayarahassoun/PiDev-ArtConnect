package tn.esprit.gestionoffres.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.models.OffreDemploi;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.services.OffreDemploiService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* public class AjouterOffreDemploi {
    private EntrepriseService entrepriseService;


    @FXML
    private TextField descriptionTF;

    @FXML
    private ComboBox<?> entrepriseCB;

    @FXML
    private TextField salaireTF;

    @FXML
    private ComboBox<?> statusCB;

    @FXML
    private TextField titreTF;

    @FXML
    void afficherOffreDemploi(ActionEvent event) {

    }

    @FXML
    void ajouterOffreDemploi(ActionEvent event) {
        OffreDemploiService offreDemploiService = new OffreDemploiService();
        OffreDemploi offreDemploi= new OffreDemploi();
        offreDemploi.setTitre(titreTF.getText());
        offreDemploi.setDescription(descriptionTF.getText());
        offreDemploiService.ajouter();

    }
}*/
public class AjouterOffreDemploi {
    private EntrepriseService entrepriseService;

    @FXML
    private TextField descriptionTF;

    @FXML
    private ComboBox<String> entrepriseCB; // Assurez-vous de spécifier le type approprié pour la ComboBox

    @FXML
    private TextField salaireTF;

    @FXML
    private ComboBox<String> statusCB; // Assurez-vous de spécifier le type approprié pour la ComboBox

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
        // Code pour afficher une offre d'emploi, si nécessaire
    }

    @FXML
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
    }
}
