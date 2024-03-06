package controllers;


import home.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import models.Entreprise;
import services.EntrepriseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.*;

public class AfficherEntreprises {
    public TextField rechercheTF;
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

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterEntreprise.fxml"));
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
            // Vérifier si tous les champs sont remplis correctement
            if (verifierChamps()) {
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

                    // Afficher une confirmation de succès
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Entreprise modifiée avec succès");
                    alert.showAndWait();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la modification de l'entreprise : " + e.getMessage());
                    // Afficher une alerte en cas d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur est survenue lors de la modification de l'entreprise");
                    alert.showAndWait();
                }
            } else {
                // Afficher une alerte si les champs ne sont pas remplis correctement
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir tous les champs correctement");
                alert.showAndWait();
            }
        }
    }



    private boolean verifierChamps() {
        String nom = nomModTF.getText();
        String adresse = adresseModTF.getText();
        String contact = contactModTF.getText();

        // Expression régulière pour valider une adresse e-mail
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);

        // Vérification des champs et de l'adresse e-mail
        return !nom.isEmpty() && !adresse.isEmpty() && !contact.isEmpty() && isValidEmail(contact);
    }

    // Fonction pour vérifier si une adresse e-mail est valide en utilisant l'expression régulière
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

        // Écouteur d'événements pour détecter les changements de texte dans la zone de recherche
        rechercheTF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la méthode de recherche avec le nouveau texte entré dans la zone de recherche
            rechercherParNomAdresseContact(newValue);
        });
    }

    // Méthode de recherche par nom, adresse ou contact
    private void rechercherParNomAdresseContact(String valeur) {
        // Récupérer toutes les entreprises depuis la base de données
        EntrepriseService entrepriseService = new EntrepriseService();
        try {
            List<Entreprise> entreprises = entrepriseService.recuperer();
            ObservableList<Entreprise> observableList = FXCollections.observableList(entreprises);

            // Filtrer les entreprises en fonction du nom, adresse ou contact
            ObservableList<Entreprise> entreprisesFiltrees = FXCollections.observableArrayList();
            for (Entreprise entreprise : observableList) {
                if (entreprise.getNom().toLowerCase().contains(valeur.toLowerCase()) ||
                        entreprise.getAdresse().toLowerCase().contains(valeur.toLowerCase()) ||
                        entreprise.getContact().toLowerCase().contains(valeur.toLowerCase())) {
                    entreprisesFiltrees.add(entreprise);
                }
            }

            // Mettre à jour la TableView avec les entreprises filtrées
            tableView.setItems(entreprisesFiltrees);
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

    public void retourBT(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterEntreprise.fxml"));
        try {
            nomModTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

