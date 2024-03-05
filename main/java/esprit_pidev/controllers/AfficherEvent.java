package esprit_pidev.controllers;

import esprit_pidev.entity.Event;
import esprit_pidev.entity.Reservations;
import esprit_pidev.services.EventServices;
import esprit_pidev.services.ResServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.MainFX;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AfficherEvent {

    @FXML
    private GridPane gridePaneTop;
    @FXML
    private TextField commentaire;

    @FXML
    private TextField eventpath;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField idUser;

    @FXML
    private TextField idmodifier;
    @FXML
    private TextField searchFieled;

    @FXML
    private ImageView imagemodifier;

    @FXML
    private TextField nbrp;

    @FXML
    private TextField num_tel;
    private List<Event> events;
    public void initialize() {
        EventServices eventServices = new EventServices();
        events = eventServices.getEvent();

        int rowIndex = 0;
        int columnIndex = 0;
        //int columnCount = 3; // Nombre de colonnes dans la grille d'événements

        for (Event event : events) {
            // Création des labels avec des styles CSS
            Label nameLabel = new Label( event.getName());
            nameLabel.getStyleClass().add("event-label1");

            Label typeLabel = new Label("Type: " + event.getType());
            typeLabel.getStyleClass().add("event-label3");

            Label dateLabel = new Label("le  " + event.getDate());
            dateLabel.getStyleClass().add("event-label3");


            Label descLabel = new Label("Description: " + event.getDesc());
            descLabel.getStyleClass().add("event-label3");

            Label localLabel = new Label("Localisation: " + event.getLocal());
            localLabel.getStyleClass().add("event-label3");


            // Créer un objet ImageView avec l'image chargée depuis le chemin spécifié
            ImageView imageView = new ImageView(new Image("file:" + event.getImagePath()));
            imageView.getStyleClass().add("event-image");

            // Définir une taille pour l'image si nécessaire
            imageView.setFitWidth(400); // Changer la largeur selon vos besoins
            imageView.setFitHeight(200); // Changer la hauteur selon vos besoins

            Bloom bloomEffect = new Bloom();
            bloomEffect.setThreshold(10.5); // Ajustez la valeur du seuil selon vos préférences
            nameLabel.setEffect(bloomEffect);
            typeLabel.setEffect(bloomEffect);
            dateLabel.setEffect(bloomEffect);
            descLabel.setEffect(bloomEffect);
            localLabel.setEffect(bloomEffect);

            // Créer un bouton pour supprimer et modifier
            Button reserverButton = new Button("Reserver");
            reserverButton.getStyleClass().add("reserve-button");


            // Ajouter chaque étiquette et image au GridPane d'événement
            GridPane eventGridPane = new GridPane();
            GridPane.setConstraints(imageView, 0, 0, 1, 5); // Ajout de la contrainte ici
            eventGridPane.getChildren().add(imageView); // Ajout de l'image au GridPane
            GridPane.setConstraints(nameLabel, 1, 1, 3, 5); // Ajout de la contrainte ici
            eventGridPane.getChildren().add(nameLabel); // Ajout de l'image au GridPane





// Ajout des labels dans la deuxième colonne
         //   eventGridPane.add(nameLabel, 1, 3);
            eventGridPane.add(typeLabel, 0, 6);
            eventGridPane.add(dateLabel, 0, 5);
            eventGridPane.add(descLabel, 1, 7);
            eventGridPane.add(localLabel, 0, 7);
            eventGridPane.add(reserverButton, 1, 5);
            reserverButton.setOnAction(e -> handlereserverButton( event));

            eventGridPane.getStyleClass().add("grid-container");


            // Ajouter le GridPane d'événement au GridPane principal
            gridPane.add(eventGridPane, columnIndex, rowIndex);
            //1gridPane.getStyleClass().add("grid-container");

            // Incrémenter les indices de colonne et de ligne
            columnIndex++;
//            if (columnIndex == columnCount) {
//                columnIndex = 0;
//                rowIndex++;
//            }
            // Afficher les événements les plus populaires
            List<Event> topEvents = eventServices.getTopThreeEventsByParticipants();
            displayTopEvents(topEvents);
        }
    }
    private void displayTopEvents(List<Event> topEvents) {
        int rowIndex = 0;
        int columnIndex = 0;
        for (Event event : topEvents) {
            // Création des labels avec des styles CSS
            Label nameLabel = new Label( event.getName());
            nameLabel.getStyleClass().add("event-label1");

            Label typeLabel = new Label("Type: " + event.getType());
            typeLabel.getStyleClass().add("event-label3");

            Label dateLabel = new Label("le  " + event.getDate());
            dateLabel.getStyleClass().add("event-label3");


            Label descLabel = new Label("Description: " + event.getDesc());
            descLabel.getStyleClass().add("event-label3");

            Label localLabel = new Label("Localisation: " + event.getLocal());
            localLabel.getStyleClass().add("event-label3");


            // Créer un objet ImageView avec l'image chargée depuis le chemin spécifié
            ImageView imageView = new ImageView(new Image("file:" + event.getImagePath()));
            imageView.getStyleClass().add("event-image");

            // Définir une taille pour l'image si nécessaire
            imageView.setFitWidth(150); // Changer la largeur selon vos besoins
            imageView.setFitHeight(150); // Changer la hauteur selon vos besoins

            // Créer un bouton pour reserver
            Button reserverButton = new Button("Reserver");
            reserverButton.getStyleClass().add("reserve-button");
            reserverButton.setOnAction(e -> handlereserverButton( event));


            // Ajouter chaque étiquette et image au GridPane d'événement
            GridPane topEventGridPane = new GridPane();
            GridPane.setConstraints(imageView, 0, 0);
            topEventGridPane.getChildren().add(imageView);
            topEventGridPane.add(nameLabel, 0, 1);
            topEventGridPane.add(typeLabel, 0, 2);
            topEventGridPane.add(localLabel, 0, 3);
            topEventGridPane.add(dateLabel, 0, 4);
            topEventGridPane.add(reserverButton, 0, 5);

            topEventGridPane.getStyleClass().add("grid-container");

            // Ajouter le GridPane d'événement au GridPane principal
            gridePaneTop.add(topEventGridPane, columnIndex, rowIndex);
            rowIndex++;

        }
    }

    private void handlereserverButton(Event event) {
        EventServices es = new EventServices();
        int id = event.getId();
        String pathimage = event.getImagePath();
        Image image = new Image("file:" + pathimage);
        imagemodifier.setFitWidth(200); // Changer la largeur selon vos besoins
        imagemodifier.setFitHeight(200);
        imagemodifier.setImage(image);
        idmodifier.setText(String.valueOf(id));
    }

    @FXML
    void reserver(ActionEvent reserve) {// Dans le contrôleur{
        int idevent;
        int userId;
        int nbP;
        int num;
        String email = commentaire.getText();
        String etat1 = "en attente";

        try {
            idevent = Integer.parseInt(idmodifier.getText());
            userId = Integer.parseInt(idUser.getText());
            nbP = Integer.parseInt(nbrp.getText());
            num = Integer.parseInt(num_tel.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir des nombres valides pour les champs ID, UserID, Nombre de personnes et Numéro de téléphone.");
            alert.showAndWait();
            return;
        }

        if (nbP <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le nombre de personnes doit être supérieur à zéro.");
            alert.showAndWait();
            return;
        }

        String numStr = Integer.toString(num);
        if (numStr.length() != 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le numéro de téléphone doit contenir exactement 8 chiffres.");
            alert.showAndWait();
            return;
        }

        // Création de la réservation
        Reservations ev = new Reservations(userId, idevent, nbP, email, num, etat1);
        ev.setEtat(etat1);
        ResServices evs = new ResServices();

        // Vérification si une réservation avec les mêmes données existe déjà
        if (evs.reservationExists(ev)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une réservation avec les mêmes données existe déjà.");
            alert.showAndWait();
            return;
        }

        // Vérification si la capacité maximale de l'événement est dépassée
        if (evs.isCapacityExceeded(ev)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Désolé, l'événement est complet.");
            alert.showAndWait();
            return;
        }

        // Ajout de la réservation
        try {
            evs.addReservation(ev);
            // Pas besoin d'afficher une alerte ici car elle est déjà affichée dans la méthode addReservation
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'envoi de la réservation : " + e.getMessage());
            alert.showAndWait();
        }
    }




    @FXML
    void afficherReservation(ActionEvent event)
        {

            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ConsulterReservation.fxml"));
            try {
                commentaire.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


    }
