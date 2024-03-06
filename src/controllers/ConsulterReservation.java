package controllers;

import home.Main;
import models.Event;
import models.Reservations;
import services.EventServices;
import services.ResServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class ConsulterReservation {

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
    private TextField etatreserve;

    @FXML
    private TextField idreserverSel;

    @FXML
    private TextField nbrpersoneSel;

    @FXML
    private TextField num_telSel;

    @FXML
    private TextField userIdSel;
    private List<Reservations> reservations;

    @FXML
  void modifierreservation(ActionEvent event) {
        String idText = idreserverSel.getText();
        if (idText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à supprimer !");
            alert.showAndWait();
            return; // Sortir de la méthode car aucune réservation n'est sélectionnée
        }
        int id = Integer.parseInt(IduserAfficher.getText());
        String email = num_telSel.getText();
        int nbrp = Integer.parseInt(nbrpersoneSel.getText());
        int num_tel = Integer.parseInt(commentaireSel.getText());
        int id_reservation = Integer.parseInt(idreserverSel.getText());
        int id_event = Integer.parseInt(ideventSel.getText());
        //  String etat = etatreserve.getText();
        Reservations ev = new Reservations(id_reservation, id, id_event, nbrp,email , num_tel);
        ev.setIdreservation(id_reservation);
        ev.setUserId(id);
        ev.setIdevent(id_event);
        ev.setNbrPerssone(nbrp);
        ev.setEmail(email);
        ev.setNum_tel(num_tel);

        ResServices evs = new ResServices();
        try {
            evs.updateReservation(ev);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Reservations modifier avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors la modification de la reservations : " + e.getMessage());
            alert.showAndWait();
        }

        evs.updateReservation(ev);
        refreshGrid();



    }




    @FXML
    void afficherreservations(ActionEvent event) {
        int iduser = Integer.parseInt(IduserAfficher.getText());


        ResServices reservationServices = new ResServices();
        reservations = reservationServices.afficherReservations(iduser);

        int rowIndex = 0;
        int columnIndex = 0;
        int columnCount = 2; // Nombre de colonnes dans la grille d'événements

        for (Reservations reservations1 : reservations) {
            Label userIdLabel = new Label("userID: " + reservations1.getUserId());
            userIdLabel.getStyleClass().add("event-label3");

            Label idevLabel = new Label("idEvenement: " + reservations1.getIdevent());
            idevLabel.getStyleClass().add("event-label3");

            Label nbrpLabel = new Label("nombres des perssones: " + reservations1.getNbrPerssone());
            nbrpLabel.getStyleClass().add("event-label3");

            Label descLabel = new Label("Email: " + reservations1.getEmail());
            descLabel.getStyleClass().add("event-label3");

            Label numLabel = new Label("num telephone : " + reservations1.getNum_tel());
            numLabel.getStyleClass().add("event-label3");

            Label etatLabel = new Label("Etat: " + reservations1.getEtat());
            descLabel.getStyleClass().add("event-label1");

            Bloom bloomEffect = new Bloom();
            bloomEffect.setThreshold(10.5);
            userIdLabel.setEffect(bloomEffect);
            idevLabel.setEffect(bloomEffect);
            nbrpLabel.setEffect(bloomEffect);
            descLabel.setEffect(bloomEffect);
            numLabel.setEffect(bloomEffect);
            Button selectioneButton = new Button("Selectioner");
            selectioneButton.getStyleClass().add("reserve-button");


            GridPane eventGridPane = new GridPane();
            eventGridPane.add(etatLabel, 0, 0);
            eventGridPane.add(userIdLabel, 1, 0);
            eventGridPane.add(idevLabel, 0, 1);
            eventGridPane.add(nbrpLabel, 1, 1);
            eventGridPane.add(descLabel, 0, 2);
            eventGridPane.add(numLabel, 1, 2);
            eventGridPane.add(selectioneButton, 0, 3);
            eventGridPane.getStyleClass().add("event-grid-pane");
            selectioneButton.setOnAction(e -> handleSelectioneButton(reservations1));
            gridPane.add(eventGridPane, columnIndex, rowIndex);

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
        String Email = reservations1.getEmail();
        int num = reservations1.getNum_tel();


        idreserverSel.setText(String.valueOf(idreservation));
        ideventSel.setText(String.valueOf(idevent));
        nbrpersoneSel.setText(String.valueOf(nbrP));
        num_telSel.setText(Email);
        commentaireSel.setText(String.valueOf(num));
    }


    public void afficherEvent(ActionEvent actionEvent){

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/MenuZ.fxml"));
            try {
                IduserAfficher.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    @FXML
    void suprimerReservation(ActionEvent event) {
        String idText = idreserverSel.getText();

        // Vérifier si l'ID est vide
        if (idText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à supprimer !");
            alert.showAndWait();
            return; // Sortir de la méthode car aucune réservation n'est sélectionnée
        }

        int id = Integer.parseInt(idText);

        // Demander confirmation avant de supprimer la réservation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réservation ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ResServices rs = new ResServices();

            // Supprimer la réservation
            rs.deleteReservation(id);

            // Afficher un message de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Suppression réussie");
            confirmationAlert.setContentText("La réservation a été supprimée avec succès.");
            confirmationAlert.showAndWait();
            refreshGrid();
        }
        }
    private void refreshGrid() {
        gridPane.getChildren().clear();
         afficherreservations(null);
    }
    }





