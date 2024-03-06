package controllers;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class frontmenu {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private User user;




    @FXML
    void btnevent(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }}


    @FXML
    void btnreclamation(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuSalma.fxml."));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }}
   /* @FXML
    void btnpart(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }}*/
 /*   @FXML
    void btnoffre(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }}*/
    @FXML
    void bntoeuvre(ActionEvent event) {
        {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ShowProduit.fxml"));
                Parent root  = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
                stage.setScene(new Scene(root)); // Set the new scene on the stage
                stage.show(); // Show the stage
            } catch (Exception ex) {
                ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
            }


        }

    }

    @FXML
    void bntprofile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Parent root = loader.load();
            ProfilController controller = loader.getController();
            controller.initData(user); // Pass the user object to the ProfileController
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }





}
