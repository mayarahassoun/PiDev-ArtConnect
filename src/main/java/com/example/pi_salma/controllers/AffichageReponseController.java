package com.example.pi_salma.controllers;

import com.example.pi_salma.models.Reponse;
import com.example.pi_salma.models.Reponse;
import com.example.pi_salma.models.Reponse;
import com.example.pi_salma.services.ServiceReponse;
import com.example.pi_salma.services.ServiceReponse;
import com.example.pi_salma.services.ServiceReponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageReponseController implements Initializable {


    @FXML
    private TableView<Reponse> tableView;
    @FXML
    private TableColumn<String, String> message;
    @FXML
    private TableColumn<String, String> type;
    @FXML
    private TableColumn<String, String> userName;

    @FXML
    private TableColumn<String, String> date;



    ServiceReponse a = new ServiceReponse();
    public static  Reponse pr ;

    @FXML
    private TextField filtre ;
    ObservableList< Reponse> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
    }

    public void show() {
        ServiceReponse a = new ServiceReponse();
        obList = a.afficherTousReponses();

        userName.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        message.setCellValueFactory(new PropertyValueFactory<>("message_rep"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeReclamation"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_rep"));


        tableView.setItems(obList);
    }


    @FXML

    void Supprimer(ActionEvent event) {
        Reponse selectedLN =  tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer un reponse ");
            alert.setContentText("Veuillez selectionner une reponse Ã  supprimer !");
            alert.showAndWait();
        }
        if(selectedLN != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce Reponse ?");
            alert.setContentText("Cliquez sur OK pour confirmer la suppression.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression, vous pouvez maintenant supprimer la réservation.
                ServiceReponse bs = new ServiceReponse();
                bs.supprimerReponse(selectedLN);
                show(); // Peut-être une mise à jour de l'interface utilisateur après la suppression
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setHeaderText(null);
                alert1.setContentText("Reponse supprimée avec succès!");
                alert1.showAndWait();
            }
        }

    }



    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/pi_salma/Admin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        };
    }


    @FXML
    void modifer(ActionEvent event) {
        Reponse selectedLN =  tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de modifier");
            alert.setContentText("Veuillez selectionner pour modifier !");
            alert.showAndWait();
        }

        else {
// Show input dialogs to get the new event details.
// Create a custom dialog
            Dialog<Reponse> dialog = new Dialog<>();
            dialog.setTitle("Modifier La Reponse");
            dialog.setHeaderText("Modifier le Messsage   Reponse");

// Set the button types (OK and Cancel)
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

// Create the content of the dialog
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

// Add text input for name, category, and price
            TextArea nameInput = new TextArea("");
            nameInput.setPromptText("Nouveau Reponse");
            grid.add(new Label("Reponse:"), 0, 0);
            grid.add(nameInput, 1, 0);

            dialog.getDialogPane().setContent(grid);

// Convert the result to a ReponseUpdate
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    selectedLN.setMessage_rep(nameInput.getText());

                    return selectedLN;
                }
                return null;
            });

// Show the dialog and handle the result
            Optional<Reponse> result = dialog.showAndWait();

            result.ifPresent(update -> {
                // Update the name, price, and category with the new values
                selectedLN.setStatut(update.getMessage_rep());

                // You can save the updated reponse or perform other actions as needed
                ServiceReponse reponseService = new ServiceReponse();
                reponseService.modifierReponse(selectedLN);


                // Display a success message or refresh the UI
                showAlert("Success", "statut  du reponse mis à jour avec succès");
            });

            tableView.refresh();
        }}
    private void showAlert(String title, String message) {
        if (title.equals("Success")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtre.getText().trim();
            if (searchText.isEmpty()) {
                tableView.setItems(obList);
            } else {
                ObservableList<Reponse> filteredList = FXCollections.observableArrayList();
                boolean reponseFound = false;
                for (Reponse b : obList) {
                    // search for name or description
                    if ((b.getNomUser().toLowerCase().contains(searchText.toLowerCase())))
                    {
                        filteredList.add(b);
                        reponseFound = true;
                    }
                }
                if (!reponseFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reponse non trouvee ");
                    alert.setHeaderText("Aucun Reponse ne correspond � votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                tableView.setItems(filteredList);
            }
        }
    }
}
