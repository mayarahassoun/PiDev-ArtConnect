package controllers;

import home.Main;
import models.Reservations;
import services.ResServices;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;


public class AfficherReservations {

    @FXML
    private TextField IduserAfficher;

    @FXML
    private TextField commentaireSel;

    @FXML
    private TextField eventpath;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField ideventSel;

    @FXML
    private TextField idreserverSel;

    @FXML
    private TextField nbrpersoneSel;

    @FXML
    private TextField num_telSel;

    @FXML
    private TextField userIdSel;
    private List<Reservations> reservations ;
    @FXML
    void accepter(ActionEvent event) {

    }

    @FXML
    void afficherreservations(ActionEvent event) {

        ResServices reservationServices = new ResServices();
        reservations = reservationServices.getReservationAccepter();

        int rowIndex = 0;
        int columnIndex = 0;
        int columnCount = 2; // Nombre de colonnes dans la grille d'événements

        for (Reservations reservations1 : reservations) {
            // Création des labels avec des styles CSS
            Label userIdLabel = new Label("userID: " + reservations1.getUserId());
            userIdLabel.getStyleClass().add("event-label1");

            Label idevLabel = new Label("idEvenement: " + reservations1.getIdevent());
            idevLabel.getStyleClass().add("event-label1");

            Label nbrpLabel = new Label("nombres des perssones: " + reservations1.getNbrPerssone());
            nbrpLabel.getStyleClass().add("event-label3");

            Label descLabel = new Label("Email: " + reservations1.getEmail());
            descLabel.getStyleClass().add("event-label3");

            Label numLabel = new Label("num telephone : " + reservations1.getNum_tel());
            numLabel.getStyleClass().add("event-label1");

            Bloom bloomEffect = new Bloom();
            bloomEffect.setThreshold(10.5); // Ajustez la valeur du seuil selon vos préférences
            userIdLabel.setEffect(bloomEffect);
            idevLabel.setEffect(bloomEffect);
            nbrpLabel.setEffect(bloomEffect);
            descLabel.setEffect(bloomEffect);
            numLabel.setEffect(bloomEffect);

            Button selectioneButton = new Button("Selectioner");
            selectioneButton.getStyleClass().add("reserve-button");

            GridPane eventGridPane = new GridPane();

// Ajout des contraintes de colonne au GridPane

//            eventGridPane.add(imageView, 0, 0);
            eventGridPane.add(userIdLabel, 1, 0);
            eventGridPane.add(idevLabel, 0, 1);
            eventGridPane.add(nbrpLabel, 1, 1);
            eventGridPane.add(descLabel, 0, 2);
            eventGridPane.add(numLabel, 1, 2);
            eventGridPane.add(selectioneButton, 0, 3);
            eventGridPane.getStyleClass().add("event-grid-pane");

            // Ajouter un gestionnaire d'événements pour le bouton "Supprimer"
            selectioneButton.setOnAction(e -> handleSelectioneButton(reservations1));
//            modifierButton.setOnAction(e -> handleUpdateButton(event));


            // Ajouter le GridPane d'événement au GridPane principal
            gridPane.add(eventGridPane, columnIndex, rowIndex);

            // Incrémenter les indices de colonne et de ligne
            columnIndex++;
            if (columnIndex == columnCount) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }


    private void handleSelectioneButton(Reservations reservations1) {
        ResServices rs = new ResServices();
        int idreservation = reservations1.getIdreservation();
        int userID = reservations1.getUserId();
        int idevent = reservations1.getIdevent();
        int nbrP = reservations1.getNbrPerssone();
        String email = reservations1.getEmail();
        int num = reservations1.getNum_tel();

        idreserverSel.setText(String.valueOf(idreservation));;
        userIdSel.setText(String.valueOf(userID));
        ideventSel.setText(String.valueOf(idevent));
        nbrpersoneSel.setText(String.valueOf(nbrP));
        num_telSel.setText(String.valueOf(num));;
        commentaireSel.setText(email);
    }


    public void afficherEvent(ActionEvent actionEvent){

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/DisplayEvent.fxml"));
        try {
            IduserAfficher.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }






}
