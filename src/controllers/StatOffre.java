package controllers;
import home.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import models.Entreprise;
import services.EntrepriseService;
import models.OffreDemploi;
import services.OffreDemploiService;



public class StatOffre implements Initializable {
    @FXML
    AnchorPane anchor;
    @FXML
    PieChart piechart;
    EntrepriseService entrepriseService= new EntrepriseService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Retrieve entreprise data
            List<Entreprise> entreprises = entrepriseService.recuperer();

            // Calculate counts for each unique address
            Map<String, Integer> addressCounts = new HashMap<>();
            for (Entreprise entreprise : entreprises) {
                String address = entreprise.getAdresse();
                addressCounts.put(address, addressCounts.getOrDefault(address, 0) + 1);
            }

            // Populate Pie Chart with address counts
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : addressCounts.entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
                data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseEntered(event -> {
                            Tooltip tooltip = new Tooltip(entry.getKey() + ": " + entry.getValue());
                            Tooltip.install(newValue, tooltip);
                        });
                    }
                });
                pieChartData.add(data);
            }
            piechart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void gestionOffreDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/AjouterOffreDemploi.fxml"));
        try {
            anchor.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gestionEntreprise(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/AjouterEntreprise.fxml"));
        try {
            anchor.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void menuOffreDemploi(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dashbordOffre.fxml"));
        try {
            anchor.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



