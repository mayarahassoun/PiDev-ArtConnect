package tn.controllers;


import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

//import com.itextpdf.layout.property.TextAlignment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.models.Partenaire;
import tn.services.partenaireServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.utils.PdfGenerator;

import static com.itextpdf.io.source.PdfTokenizer.TokenType.Name;


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
    public static int idgetter;
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


    @FXML
    private void pdf(ActionEvent event) {
        PdfGenerator pg=new PdfGenerator();
        String date=LocalDateTime.now().toLocalDate().toString();
        String path="C:\\Users\\MSI\\IdeaProjects\\ProjetPiJava\\src\\main\\java\\tn\\pdf\\Partenaire"+date+".pdf";
        Document doc=pg.createPdf(path);
        doc.add(generatePartenairePdfTable());
        doc.close();
    }
    public Table generatePartenairePdfTable(){
        int row=1;
        String imgPath="C:\\Users\\MSI\\IdeaProjects\\ProjetPiJava\\src\\main\\java\\tn\\img\\";
        PdfFont bold=null;
        try {
            bold=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        } catch (IOException ex) {
            Logger.getLogger(Afficherpartenaire.class.getName()).log(Level.SEVERE, null, ex);
        }

        Table table=new Table(new float[6]).useAllAvailableWidth();
        table.setMargin(5);

        Cell cell=new Cell(1,6).add(new Paragraph("Liste des partenaires").setFontSize(14));
       // cell.setTextAlignment(TextAlignment.CENTER);
        cell.setPadding(5);
        table.addCell(cell);
       // table.setTextAlignment(TextAlignment.CENTER);


        //table.addCell(new Cell(1,1).add(new Paragraph("Id article")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("Name")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("Type")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("Description")).setFont(bold));/*
        table.addCell(new Cell(1,1).add(new Paragraph("Gouvernorat")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("Region")).setFont(bold));*/
        table.addCell(new Cell(1,1).add(new Paragraph("Numero")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("DateDebut")).setFont(bold));
        table.addCell(new Cell(1,1).add(new Paragraph("DateFin")).setFont(bold));


        for(Partenaire a:ps.displayPartenaire()){
            //table.addCell(a.getId()+"");
            table.addCell(a.getName()+"");
            table.addCell(a.getType()+"");
            table.addCell(a.getDescription()+"");
            /*
            table.addCell(a.getGouvernorat()+"");
            table.addCell(a.getRegion()+"");*/
            table.addCell(a.getNumero()+"");
            table.addCell(a.getDateDebut()+"");
            table.addCell(a.getDateFin()+"");



            row++;
        }
        return table;


    }



    @FXML
    void statistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statpartenaire.fxml"));
            Parent root = loader.load();
            table.getScene().setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}