/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionoeuvre.controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.gestionoeuvre.entity.Oeuvre;
import tn.esprit.gestionoeuvre.entity.panier;
import tn.esprit.gestionoeuvre.service.ProduitService;
import tn.esprit.gestionoeuvre.service.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowPanierController implements Initializable {

    @FXML
    private ListView<Oeuvre> listView;
   
    ObservableList<Oeuvre> data;
    
    public static int idE ;
    
    Service ds = new Service();
    ProduitService ps = new ProduitService();

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        List<Oeuvre> listProduit = new ArrayList<>();
        
        
        try {
            for (panier p : ds.getAllPanier()){
                listProduit.add(ps.getOeuvreById(p.getId_produit()));
            }
            
         ObservableList<Oeuvre> observableList = FXCollections.observableList(listProduit);
        data = (ObservableList<Oeuvre>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<Oeuvre> param) -> new ListViewPanier());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
        
        // TODO
    }    




    @FXML
    private void PasserCommande(ActionEvent event) throws SQLDataException {


        
    }

    @FXML
    private void AnnulerCommande(ActionEvent event) throws SQLDataException {
        
             ds.deletePanier();
               Parent root;
                       try {
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn/esprit/gestionOeuvre/ShowProduit.fxml")));
               Stage myWindow = (Stage) listView.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
        
    }

    @FXML
    private void SuprimerItem(ActionEvent event) throws SQLDataException {
        
                ObservableList<Oeuvre> e = listView.getSelectionModel().getSelectedItems();
         for (Oeuvre m : e) {
             ds.deleteFromPanier(m.getIdOeuvre());
         }
         
                  Parent root;
                       try {
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ShowPanier.fxml")));
               Stage myWindow = (Stage) listView.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
               }


        
    }




    }

    

