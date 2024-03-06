package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.OffreDemploi;
import services.OffreDemploiService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PartieFront {
    @FXML
    private TextField rechercheTF;

    @FXML
    private ComboBox<String> trierparCB;

    @FXML
    private ListView<OffreDemploi> offreListView;

    private OffreDemploiService offreService;

    public PartieFront() {
        offreService = new OffreDemploiService();
    }

    @FXML
    public void initialize() {
        // Peupler la ComboBox avec les options de tri
        trierparCB.getItems().addAll("Salaire", "Titre");
        try {
            List<OffreDemploi> offres = offreService.recuperer();
            offreListView.setCellFactory(listView -> new OffreDemploiCell());
            offreListView.getItems().addAll(offres);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rechercheTF.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherParTitre(newValue.trim());
        });
    }
    public void rechercherParTitre(String titreRecherche) {
        // Effacer la liste des offres affichées
        offreListView.getItems().clear();

        // Récupérer toutes les offres depuis le service
        try {
            List<OffreDemploi> offres = offreService.recuperer();

            // Filtrer les offres par titre
            List<OffreDemploi> offresFiltrees = offres.stream()
                    .filter(offre -> offre.getTitre().toLowerCase().contains(titreRecherche.toLowerCase()))
                    .toList();

            // Afficher les offres filtrées dans la liste
            offreListView.getItems().addAll(offresFiltrees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void trier(ActionEvent actionEvent) {
        String critereTri = trierparCB.getValue();

        if (critereTri.equals("Salaire")) {
            trierParSalaireCroissant();
        } else if (critereTri.equals("Titre")) {
            trierParTitre();
        }
    }

    private void trierParSalaireCroissant() {
        offreListView.getItems().sort(Comparator.comparingDouble(OffreDemploi::getSalaire));
    }

    private void trierParTitre() {
        offreListView.getItems().sort(Comparator.comparing(OffreDemploi::getTitre));
    }


}