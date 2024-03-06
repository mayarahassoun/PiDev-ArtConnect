package controllers;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.scene.effect.Bloom;
import models.Event;
import services.EventServices;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class DisplayEvent {
    @FXML
    private ImageView postFacebook;

    @FXML
    private TextField rechrecheTF;
    @FXML
    private DatePicker datemodifier;

    @FXML
    private TextField descmodifier;
    @FXML

    private ImageView imagemodifier;
    @FXML
    private TextField eventpath;

    @FXML
    private TextField localisationmodifier;
    static float latitude;
    static float longitude;

    @FXML
    private TextField nommodifier;

    @FXML
    private TextField typemodifier;
    @FXML
    private TextField idmodifier;
    @FXML
    private GridPane gridPane;
    private List<Event> events;
    private String filePath;



    // Méthode pour initialiser et afficher les données dans le GridPane
    @FXML
    public void initialize() {
        EventServices eventServices = new EventServices();
        events = eventServices.getEvent();

        int rowIndex = 0;
        int columnIndex = 0;
        int columnCount = 3; // Nombre de colonnes dans la grille d'événements

        for (Event event : events) {
            // Création des labels avec des styles CSS
            Label nameLabel = new Label(event.getName());
            nameLabel.getStyleClass().add("event-label1");

            Label typeLabel = new Label("Type: " + event.getType());
            typeLabel.getStyleClass().add("event-label3");

            Label dateLabel = new Label("Date: " + event.getDate());
            dateLabel.getStyleClass().add("event-label3");

            Label descLabel = new Label("Description: " + event.getDesc());
            descLabel.getStyleClass().add("event-label3");

            Label localLabel = new Label("Localisation: " + event.getLocal());
            localLabel.getStyleClass().add("event-label3");

            // Créer un objet ImageView avec l'image chargée depuis le chemin spécifié
            ImageView imageView = new ImageView(new Image("file:" + event.getImagePath()));
            imageView.getStyleClass().add("event-image");

            // Définir une taille pour l'image si nécessaire
            imageView.setFitWidth(200); // Changer la largeur selon vos besoins
            imageView.setFitHeight(200); // Changer la hauteur selon vos besoins

            Bloom bloomEffect = new Bloom();
            bloomEffect.setThreshold(10.5); // Ajustez la valeur du seuil selon vos préférences
            nameLabel.setEffect(bloomEffect);
            typeLabel.setEffect(bloomEffect);
            dateLabel.setEffect(bloomEffect);
            descLabel.setEffect(bloomEffect);
            localLabel.setEffect(bloomEffect);

            // Créer un bouton pour supprimer et modifier
            Button supprimerButton = new Button("Supprimer");
            supprimerButton.getStyleClass().add("reserve-button");
            ImageView mapIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/mapsicon.png")));
            Button mapButton = new Button("Localiser");
            mapButton.setGraphic(mapIcon);
            mapIcon.setFitHeight(40);
            mapIcon.setFitWidth(40);
            mapIcon.setOnMouseClicked(eventt ->{
                try {
                    openMap(event.getLatitude(),event.getLongitude());
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            });
            Button modifierButton = new Button("Modifier");
            modifierButton.getStyleClass().add("reserve-button");

            // Ajouter chaque étiquette et image au GridPane d'événement
            GridPane eventGridPane = new GridPane();

// Ajout des contraintes de colonne au GridPane

            eventGridPane.add(imageView, 0, 0);
            eventGridPane.add(nameLabel, 1, 0);
            eventGridPane.add(typeLabel, 0, 1);
            eventGridPane.add(dateLabel, 1, 1);
            eventGridPane.add(descLabel, 0, 2);
            eventGridPane.add(localLabel, 1, 2);
            eventGridPane.add(supprimerButton, 0, 4);
            eventGridPane.add(modifierButton, 1, 4);
            eventGridPane.add(mapIcon, 0, 3);
            eventGridPane.getStyleClass().add("event-grid-pane");

            // Ajouter un gestionnaire d'événements pour le bouton "Supprimer"
            supprimerButton.setOnAction(e -> handleDeleteButton(event));
            modifierButton.setOnAction(e -> handleUpdateButton(event));


            // Ajouter le GridPane d'événement au GridPane principal
            gridPane.add(eventGridPane, columnIndex, rowIndex);

            // Incrémenter les indices de colonne et de ligne
            columnIndex++;
            if (columnIndex == columnCount) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }
    private void openMap(float latitude, float longitude) throws IOException {
        LocationView.lat=latitude;
        LocationView.lon=longitude;
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/fxml/LocationView.fxml")));
        System.out.println(LocationView.lat);
        System.out.println(LocationView.lon);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("localiser le borne");
        stage.setScene(scene);
        stage.show();
    }
    public void savecoords(String latitude, String longitude) {

        DisplayEvent.latitude =Float.parseFloat(latitude);
        DisplayEvent.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER BORNE"+latitude+"  "+latitude);
    }


    private void handleUpdateButton(Event event) {
        EventServices es = new EventServices();
        int id = event.getId();
        String pathimage = event.getImagePath();
        Image image = new Image("file:" + pathimage);
        imagemodifier.setImage(image);
        String name = event.getName();
        String type = event.getType();
        Date date = event.getDate();
        String desc = event.getDesc();
        String local = event.getLocal();

        nommodifier.setText(name);
        typemodifier.setText(type);
        descmodifier.setText(desc);
        localisationmodifier.setText(local);
        idmodifier.setText(String.valueOf(id));
        ;
        eventpath.setText(pathimage);


    }

    @FXML
    void confirmer(ActionEvent event) {
        int id = Integer.parseInt(idmodifier.getText());
        String name = nommodifier.getText();
        String type = typemodifier.getText();
        String desc = descmodifier.getText();
        String localisation = localisationmodifier.getText();
        LocalDate date = datemodifier.getValue();
        String path = eventpath.getText();


        Event ev = new Event(id, name, type, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), desc, path, localisation);
        ev.setName(name);
        ev.setId(id);
        ev.setType(type);
        ev.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ev.setDesc(desc);
        ev.setImagePath(path);
        ev.setLocal(localisation);
        ev.setLatitude(latitude);
        ev.setLongitude(longitude);

        EventServices evs = new EventServices();
        try {
            evs.updateEvent(ev);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Evenement modifier avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors la modification de l evenment : " + e.getMessage());
            alert.showAndWait();
        }

        evs.updateEvent(ev);
        refreshGrid();


    }

    private void handleDeleteButton(Event eventToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            EventServices es = new EventServices();
            es.deleteEvent(eventToDelete.getId());
            refreshGrid();
        }
    }

    private void refreshGrid() {
        gridPane.getChildren().clear();
        initialize();
    }

    @FXML
    void handleUploadButtonAction(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null && isImageFile(selectedFile)) {
            // Store the file path
            filePath = selectedFile.getAbsolutePath();
            System.out.println("File path stored: " + filePath);
            eventpath.setText(filePath);

            // Set the image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imagemodifier.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }
    }

    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    void afficherReservation(ActionEvent event)
    {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/DisplayReservation.fxml"));
        try {
            rechrecheTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void chercher(ActionEvent event) {
        EventServices eventServices = new EventServices();
        String rechercheName = rechrecheTF.getText();
        List<Event> events = eventServices.getEventsByName(rechercheName);

        if (!events.isEmpty()) {
            Event event1 = events.get(0); // Supposons que vous voulez travailler avec le premier événement trouvé
            int id = event1.getId();
            String pathimage = event1.getImagePath();
            Image image = new Image("file:" + pathimage);
            imagemodifier.setImage(image);
            String name = event1.getName();
            String type = event1.getType();
            Date date = event1.getDate();
            String desc = event1.getDesc();
            String local = event1.getLocal();

            nommodifier.setText(name);
            typemodifier.setText(type);
            descmodifier.setText(desc);
            localisationmodifier.setText(local);
            idmodifier.setText(String.valueOf(id));
            eventpath.setText(pathimage);

        } else {
            // Gérer le cas où aucun événement n'est trouvé avec le nom spécifié
            System.out.println("Aucun événement trouvé avec le nom spécifié.");
        }
    }
    @FXML
    void menu(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/dashboard.fxml"));
        try {
            idmodifier.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void postFacebook(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        EventServices i =new EventServices();
        Event event =i.getLastInsertedEvent();
        String appId = "378072095035049";
        String appSecret = "6d03ad71f60d06967f8c68eb6171eb49";
        String accessTokenString = "EAAFX2sTsjqkBOwQlIGbqGuA73ZBhUCab3ZANT85ZCoqLkxTbPefP5tmZAtT9pJF6E5Lt1GydyZACFxWZBifkXHWnbtZCFmh1ck032K8Ih3cLFQZCHRcxYabpeQ0HzZAwBkS5RpZAsDBv4W69h9fZBp9VzgMnQK3RZBG2Ky8s3lRKezV9AzvZCRd8dQTI21yFGV9Tfd3F0ZBiguuqYUIeVeJVLebV874aQZD\n";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        String msg = "L'Evenement le plus récent"
                + "\n-Name: "
                + event.getName()
                + "\n-Type: "
                + event.getType()
                + "\n-Date: "
                + event.getDate()
                + "\n-Localisation: "
                + event.getLocal();

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
    }

    public void openmap(ActionEvent actionEvent) throws IOException {
        controllers.MapController m=new MapController();
        m.showWindow();
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/fxml/webview.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("localiser le borne");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ajouterEvent(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/EventMangement.fxml"));
        try {
            idmodifier.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}