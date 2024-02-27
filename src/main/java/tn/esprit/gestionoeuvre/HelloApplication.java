package tn.esprit.gestionoeuvre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ShowProduit.fxml"));   // produit Client
          FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AfficherOeuvre.fxml")); ///gestion oeuvre Admin
         // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddCategorie.fxml"));  //gestion categorie

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("A!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}