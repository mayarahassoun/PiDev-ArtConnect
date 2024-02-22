package tn.esprit.gestionoffres.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherEntreprises {
    @FXML
    private TableColumn<Entreprise,String> adresseCol;

    @FXML
    private TableColumn<Entreprise, String> contactCol;

    @FXML
    private TableColumn<Entreprise, String> nomCol;

    @FXML
    private TableView<Entreprise> tableView;
    @FXML
    private TextField nomModTF;
    @FXML
    private TextField adresseModTF;

    @FXML
    private TextField contactModTF;


    @FXML
    void ajouterEntreprise(ActionEvent event) {
        //navigation de scene affichage au sene ajouter
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/gestionoffres/AjouterEntreprise.fxml"));
        try {
            nomModTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ligneSelected(MouseEvent event) {

    }




    @FXML
    void modifierEntreprise(ActionEvent event) {
        // Récupérer l'entreprise sélectionnée
        Entreprise selectedEntreprise = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntreprise != null) {
            // Mettre à jour les données de l'entreprise avec celles des TextField
            selectedEntreprise.setNom(nomModTF.getText());
            selectedEntreprise.setAdresse(adresseModTF.getText());
            selectedEntreprise.setContact(contactModTF.getText());

            // Mettre à jour l'entreprise dans la base de données
            EntrepriseService entrepriseService = new EntrepriseService();
            try {
                entrepriseService.modifier(selectedEntreprise);

                // Mettre à jour la TableView
                tableView.refresh();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la modification de l'entreprise : " + e.getMessage());
            }
        }

    }

    @FXML
    void supprimerEntreprise(ActionEvent event) {
        // Récupérer l'entreprise sélectionnée
        Entreprise selectedEntreprise = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntreprise != null) {
            // Supprimer l'entreprise de la base de données
            EntrepriseService entrepriseService = new EntrepriseService();
            try {
                entrepriseService.supprimer(selectedEntreprise.getId());

                // Mettre à jour la TableView en retirant l'entreprise supprimée de la liste observable
                tableView.getItems().remove(selectedEntreprise);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la suppression de l'entreprise : " + e.getMessage());
            }
        }


    }

    @FXML
    void initialize() {
        EntrepriseService entrepriseService= new EntrepriseService();
        try {
            List<Entreprise> entreprises = entrepriseService.recuperer();
            ObservableList<Entreprise> observableList = FXCollections.observableList(entreprises);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void handleTableRowClicked(MouseEvent event) {
        if (event != null && event.getClickCount() == 1) { // Vérifiez si c'est un simple clic de souris
            Entreprise selectedEntreprise = tableView.getSelectionModel().getSelectedItem();
            if (selectedEntreprise != null) {
                // Récupérer les valeurs des colonnes et les mettre dans les TextField correspondants
                nomModTF.setText(selectedEntreprise.getNom());
                adresseModTF.setText(selectedEntreprise.getAdresse());
                contactModTF.setText(selectedEntreprise.getContact());
            }
        }
    }

}
