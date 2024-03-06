package controllers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;


import controllers.Frontpointvente;
import models.Partenaire;
import models.PointVente;
import org.controlsfx.control.Notifications;
import services.PointVenteServices;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Afficherpointvente {

    @FXML
    private TableColumn<PointVente, String> Description_col;

    @FXML
    private TableColumn<PointVente, String> Modifier_col;

    @FXML
    private TableColumn<PointVente, String> delete_col;

    @FXML
    private TableColumn<PointVente, String> localisation_col;

    @FXML
    private TableColumn<PointVente, String> nom_col;

    @FXML
    private TableColumn<PointVente, Integer> numero_col;

    @FXML
    private TableView<PointVente> table;

    @FXML
    private TextField tf_search;



    PointVenteServices pvs = new PointVenteServices();
    ObservableList<PointVente> pointlist = pvs.displayPointVente();



    @FXML
    void initialize() {
           //  initListDeroulante();
        showPointvente(pvs.displayPointVente());



        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            // Use a stream to search the list of events
            List<PointVente> searchResults = pointlist.stream()
                    .filter(event -> event.getName().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toList());
            ObservableList<PointVente> search = FXCollections.observableArrayList();
            search.addAll(searchResults);
            showPointvente(search);
        });
    }

    private void showPointvente (ObservableList<PointVente> p) {

        table.setItems(p);
        nom_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("name"));
        localisation_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("localisation"));
        Description_col.setCellValueFactory(new PropertyValueFactory<PointVente, String>("description"));
        numero_col.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumero()));



        Callback<TableColumn<PointVente, String>, TableCell<PointVente, String>> deletecellFactory = (TableColumn<PointVente, String> param) -> {
            final TableCell<PointVente, String> cell = new TableCell<PointVente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button();
                        deleteIcon.setText("Supprimer");

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            PointVente selectedPointVente = getTableView().getItems().get(getIndex());

                            //alert
                            if (selectedPointVente != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initStyle(StageStyle.UTILITY);
                                alert.setTitle("Supprimer le PointVente");
                                alert.setHeaderText(null);
                                alert.setContentText("Etes vous sur de vouloir supprimer le PointVente ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                                {

                                    if (pvs.deletePointVente(selectedPointVente.getId())) {
                                        Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                        alerts.initStyle(StageStyle.UTILITY);
                                        alerts.setTitle("Success");
                                        alerts.setHeaderText(null);
                                        alerts.setContentText("PointVente a été supprimée");
                                        alerts.showAndWait();
                                        Notifications.create().title("Success").text("point vente est supprimée avec succes").showInformation();
                                        showPointvente(pvs.displayPointVente());
                                    } else {
                                        Alert alertz = new Alert(Alert.AlertType.ERROR);
                                        alertz.initStyle(StageStyle.UTILITY);
                                        alertz.setTitle("Error");
                                        alertz.setHeaderText(null);
                                        alertz.setContentText("PointVente n'a pas été supprimée");
                                        alertz.showAndWait();
                                    }
                                }

                            } else {
                                Alert alertz = new Alert(Alert.AlertType.ERROR);
                                alertz.initStyle(StageStyle.UTILITY);
                                alertz.setTitle("Error");
                                alertz.setHeaderText(null);
                                alertz.setContentText("selectionnez un PointVente");
                                alertz.showAndWait();
                            }
                        });

                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };

        Callback<TableColumn<PointVente, String>, TableCell<PointVente, String>> modifCellFactory = (TableColumn<PointVente, String> param) -> {
            final TableCell<PointVente, String> cell = new TableCell<PointVente, String>() {

                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button edtIcon = new Button();
                        edtIcon.setText("Modifier");


                        edtIcon.setOnMouseClicked((MouseEvent event) -> {
                            PointVente selectedPointVente = getTableView().getItems().get(getIndex());

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifierpointvente.fxml"));
                                Parent root = loader.load();
                                Modifierpointvente PointVenteController = loader.getController();
                                PointVenteController.setPointvente(selectedPointVente);
                                table.getScene().setRoot(root);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        });
                        HBox managebtn = new HBox(edtIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edtIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }

                }
            };




            return cell;

        };



        delete_col.setCellFactory(deletecellFactory);
        Modifier_col.setCellFactory(modifCellFactory);

    }

    @FXML
    void cancelTri(ActionEvent event) {

    }

    @FXML
    void goToAddPointvente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ajoutpointvente.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void gotoAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void triParName(ActionEvent event) {

    }


    @FXML
    void Imprimer(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(table.getScene().getWindow())) {
            boolean success = printerJob.printPage(table);
            if (success) {
                printerJob.endJob();
                System.out.println("Printing completed successfully.");
            } else {
                System.out.println("Printing failed.");
            }
        }
    }



}
