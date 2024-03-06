package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import models.Partenaire;
import org.controlsfx.control.Notifications;
import services.partenaireServices;

import java.sql.SQLException;

public class Ajoutpartenaire {

    @FXML
    private DatePicker part_debut;

    @FXML
    private TextArea part_desc;

    @FXML
    private DatePicker part_fin;

    @FXML
    private TextField part_nom;

    @FXML
    private TextField part_num;

    @FXML
    private TextField part_type;

    @FXML
    void affichagePartenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/afficherpartenaire.fxml"));
            Parent root = loader.load();
            part_nom.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    partenaireServices ps = new partenaireServices();
    @FXML
    private void ajouterPartenaire(ActionEvent event) throws SQLException {
        if (part_nom.getText().isEmpty() || part_type.getText().isEmpty() || part_num.getText().length() < 8 || part_num.getText().length() > 8 || part_desc.getText().isEmpty() || part_debut.isArmed() || part_fin.isArmed()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
            alert.showAndWait();
        } else {
            Partenaire prt = new Partenaire();
            prt.setName(part_nom.getText());
            prt.setType(part_type.getText());
            prt.setDescription(part_desc.getText());
            prt.setNumero(Integer.parseInt(part_num.getText()));
            prt.setDateDebut(part_debut.getValue().toString());
            prt.setDateFin(part_fin.getValue().toString());


            if (ps.addPartenaire(prt))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("partenaire a été ajouté");
                alert.showAndWait();
                Notifications.create().title("Success").text("partenaire est ajouté avec succes").showInformation();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("partenaire a été ajouté");
                alert.showAndWait();
                Notifications.create().title("Success").text("partenaire est ajouté avec succes").showInformation();
            }

        }
    }

}