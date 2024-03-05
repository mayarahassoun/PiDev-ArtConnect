package tn.esprit.gestionoffres.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.models.OffreDemploi;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.services.OffreDemploiService;
import tn.esprit.gestionoffres.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherOffreDemploi {

    @FXML
    private TableColumn<OffreDemploi, String> titreCol;

    @FXML
    private TableColumn<OffreDemploi, String> descriptionCol;

    @FXML
    private TableColumn<OffreDemploi, Float> salaireCol;

    @FXML
    private TableColumn<OffreDemploi, String> statusCol;

    @FXML
    private TableColumn<OffreDemploi, String> entrepriseCol;

    @FXML
    private TableView<OffreDemploi> tableView;

    @FXML
    private TextField titreTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField salaireTF;

    @FXML
    private ComboBox<String> statusCB;

    @FXML
    private ComboBox<String> entrepriseCB;

    private final OffreDemploiService offreDemploiService = new OffreDemploiService();
    private final EntrepriseService entrepriseService = new EntrepriseService();

    @FXML
    void ajouterOffreDemploi(ActionEvent event) {
        // Navigation vers la scène d'ajout d'offre d'emploi
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/gestionoffres/AjouterOffreDemploi.fxml"));
        try {
            titreTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierOffreDemploi(ActionEvent event) {
        OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            // Mettre à jour les données de l'offre avec celles des champs de texte
            selectedOffre.setTitre(titreTF.getText());
            selectedOffre.setDescription(descriptionTF.getText());
            selectedOffre.setSalaire(Float.parseFloat(salaireTF.getText()));
            selectedOffre.setStatus(statusCB.getValue());

            // Récupérer l'entreprise sélectionnée depuis la ComboBox
            String nomEntreprise = entrepriseCB.getValue();
            if (nomEntreprise != null && !nomEntreprise.isEmpty()) {
                try {
                    // Récupérer l'entreprise par son nom depuis la base de données
                    Entreprise entreprise = entrepriseService.getEntrepriseByNom(nomEntreprise);
                    if (entreprise != null) {
                        // Mettre à jour l'identifiant de l'entreprise dans l'offre d'emploi
                        selectedOffre.setIdEntreprise(entreprise.getId());
                    }
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la récupération de l'entreprise : " + e.getMessage());
                }
            }

            // Mettre à jour l'offre dans la base de données
            try {
                offreDemploiService.modifier(selectedOffre);
                tableView.refresh(); // Rafraîchir la TableView
            } catch (SQLException e) {
                System.err.println("Erreur lors de la modification de l'offre d'emploi : " + e.getMessage());
            }
        }
    }

    @FXML
    void supprimerOffreDemploi(ActionEvent event) {
        OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            // Supprimer l'offre de la base de données
            try {
                offreDemploiService.supprimer(selectedOffre.getId());
                tableView.getItems().remove(selectedOffre); // Retirer l'offre de la TableView
            } catch (SQLException e) {
                System.err.println("Erreur lors de la suppression de l'offre d'emploi : " + e.getMessage());
            }
        }
    }

    @FXML
    void initialize() {
        try {
            // Configuration des colonnes de la TableView
            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaire"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            entrepriseCol.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));

            // Récupérer les noms des entreprises et les ajouter à la ComboBox
            List<String> nomsEntreprises = entrepriseService.recupererNomsEntreprises();
            ObservableList<String> observableNomsEntreprises = FXCollections.observableList(nomsEntreprises);
            entrepriseCB.setItems(observableNomsEntreprises);

            // Initialisation de la TableView
            List<OffreDemploi> offres = offreDemploiService.recuperer();
            ObservableList<OffreDemploi> observableList = FXCollections.observableList(offres);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // Peupler la ComboBox du statut de l'offre d'emploi
        ObservableList<String> statutsList = FXCollections.observableArrayList(
                "En attente", "Publiée", "Clôturée");
        statusCB.setItems(statutsList);
    }

    @FXML
    void handleTableRowClicked(MouseEvent event) throws SQLException {
        OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            titreTF.setText(selectedOffre.getTitre());
            descriptionTF.setText(selectedOffre.getDescription());
            salaireTF.setText(String.valueOf(selectedOffre.getSalaire()));
            statusCB.setValue(selectedOffre.getStatus());
            try {
                entrepriseCB.setValue(selectedOffre.getNomEntreprise());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
