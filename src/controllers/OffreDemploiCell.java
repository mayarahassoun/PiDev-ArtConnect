package controllers;


import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import models.Entreprise;
import services.EntrepriseService;
import models.OffreDemploi;
import services.OffreDemploiService;


public class OffreDemploiCell extends ListCell<OffreDemploi> {

    @Override
    protected void updateItem(OffreDemploi offre, boolean empty) {
        super.updateItem(offre, empty);

        if (empty || offre == null) {
            setGraphic(null);
        } else {
            // Créer un GridPane pour afficher les informations de l'offre d'emploi
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(5));
            gridPane.setHgap(10);
            gridPane.setVgap(5);



            // Ajouter le titre de l'offre
            Text titreText = new Text("Titre: " + offre.getTitre());
            gridPane.add(titreText, 0, 1);

            // Ajouter la description de l'offre
            Text descriptionText = new Text("Description: " + offre.getDescription());
            gridPane.add(descriptionText, 0, 2);

            // Ajouter le salaire de l'offre
            Text salaireText = new Text("Salaire: " + offre.getSalaire());
            gridPane.add(salaireText, 0, 3);

            // Ajouter le statut de l'offre
            Text statusText = new Text("Statut: " + offre.getStatus());
            gridPane.add(statusText, 0, 4);

            // Définir le GridPane comme contenu graphique de la cellule
            setGraphic(gridPane);
        }
    }
}