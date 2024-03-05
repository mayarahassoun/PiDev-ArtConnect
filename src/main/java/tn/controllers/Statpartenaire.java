package tn.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import tn.models.Partenaire;
import tn.services.partenaireServices;

import java.util.*;

public class Statpartenaire{

    @FXML
    private PieChart pie;



    public void initialize() {

        List<String> listAges = new ArrayList<>();

        List<Partenaire> partenaires = new ArrayList<>();

        partenaireServices ps = new partenaireServices();
        partenaires = ps.displayPartenaire();
        for (Partenaire p : partenaires){
            listAges.add(p.getName());

        }

        Map<String, Integer> occurences = new HashMap<>();

        for (String element : listAges) {
            if (occurences.containsKey(element)) {
                int count = occurences.get(element);
                occurences.put(element, count + 1);
            } else {
                occurences.put(element, 1);
            }
        }

        System.out.println("Nombre d'utilisateurs par adresse: ");
        for (Map.Entry<String, Integer> entry : occurences.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(entry.getKey(), entry.getValue())
                    );
            pie.getData().addAll(pieChartData);
            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), "  ", data.pieValueProperty()
                            )
                    )
            );
        }
    }

    @FXML
    void affichagePartenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpartenaire.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }

}

