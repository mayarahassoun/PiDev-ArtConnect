package tn.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.models.PointVente;
import tn.services.PointVenteServices;

import java.util.List;
import java.util.stream.Collectors;

public class Frontpointvente {

    @FXML
    private TableColumn<PointVente, String> Description_col;

    @FXML
    private TableColumn<PointVente, String> localisation_col;

    @FXML
    private TableColumn<PointVente, String> nom_col;

    @FXML
    private TableColumn<PointVente, Integer> numero_col;

    @FXML
    private TableView<PointVente> table;



    PointVenteServices pvs = new PointVenteServices();
    ObservableList<PointVente> pointlist = pvs.displayPointVente();

    @FXML
    void initialize() {
        //  initListDeroulante();
        showPointvente(pvs.displayPointVente());
    }
    private void showPointvente (ObservableList<PointVente> p) {

        table.setItems(p);
        nom_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("name"));
        localisation_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("localisation"));
        Description_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("description"));
        numero_col.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumero()));
    }

}
