package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.Categorie;
import models.Oeuvre;
import services.CategorieService;
import services.ProduitService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Statistique implements Initializable {



    @FXML
    private CategoryAxis Xsujet;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private NumberAxis yNombre;


    ProduitService produitService = new ProduitService();

    CategorieService categorieService = new CategorieService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            System.out.println("ssssss");
            setDataToBarChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void setDataToBarChart() throws SQLException {

        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        try {
            for (Categorie c : categorieService.getAllCategories()){
                data.add(new XYChart.Data<>(c.getDescription(), produitService.getProduitByCategorie(categorieService.getCategorieByDescription(c.getDescription()).getId_categori())));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer la série de données à partir des données
        XYChart.Series<String, Number> series = new XYChart.Series<>(data);
        series.setName("User Status");

        // Ajouter la série de données au graphique à barres
        barChart.getData().add(series);
    }



}
