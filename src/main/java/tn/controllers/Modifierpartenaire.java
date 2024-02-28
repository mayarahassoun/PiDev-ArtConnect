package tn.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import tn.models.Partenaire;
import tn.services.partenaireServices;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Modifierpartenaire {

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
    Partenaire partenaire ;

    partenaireServices ps = new partenaireServices();

    @FXML
    void affichagePartenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpartenaire.fxml"));
            Parent root = loader.load();
            part_nom.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setPartenaire(Partenaire prt) {
        partenaire = prt;
        part_nom.setText(prt.getName());
        part_debut.setValue(LocalDate.parse(prt.getDateDebut()));
        part_fin.setValue(LocalDate.parse(prt.getDateFin()));
        part_desc.setText(prt.getDescription());
        part_type.setText(prt.getType());
        part_num.setText(String.valueOf(prt.getNumero()));

    }

    @FXML
    void modifierPartenaire(ActionEvent event)  {

        if (part_nom.getText().isEmpty() || part_type.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
            alert.showAndWait();
        } else  {

            partenaire.setName(part_nom.getText());
            partenaire.setType(part_type.getText());
            partenaire.setDescription(part_desc.getText());
            partenaire.setNumero(Integer.parseInt(part_num.getText()));
            partenaire.setDateDebut(part_debut.getValue().toString());
            partenaire.setDateFin(part_fin.getValue().toString());

            if (ps.updatePartenaire(partenaire)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("partenaire a été modifié");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("partenaire n'a pas été modifié");
                alert.showAndWait();
            }

        }
    }



}

