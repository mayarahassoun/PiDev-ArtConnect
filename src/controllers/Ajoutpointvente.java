package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import models.PointVente;
import org.controlsfx.control.Notifications;
import services.PointVenteServices;
import services.partenaireServices;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Ajoutpointvente {

    @FXML
    private TextArea point_desc;


    @FXML
    private TextField point_localisation;

    @FXML
    private TextField point_nom;

    @FXML
    private TextField point_num;

    @FXML
    void affichagePointvente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/afficherpointvente.fxml"));
            Parent root = loader.load();
            point_nom.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    PointVenteServices pvs = new PointVenteServices();

    @FXML
    void ajouterPointvente(ActionEvent event) throws SQLException {
        if (point_nom.getText().isEmpty() || point_localisation.getText().length() < 4
               ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
            alert.showAndWait();
        } else {
            PointVente pv = new PointVente();
            pv.setName(point_nom.getText());
            pv.setLocalisation(point_localisation.getText());
            pv.setDescription(point_desc.getText());
            pv.setNumero(Integer.parseInt(point_num.getText()));


            if (pvs.addPointVente(pv)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("PointVente a été ajouté");
                alert.showAndWait();
                Notifications.create().title("Success").text("pointvente est ajouté avec succes").showInformation();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("PointVente a été ajouté");
                alert.showAndWait();
                Notifications.create().title("Success").text("point vente est ajouté avec succes").showInformation();
            }

        }

    }




    }

