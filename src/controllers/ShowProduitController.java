/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import models.Oeuvre;
import models.panier;
import services.ProduitService;
import services.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ShowProduitController implements Initializable {

    @FXML
    private ListView<Oeuvre> listView;
   
    ObservableList<Oeuvre> data;
    
    public static int idE ;
    
    Service ds = new Service();
    ProduitService p = new ProduitService();
    
    @FXML
    private Label label;

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            data = (ObservableList<Oeuvre>)p.getAllOeuvres();
            listView.setItems(data);
            listView.setCellFactory((ListView<Oeuvre> param) -> new ListViewProduit());
            
            
            // TODO
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    




    @FXML
    private void AjouterPanier(ActionEvent event) throws SQLException {
        
       ObservableList<Oeuvre> e = listView.getSelectionModel().getSelectedItems();

       

          panier p = new panier();
       
           for (Oeuvre m : e) {
             List<panier> listpanier =  ds.getAllPanier();

               if(listpanier.isEmpty()){
               System.out.println("gggg"+ listpanier.isEmpty());
               p.setId_produit(m.getIdOeuvre());
               p.setQuantité(1);
               ds.addPanier(p);
               }
               
              else if(ds.get_PanierByProduit(m.getIdOeuvre()).getId_panier()!=0){
               p.setId_produit(m.getIdOeuvre());
               p.setQuantité(p.getQuantité()+1);
               System.out.println("id"+p.getId_produit()+"quantité"+p.getQuantité());
               ds.ModifierQuantite(m.getIdOeuvre(),p.getQuantité()+1);

               }
              else{
               p.setId_produit(m.getIdOeuvre());
               p.setQuantité(1); 
               ds.addPanier(p);
              }

           }
           
           label.setText(String.valueOf(Integer.valueOf(label.getText())+1));
        
        
    }

    @FXML
    private void Panier(ActionEvent event) {
        
                
        Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/fxml/ShowPanier.fxml"));
               Stage myWindow = (Stage) label.getScene().getWindow();
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

    

