package controllers;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Categorie;
import models.Oeuvre;
import services.CategorieService;
import services.ProduitService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherOeuvreController implements Initializable {



    private static final String CHARSET = "UTF-8";
    private static final String FILE_FORMAT = "png";
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
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddOeuvre.fxml")));
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
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ModifierOeuvre.fxml")));
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
                Categorie c = null; // Assure que cette méthode existe
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



    @FXML
    void generer(ActionEvent event) {
        Oeuvre e =  table.getSelectionModel().getSelectedItem();

        try {
            String data = "Nom :"+e.getNom()+" description : "+e.getDescription()+"Prix :"+e.getPrix(); // Votre texte ou données à encoder dans le code QR
            String filePath = "\"C:\\Users\\abdel\\OneDrive\\Desktop\\Qr_CODE\""+e.getNom()+".png"; // Chemin du fichier de sortie
            int width = 300; // Largeur du code QR en pixels
            int height = 300; // Hauteur du code QR en pixels

            generateQRCode(data, filePath, width, height);
            System.out.println("Code QR généré avec succès !");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void generateQRCode(String data, String filePath, int width, int height) throws IOException, WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, CHARSET);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);

        File outputFile = new File(filePath);
        MatrixToImageWriter.writeToFile(bitMatrix, FILE_FORMAT, outputFile);
    }

    @FXML
    void stat(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Statistique.fxml")));
            Stage myWindow = new Stage();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Statistique");
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void retour_oeuvre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu1.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }
}
