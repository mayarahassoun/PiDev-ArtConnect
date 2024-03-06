package controllers;

import models.Reclamation;
import models.Reponse;
import services.ServiceReclamation;
import services.ServiceReponse;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageReclamationBack implements Initializable {


    @FXML
    private TableView<Reclamation> tableView;
    @FXML
    private TableColumn<String, String> message;
    @FXML
    private TableColumn<Reclamation, String> type;
    @FXML
    private TableColumn<String, String> userName;

    @FXML
    private TableColumn<Reclamation, String> statut;

    @FXML
    private TableColumn<String, String> date;

    ServiceReclamation a = new ServiceReclamation();
    public static  Reclamation pr ;
    @FXML
    private TableColumn<String, String> colname;
    @FXML
    private TextField filtre ;
    ObservableList< Reclamation> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
    }

    public void show() {
        ServiceReclamation a = new ServiceReclamation();
        obList = a.Read();
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        statut.setCellFactory(column -> new TableCell<Reclamation, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Assurez-vous que la cellule n'est pas vide
                if (!empty) {
                    setText(item);

                    // Changez la couleur du fond en fonction de la valeur de la colonne "mimetype"
                    if ("Traitee".equals(item)) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
                } else {
                    setText(null);
                    setStyle(null); // Remettre le style par défaut
                }
            }
        });
        userName.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
       // type.setCellValueFactory(new PropertyValueFactory<>("type"));

        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_rec"));


        tableView.setItems(obList);
    }
    Button btn;


    Button btnSupprimer;
    private Label label;

    private void showConfirmation(Reclamation pr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Reclamation ?");
        alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            this.label.setText("pas selection!");
        } else if (option.get() == ButtonType.OK) {
            System.out.println(" iddd=" + pr.getId_reclamation());
            a.delete(pr.getId_reclamation());
            obList.clear();

        } else if (option.get() == ButtonType.CANCEL) {
            this.label.setText("Exit!");
        } else {
            this.label.setText("-");
        }
    }

    @FXML

    void Supprimer(ActionEvent event) {
        Reclamation selectedLN =  tableView.getSelectionModel().getSelectedItem();
        if (selectedLN == null) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer un reclamation ");
            alert.setContentText("Veuillez selectionner une reclamation Ã  supprimer !");
            alert.showAndWait();
        }
        if(selectedLN != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce Reclamation ?");
            alert.setContentText("Cliquez sur OK pour confirmer la suppression.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la suppression, vous pouvez maintenant supprimer la réservation.
                ServiceReclamation bs = new ServiceReclamation();
                bs.delete(selectedLN.getId_reclamation());
                show(); // Peut-être une mise à jour de l'interface utilisateur après la suppression
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setHeaderText(null);
                alert1.setContentText("Reclamation supprimée avec succès!");
                alert1.showAndWait();
            }
        }

    }



    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboardS.fxml"));
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
        Reclamation selectedLN =  tableView.getSelectionModel().getSelectedItem();
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
            Dialog<Reclamation> dialog = new Dialog<>();
            dialog.setTitle("Modifier une Reclamtion");
            dialog.setHeaderText("Modifier le statut et faire la Reponse  Reclamation");

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


// Create a ToggleGroup for the radio buttons
            ToggleGroup toggleGroup = new ToggleGroup();

// Create radio buttons
            RadioButton treatedRadioButton = new RadioButton("Traitee");
            RadioButton notTreatedRadioButton = new RadioButton("Non traitee");

// Add radio buttons to the ToggleGroup
            treatedRadioButton.setToggleGroup(toggleGroup);
            notTreatedRadioButton.setToggleGroup(toggleGroup);

// Set default selection (if needed)
            toggleGroup.selectToggle(notTreatedRadioButton);

// Add a ChangeListener to the ToggleGroup
            toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    String newMessageText = (newToggle == treatedRadioButton) ? "Reclamation traitee" : "Reclamation non traitee";
                    nameInput.setPromptText(newMessageText);

                    // Update the statut in the selectedLN based on the selected radio button
                    selectedLN.setStatut((newToggle == treatedRadioButton) ? "Traitee" : "Non traitee");
                }
            });
            // Add radio buttons and other controls to the grid
            grid.add(new Label("Statut:"), 0, 2);
            grid.add(treatedRadioButton, 1, 2);
            grid.add(notTreatedRadioButton, 2, 2);

            grid.add(new Label("Reponse:"), 0, 0);
            grid.add(nameInput, 1, 0);


            dialog.getDialogPane().setContent(grid);

// Convert the result to a ReclamationUpdate
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {

                    return selectedLN;
                }
                return null;
            });

// Show the dialog and handle the result
            Optional<Reclamation> result = dialog.showAndWait();

            result.ifPresent(update -> {
                // Update the name, price, and category with the new values
                  selectedLN.setStatut(update.getStatut());

                // You can save the updated reclamation or perform other actions as needed
                ServiceReclamation reclamationService = new ServiceReclamation();
                reclamationService.modifierStatutReclamation(selectedLN.getId_reclamation(),selectedLN.getStatut(),selectedLN.getId());
                Date currentDate = new Date();

                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Format the date to a string
                String dateString = dateFormat.format(currentDate);
                ServiceReponse sp=new ServiceReponse();
                if(nameInput.getText().isEmpty())
                {
                    showAlert("WARNING", "Il faut saisi lA REPONSE!!");

                }
                else {
                    Reponse r = new Reponse(selectedLN.getId_reclamation(), nameInput.getText(), dateString);
                    sp.ajouterReponse(r, selectedLN.getId());
                    showAlert("Success", "Reponse Create avec succès!!!");

                }
                // Display a success message or refresh the UI
                showAlert("Success", "statut  du reclamation mis à jour avec succès");
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
//recherche par nom User or Statut Reclamation
    @FXML
    public void handleSearch(KeyEvent event) {
        String searchText = filtre.getText().trim();
        ObservableList<Reclamation> filteredList = FXCollections.observableArrayList();

        for (Reclamation b : obList) {
            // Rechercher par nom ou description ou statut
            if ((b.getNom().toLowerCase().contains(searchText.toLowerCase())) ||
                    (b.getStatut().equalsIgnoreCase(searchText))) {
                filteredList.add(b);
            }
        }

        tableView.setItems(filteredList);

        if (filteredList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation non trouvee ");
            alert.setHeaderText("Aucun Reclamation ne correspond à votre recherche");
            alert.setContentText("Veuillez essayer une autre recherche.");
            alert.showAndWait();
        }
    }
    }












