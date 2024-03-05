package tn.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import tn.models.Partenaire;
import tn.models.PointVente;
import tn.services.PointVenteServices;
import tn.services.partenaireServices;

import java.time.LocalDate;

public class Modifierpointvente {

    @FXML
    private TextArea point_desc;


    @FXML
    private TextField point_localisation;

    @FXML
    private TextField point_nom;

    @FXML
    private TextField point_num;

    PointVente pointVente ;

    PointVenteServices pvs = new PointVenteServices();

    @FXML
    void modifierPointvente(ActionEvent event) {
        if (point_nom.getText().isEmpty() || point_localisation.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
            alert.showAndWait();
        } else  {

            pointVente.setName(point_nom.getText());
            pointVente.setLocalisation(point_localisation.getText());
            pointVente.setDescription(point_desc.getText());
            pointVente.setNumero(Integer.parseInt(point_num.getText()));


            if (pvs.updatePointVente(pointVente)) {
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

    public void setPointvente(PointVente pv) {
        pointVente = pv;
        point_nom.setText(pv.getName());
        point_desc.setText(pv.getDescription());
        point_localisation.setText(pv.getLocalisation());
        point_num.setText(String.valueOf(pv.getNumero()));

    }

    public void affichagePointvente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpointvente.fxml"));
            Parent root = loader.load();
            point_nom.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}