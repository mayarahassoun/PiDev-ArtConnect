package esprit_pidev.controllers;


import com.twilio.Twilio;
import esprit_pidev.entity.Reservations;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.MainFX;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;

import java.util.List;

public class DisplayReservation {
    private List<Reservations> reservations ;
    @FXML
    private TextField commentaireSel;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button GoStat;

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
    private static final String API_KEY = "AC61e284f59a88779c29bfbe6ba4474d2b";


    @FXML
    public void initialize() {
        ResServices reservationServices = new ResServices();
        reservations = reservationServices.getReservation();

        int rowIndex = 0;
        int columnIndex = 0;
        int columnCount = 2; // Nombre de colonnes dans la grille d'événements

        for (Reservations reservations1 : reservations) {

            // Création des labels avec des styles CSS
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

            eventGridPane.add(userIdLabel, 0, 0);
            eventGridPane.add(idevLabel, 0, 1);
            eventGridPane.add(nbrpLabel, 0, 2);
            eventGridPane.add(numLabel, 0, 3);
            eventGridPane.add(descLabel, 0, 4);
            eventGridPane.add(selectioneButton, 0, 5);
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

    private void handleSelectioneButton(Reservations reservations) {
        ResServices rs = new ResServices();
        int idreservation = reservations.getIdreservation();
        int userID = reservations.getUserId();
       int idevent = reservations.getIdevent();
        int nbrP = reservations.getNbrPerssone();
        String Email = reservations.getEmail();
        int num = reservations.getNum_tel();

        idreserverSel.setText(String.valueOf(idreservation));;
        userIdSel.setText(String.valueOf(userID));
        ideventSel.setText(String.valueOf(idevent));
        nbrpersoneSel.setText(String.valueOf(nbrP));
        num_telSel.setText(Email);
        commentaireSel.setText(String.valueOf(num));
    }
    @FXML
    void afficherReservationAccepter(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/ReservationAccepter.fxml"));
        try {
            ideventSel.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void accepter(ActionEvent reservations) {
        int id = Integer.parseInt(idreserverSel.getText());
        String email = num_telSel.getText();
        String etat = "accepter";
        Reservations R = new Reservations(id,etat);
        R.setIdreservation(id);
        R.setEtat(etat);
        ResServices evs = new ResServices();

        try {
            evs.modifierEtatReservation(id,etat);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Reservations accepte avec succès.");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors la modification de l evenment : " + e.getMessage());
            alert.showAndWait();
        }
        evs.modifierEtatReservation(id,etat);
        String to = "houcem.aleya@gmail.com"; // Adresse e-mail du destinataire
        String subject = "Confirmation de réservation";
        String body = "Votre réservation a été confirmée avec succès.";

        R.sendEmail(email, subject, body);

        // Afficher une confirmation à l'utilisateur
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("Reservation ajoutée avec succès et email de confirmation envoyé.");
        alert1.showAndWait();

        refreshGrid();



    }
    @FXML
    void refuserReservation(ActionEvent event) {
        int id = Integer.parseInt(idreserverSel.getText());
        String etat = "refuser";
        Reservations R = new Reservations(id,etat);
        R.setIdreservation(id);
        R.setEtat(etat);
        ResServices evs = new ResServices();

        try {
            evs.modifierEtatReservation(id,etat);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Reservations Refuser avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors la refusations : " + e.getMessage());
            alert.showAndWait();
        }
        evs.modifierEtatReservation(id,etat);
        // Find your Account Sid and Token at twilio.com/console
        final String ACCOUNT_SID = "AC61e284f59a88779c29bfbe6ba4474d2b";
        final String AUTH_TOKEN = "0079068e0f7662637a9c9710743feada";


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21628813023"),
                        new com.twilio.type.PhoneNumber("+18177834110"),
                        "Une  Reservations est Refuser.")
                .create();

        System.out.println(message.getSid());
        refreshGrid();

    }
    private void refreshGrid() {
        gridPane.getChildren().clear();
        initialize();
    }

    @FXML
    void afficherlevenment(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/DisplayEvent.fxml"));
        try {
            ideventSel.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void afficherReservationRefuser(ActionEvent event)
    {

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/AfficherReservations.fxml"));
        try {
            ideventSel.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void goStat(ActionEvent event) {try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) GoStat.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException e) {
        e.printStackTrace();
    }


    }
    @FXML
    void menu(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/dashbord.fxml"));
        try {
            ideventSel.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }





}
