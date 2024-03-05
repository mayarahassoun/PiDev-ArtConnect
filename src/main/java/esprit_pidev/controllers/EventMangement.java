package esprit_pidev.controllers;

import esprit_pidev.services.EventServices;
import esprit_pidev.entity.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.example.MainFX;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EventMangement {
    @FXML
    private Button blisteEV;
    @FXML
    private DatePicker dateEvent;

    @FXML
    private TextField descEvent;

    @FXML
    private TextField loaclisation;

    @FXML
    private TextField nomEvent;

    @FXML
    private TextField typeEvent;
    private String filePath;
    @FXML
    private ImageView imageTF;

    @FXML
    void handleUploadButtonAction(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null && isImageFile(selectedFile)) {
            // Store the file path
            filePath = selectedFile.getAbsolutePath();
            System.out.println("File path stored: " + filePath);

            // Set the image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }
    }

    // Method to check if the selected file is an image file
    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to retrieve the stored file path
    public String getFilePath() {
        return filePath;
    }

    @FXML


    void ajouterEvent(ActionEvent event) {
        // Vérifier la longueur de la description
        if (descEvent.getText().length() > 15) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La description ne peut pas dépasser 100 caractères.");
            alert.showAndWait();
            return; // Arrêter le traitement si la description est trop longue
        }

        // Vérifier que la date ajoutée est après un jour de la date actuelle
        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateAjoutee = dateEvent.getValue();
        if (dateAjoutee.isBefore(dateActuelle.plus(1, ChronoUnit.DAYS))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date ajoutée doit être après un jour de la date actuelle.");
            alert.showAndWait();
            return; // Arrêter le traitement si la date ajoutée est invalide
        }

        // Si les conditions de validation sont satisfaites, procéder à l'ajout de l'événement
        EventServices eventServices = new EventServices();
        Event event1 = new Event();
        event1.setDate(Date.valueOf(dateAjoutee));
        event1.setDesc(descEvent.getText());
        event1.setName(nomEvent.getText());
        event1.setType(typeEvent.getText());
        event1.setImagePath(filePath);
        event1.setLocal(loaclisation.getText());
        try {
            eventServices.addEvent(event1);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement ajouté avec succès.");
            Notifications.create().title("Success").text("L'Evenement est ajouté avec succes").showInformation();
            alert.showAndWait();
            Notifications.create().title("Done").text("ajout avec succés").showConfirm();
            displayev(null);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            alert.showAndWait();
        }
    }@FXML
    void displayev(MouseEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/DisplayEvent.fxml"));
        try {
            descEvent.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}

