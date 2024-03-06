package controllers;
import home.Main;
import utils.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;


public class Stat {
    private Connection connection;

public Stat(){connection = (Connection) ConnectionUtil.conDB();}


    // private Statement ste;

    @FXML
    private PieChart pieChart;
    @FXML
    private TextField retour;

    @FXML
    void afficherStatistique(ActionEvent event) {

    }

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            // Utilisez un Statement pour exécuter la requête SQL
            try (Statement ste = connection.createStatement();
                 ResultSet resultSet = ste.executeQuery("SELECT idreserv, etat, COUNT(*) FROM reservations GROUP BY  idreserv,etat")) {

                // Parcours des résultats et ajout des données au PieChart
                while (resultSet.next()) {
                    String etat = resultSet.getString("etat");
                    int id_reservation = resultSet.getInt("idreserv");
                    int nombreEvenements = resultSet.getInt(3); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                    PieChart.Data slice = new PieChart.Data(id_reservation + " - id_reservation " + etat, nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
    @FXML
    void retourbuttun(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/DisplayReservation.fxml"));
        try {
            retour.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
}
