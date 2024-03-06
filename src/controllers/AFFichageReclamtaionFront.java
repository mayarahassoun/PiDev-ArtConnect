package controllers;

import home.Main;
import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;


public class AFFichageReclamtaionFront implements Initializable {




    @FXML
    private ListView<Reclamation> reclamationListView;
    private FilteredList<Reclamation> filteredReclamations;

    @FXML
    private TextField filtre;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reclamationListView.setCellFactory(new ReclamationCellFactory());

        ServiceReclamation a =new ServiceReclamation();

        List<Reclamation> reclamations =a.afficherReclamationsParUser() ;

        // Set the items in the ListView
        reclamationListView.getItems().addAll(reclamations);
        // Créer la FilteredList
        filteredReclamations = new FilteredList<>(FXCollections.observableList(reclamations));

// Initialiser votre ListView
        reclamationListView.setItems(filteredReclamations);
    }





    public class ReclamationCellFactory implements Callback<ListView<Reclamation>, ListCell<Reclamation>> {

        @Override
        public ListCell<Reclamation> call(ListView<Reclamation> param) {
            return new ListCell<Reclamation>() {

                @Override
                protected void updateItem(Reclamation item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Create a custom cell with image, text, and a button
                        HBox cellContent = createCellContent(item);
                        setGraphic(cellContent);
                    }
                }
            };
        }

        private HBox createCellContent(Reclamation reclamation) {
            ImageView imageView = new ImageView(new Image(getClass().getResource("/images/img.png").toExternalForm()));
            imageView.setFitWidth(70);
            imageView.setFitHeight(70);

            Label titleLabel = new Label("Réclamation Details:");
            titleLabel.setStyle("-fx-font-size: 16; -fx-text-fill: blue;"); // Adjust color and font size
            Label typeLabel = new Label("Type:" + reclamation.getType());
            typeLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size

            Label dateLabel = new Label("Date:" + reclamation.getDate_rec());
            dateLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size
            Label statutLabel = new Label("Statut: " + reclamation.getStatut());

            if (reclamation.getStatut().equals("non Traitee")) {
                statutLabel.setStyle("-fx-font-size: 14;  -fx-text-fill: red;"); // Adjust color and font size
            } else {

                statutLabel.setStyle("-fx-font-size: 14;  -fx-text-fill: green;"); // Adjust color and font size

            }

            Button reponseButton = new Button("Réponse");
            reponseButton.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: skyblue;");
            Button deleteButton = new Button("supprimer");
            deleteButton.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: red;");
            Button updateButton = new Button("modifier");
            updateButton.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: green;");
            if (reclamation.getStatut().equals("non Traitee")) {
                reponseButton.setDisable(true);
                deleteButton.setDisable(false);

                updateButton.setDisable(false);

            } else {
                deleteButton.setDisable(true);

                updateButton.setDisable(true);
            }
            reponseButton.setOnAction(event -> {
                ServiceReponse sp = new ServiceReponse();

                Reponse r = sp.recupererReponseParIdReclamation(reclamation.getId_reclamation());

                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Répondre à la réclamation");
                dialog.setHeaderText("Votre réponse et la date");

                // Ajouter les champs de texte à la boîte de dialogue
                TextArea reponseArea = new TextArea(r.getMessage_rep());
                reponseArea.setPromptText("Réponse");
                TextField dateField = new TextField(r.getDate_rep());
                dateField.setPromptText("Date");

                GridPane grid = new GridPane();
                grid.add(new Label("Réponse:"), 0, 0);
                grid.add(reponseArea, 1, 0); // Utiliser reponseArea à la place de reponseField
                grid.add(new Label("Date:"), 0, 1);
                grid.add(dateField, 1, 1);

                dialog.getDialogPane().setContent(grid);

                // Ajouter les boutons nécessaires (par exemple, OK et Annuler)
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

                // Afficher la boîte de dialogue et attendre la fermeture
                Optional<Pair<String, String>> result = dialog.showAndWait();

                // Traiter le résultat si présent
                result.ifPresent(reponseDatePair -> {
                    String reponse = reponseDatePair.getKey();
                    String date = reponseDatePair.getValue();

                    // Faire quelque chose avec la réponse et la date
                    // Par exemple, mettre à jour la base de données, afficher un message, etc.
                });
            });
            updateButton.setOnAction(event -> {
                // Créer la boîte de dialogue pour la mise à jour
                Dialog<Pair<String, String>> updateDialog = new Dialog<>();
                updateDialog.setTitle("Mettre à jour la réclamation");
                updateDialog.setHeaderText("Modifier le message et le type de réclamation");

                // Ajouter les champs de texte à la boîte de dialogue
                TextArea messageField = new TextArea(reclamation.getMessage());
                messageField.setPromptText("Message");

                TextField typeField = new TextField(reclamation.getType());
                typeField.setPromptText("Type de réclamation");

                GridPane updateGrid = new GridPane();
                updateGrid.add(new Label("Message:"), 0, 0);
                updateGrid.add(messageField, 1, 0);
                updateGrid.add(new Label("Type de réclamation:"), 0, 1);
                updateGrid.add(typeField, 1, 1);

                updateDialog.getDialogPane().setContent(updateGrid);

                // Ajouter les boutons nécessaires (par exemple, Modifier et Annuler)
                ButtonType updateButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
                updateDialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

                // Activer le bouton Modifier uniquement si les champs sont valides
                Node updateButtonNode = updateDialog.getDialogPane().lookupButton(updateButtonType);
                updateButtonNode.setDisable(true);

                // Validation des champs avant d'activer le bouton Modifier
                messageField.textProperty().addListener((observable, oldValue, newValue) -> {
                    updateButtonNode.setDisable(newValue.trim().isEmpty() || typeField.getText().trim().isEmpty());
                });

                typeField.textProperty().addListener((observable, oldValue, newValue) -> {
                    updateButtonNode.setDisable(newValue.trim().isEmpty() || messageField.getText().trim().isEmpty());
                });


                updateDialog.setResultConverter(dialogButton -> {
                    if (dialogButton == updateButtonType) {
                        return new Pair<>(messageField.getText(), typeField.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> updateResult = updateDialog.showAndWait();

                // Traiter le résultat de la mise à jour si présent
                updateResult.ifPresent(messageTypePair -> {
                    String updatedMessage = messageTypePair.getKey();
                    String updatedType = messageTypePair.getValue();
                    reclamation.setMessage(updatedMessage);
                    reclamation.setType(updatedType);
                    ServiceReclamation sr = new ServiceReclamation();
                    sr.updateReclamationParUser(reclamation);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Information");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Reclamation updated avec succès!");
                    alert1.showAndWait();
                    reclamationListView.refresh();
                    // Mettre à jour la base de données ou effectuer d'autres actions
                    // Utilisez les valeurs de updatedMessage et updatedType
                    // par exemple, serviceReclamation.update(reclamation.getId_reclamation(), updatedMessage, updatedType);
                });
            });


            deleteButton.setOnAction(event -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Voulez-vous vraiment supprimer ce Reclamation ?");
                alert.setContentText("Cliquez sur OK pour confirmer la suppression.");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // L'utilisateur a confirmé la suppression, vous pouvez maintenant supprimer la réservation.
                    ServiceReclamation bs = new ServiceReclamation();
                    bs.deleteReclamationParUSer(reclamation.getId_reclamation(), reclamation.getId());
                    // reclamationListView.getItems().remove(reclamation);
                    List<Reclamation> newList = new ArrayList<>(reclamationListView.getItems());
                    newList.remove(reclamation);
                    reclamationListView.setItems(FXCollections.observableList(newList));

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Information");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Reclamation supprimée avec succès!");
                    alert1.showAndWait();

                }
                reclamationListView.refresh();

            });
            VBox detailsBox = new VBox(5);
            detailsBox.getChildren().addAll(titleLabel, typeLabel, dateLabel, statutLabel);

            HBox cellContent = new HBox(10);
            cellContent.getChildren().addAll(imageView, detailsBox, reponseButton, deleteButton, updateButton);

            return cellContent;
        }

    }

    @FXML
    public void handleSearch(KeyEvent event) {
        // Retrieve the search term
        String searchTerm = filtre.getText().toLowerCase();

        // Create a predicate to filter by status
        Predicate<Reclamation> filterByStatut = reclamation -> {
            String statut = reclamation.getStatut().toLowerCase();
            return statut.contains(searchTerm);
        };

        // Apply the predicate to the FilteredList
        filteredReclamations.setPredicate(filterByStatut);
    }





    @FXML
    public void Back(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) filtre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/MenuSalma.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        };
    }

}








