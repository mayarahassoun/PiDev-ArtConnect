package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import controllers.EventMangement;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private WebView webView;


    public void showWindow() {
        System.out.println("OPENING MAPS");

        System.out.println("TEST SHOW WINDOW");

    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    String latitude;
    String longitude;
    private void closeWindow() {
        // Get the Stage and close it
        Stage stage = (Stage) webView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmer() throws IOException {
        System.out.println("confirming");

        WebEngine webEngine = webView.getEngine();

        String script = "document.getElementById('latitude').innerText + ',' + document.getElementById('longitude').innerText";

        String latLong = (String) webEngine.executeScript(script);

        if (latLong != null && !latLong.isEmpty()) {
            String[] parts = latLong.split(",");
            if (parts.length == 2)  //cela signifie qu'il y a une latitude et une longitude dans la chaîne latLong.
            {
                String latitude = parts[0].trim(); //on stocke la latitude dans la chaîne latitude
                String longitude = parts[1].trim();// on stocke la longitude dans la chaîne longitude
                EventMangement ajoutEvent=new EventMangement();
                ajoutEvent.savecoords(latitude,longitude); // on appelle la fonction savecoords qui est dans la classe AjouterBorne
                DisplayEvent displayEvent=new DisplayEvent();
                displayEvent.savecoords(latitude,longitude);

                Stage currentStage = (Stage) webView.getScene().getWindow(); //  La référence à la fenêtre actuelle, qui contient la carte, est obtenue.
                currentStage.close(); // la fenêtre actuelle est fermée
                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);
            } else {
                System.err.println("Error parsing latitude and longitude");
            }
        } else {
            System.err.println("Latitude and longitude not found in HTML");
        }
    }


    public void setMapLocation(double latitude, double longitude) //
    {
        String script = "initialize(" + latitude + ", " + longitude + ");";
        WebEngine webEngine = webView.getEngine();
        webEngine.executeScript(script);
    }
    public static float latitude_static;
    public static float longitude_static;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        WebEngine webEngine = webView.getEngine(); //Le moteur Web charge et rend le contenu HTML dans la WebView.
        webEngine.load(getClass().getResource("/googleMaps.html").toExternalForm()); // charge le contenu HTML de la carte Google Maps, récupère l'URL du fichier HTML, convertit cette URL en une chaîne (texte)

    }
}