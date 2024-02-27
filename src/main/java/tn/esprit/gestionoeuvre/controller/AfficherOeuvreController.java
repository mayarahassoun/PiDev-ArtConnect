package tn.esprit.gestionoeuvre.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.gestionoeuvre.entity.Categorie;
import tn.esprit.gestionoeuvre.entity.Oeuvre;
import tn.esprit.gestionoeuvre.service.CategorieService;
import tn.esprit.gestionoeuvre.service.ProduitService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherOeuvreController implements Initializable {


    @FXML
    private TableColumn<Oeuvre, String> cat;
    @FXML
    private TableColumn<Oeuvre, String> cert;
    @FXML
    private TableColumn<Oeuvre, String> des;

    @FXML
    private TableColumn<Oeuvre, String> nom;

    @FXML
    private TableColumn<Oeuvre, Float> prix;

    @FXML
    private TableColumn<Oeuvre, Integer> qt;


    private final ObservableList<Oeuvre> UserOeuvre = FXCollections.observableArrayList();

    ProduitService produitService = new ProduitService();
    CategorieService categorieService = new CategorieService();

    public  static int idOeuvre ;

    @FXML
    private TableView<Oeuvre> table;

    @FXML
    void Ajouter(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn/esprit/gestionoeuvre/AddOeuvre.fxml")));
            Stage myWindow = (Stage) table.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Delete(ActionEvent event) throws SQLDataException {
        int id =  table.getSelectionModel().getSelectedItem().getIdOeuvre();
        produitService.deleteOeuvre(id);
        resetTableData();
    }

    @FXML
    void Modif(ActionEvent event) {
        idOeuvre =  table.getSelectionModel().getSelectedItem().getIdOeuvre();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModifierOeuvre.fxml")));
            Stage myWindow = (Stage) table.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            List<Oeuvre> listb= new ArrayList<Oeuvre>();

            listb = produitService.getAllOeuvres();

            System.out.println("hhhh"+listb);
            UserOeuvre.clear();
            UserOeuvre.addAll(listb);
            table.setItems(UserOeuvre);

            des.setCellValueFactory
                    (
                            new PropertyValueFactory<>("description")
                    );
            prix.setCellValueFactory
                    (
                            new PropertyValueFactory<>("prix")
                    );
            qt.setCellValueFactory
                    (
                            new PropertyValueFactory<>("quantite")
                    );
            nom.setCellValueFactory
                    (
                            new PropertyValueFactory<>("nom")
                    );
            cert.setCellValueFactory
                    (
                            new PropertyValueFactory<>("certif")
                    );

            cat.setCellValueFactory(cellData -> {
                Oeuvre o = cellData.getValue();
                Categorie c = null; // Assurez-vous que cette méthode existe
                c = categorieService.get_CatById(o.getId_cat());
                return new SimpleStringProperty(c.getDescription()); // Accédez à l'attribut "nom" de l'objet Home
            });

        } catch (SQLDataException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


    public void resetTableData() throws SQLDataException
    {
        List<Oeuvre> lisre = new ArrayList<>();
        lisre = produitService.getAllOeuvres();
        ObservableList<Oeuvre> data = FXCollections.observableArrayList(lisre);
        table.setItems(data);
    }
}
