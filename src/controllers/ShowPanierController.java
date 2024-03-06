/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Oeuvre;
import models.panier;
import services.ProduitService;
import services.Service;

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

    private static final String STRIPE_SECRET_KEY = "sk_test_51OqhNmAEAwH0wDNb43alJF9dLRE8XPczVoLJp8ywcryrYBt6WohoFzwatj1u2DYAuPHEODX0064hyVVLGEmuoZQG00YTcsm5E8";


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
        ProduitService ps = new ProduitService();
       List<panier> p = ds.getAllPanier();
       Long amout = 0L ;
        Stripe.apiKey = STRIPE_SECRET_KEY;


       for (panier pa : p){
          amout =((long)(ps.getOeuvreById(pa.getId_produit()).getPrix())+amout);
       }

        try {
            processPayment(amout*100); // Montant en cents (1000 = 10.00 USD)
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        sendSMS("+21627529875",amout);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Paiement Avec succès", ButtonType.OK);
        alert.showAndWait();
        ObservableList<Oeuvre> e = listView.getSelectionModel().getSelectedItems();
        for (Oeuvre m : e) {
            ds.deleteFromPanier(m.getIdOeuvre());
        }
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ShowProduit.fxml")));
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
    private void AnnulerCommande(ActionEvent event) throws SQLDataException {
        
             ds.deletePanier();
               Parent root;
                       try {
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ShowProduit.fxml")));
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
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/ShowPanier.fxml")));
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




    public void processPayment(Long amount) throws StripeException {
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(amount) // Montant en centimes
                .setCurrency("eur")
                .setDescription("Example Payment")
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
    }

    void sendSMS(String recipientNumber,Long amount) {
        String ACCOUNT_SID = "AC67833cab788287fd37feafa04048da1f";
        String AUTH_TOKEN = "a022ddf3937eadc4c71d625b6521d7e2";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        // recipientNumber = "+21629082917";
        String message = "Votre Paiement du montant "+amount+"a été effectué avec succès";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("19497103950"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());

    }

    }

    

