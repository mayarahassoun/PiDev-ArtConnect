package tn.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.models.Partenaire;
import tn.services.partenaireServices;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.awt.*;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Afficherpartenaire {

    @FXML
    private TableColumn<Partenaire, String> Description_col;

    @FXML
    private TableColumn<Partenaire, String> Modifier_col;

    @FXML
    private TableColumn<Partenaire, String> dated_col;

    @FXML
    private TableColumn<Partenaire, String> datef_col;

    @FXML
    private TableColumn<Partenaire, String> delete_col;

    @FXML
    private TableColumn<Partenaire, String> nom_col;

    @FXML
    private TableColumn<Partenaire, Integer> numero_col;

    @FXML
    private TableView<Partenaire> table;

    @FXML
    private TableColumn<Partenaire, String> type_col;

    @FXML
    private TextField tf_search;

    partenaireServices ps = new partenaireServices();
    ObservableList<Partenaire> partlist = ps.displayPartenaire();



    @FXML
    void initialize() {
        /*       initListDeroulante();*/
        showPartenaire(ps.displayPartenaire());



        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            // Use a stream to search the list of events
            List<Partenaire> searchResults = partlist.stream()
                    .filter(event -> event.getName().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toList());
            ObservableList<Partenaire> search = FXCollections.observableArrayList();
            search.addAll(searchResults);
            showPartenaire(search);
        });
    }


    private void showPartenaire (ObservableList<Partenaire> p) {

        table.setItems(p);
        nom_col.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("name"));
        type_col.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("type"));
        Description_col.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("description"));
        numero_col.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumero()));
        dated_col.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("DateDebut"));
        datef_col.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("DateFin"));

        Callback<TableColumn<Partenaire, String>, TableCell<Partenaire, String>> deletecellFactory = (TableColumn<Partenaire, String> param) -> {
            final TableCell<Partenaire, String> cell = new TableCell<Partenaire, String>() {
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
                            Partenaire selectedPartenaire = getTableView().getItems().get(getIndex());

                            //alert
                            if (selectedPartenaire != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initStyle(StageStyle.UTILITY);
                                alert.setTitle("Supprimer l'Partenaire");
                                alert.setHeaderText(null);
                                alert.setContentText("Etes vous sur de vouloir supprimer l'Partenaire ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                                {

                                    if (ps.deletePartenaire(selectedPartenaire.getId())) {
                                        Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                        alerts.initStyle(StageStyle.UTILITY);
                                        alerts.setTitle("Success");
                                        alerts.setHeaderText(null);
                                        alerts.setContentText("Partenaire a été supprimée");
                                        alerts.showAndWait();
                                        showPartenaire(ps.displayPartenaire());
                                    } else {
                                        Alert alertz = new Alert(Alert.AlertType.ERROR);
                                        alertz.initStyle(StageStyle.UTILITY);
                                        alertz.setTitle("Error");
                                        alertz.setHeaderText(null);
                                        alertz.setContentText("Partenaire n'a pas été supprimée");
                                        alertz.showAndWait();
                                    }
                                }

                            } else {
                                Alert alertz = new Alert(Alert.AlertType.ERROR);
                                alertz.initStyle(StageStyle.UTILITY);
                                alertz.setTitle("Error");
                                alertz.setHeaderText(null);
                                alertz.setContentText("selectionnez un Partenaire");
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

        Callback<TableColumn<Partenaire, String>, TableCell<Partenaire, String>> modifCellFactory = (TableColumn<Partenaire, String> param) -> {
            final TableCell<Partenaire, String> cell = new TableCell<Partenaire, String>() {

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
                            Partenaire selectedPartenaire = getTableView().getItems().get(getIndex());

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierpartenaire.fxml"));
                                Parent root = loader.load();
                                Modifierpartenaire PartenaireController = loader.getController();
                                PartenaireController.setPartenaire(selectedPartenaire);
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
    private void goToAddPartenaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajoutpartenaire.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void gotoAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void triParName(ActionEvent event) {
        showPartenaire(ps.displayPartenaireByName());
    }

    @FXML
    void cancelTri(ActionEvent event) {
        showPartenaire(ps.displayPartenaire());

    }

}