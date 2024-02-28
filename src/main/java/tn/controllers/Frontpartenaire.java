package tn.controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import tn.models.Partenaire;
import tn.services.partenaireServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Frontpartenaire implements Initializable {

    @FXML
    private AnchorPane anchorPaneE1;

    @FXML
    private TextField filtre;

    @FXML
    private ListView<Partenaire> partenaireview;

    @FXML
    void Back(ActionEvent event) {

    }

    @FXML
    void handleSearch(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    partenaireview.setCellFactory(new PartenaireCellFactory());

        partenaireServices p =new partenaireServices();

       List<Partenaire> partenaire =p.displayPartenaire() ;

        // Set the items in the ListView
    partenaireview.getItems().addAll(partenaire);





    }

    public class PartenaireCellFactory implements Callback<ListView<Partenaire>, ListCell<Partenaire>> {

        @Override
        public ListCell<Partenaire> call(ListView<Partenaire> param) {
            return new ListCell<Partenaire>() {

                @Override
                protected void updateItem(Partenaire item, boolean empty) {
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

        private HBox createCellContent(Partenaire partenaire) {

            Label titleLabel = new Label("Réclamation Details:");
            titleLabel.setStyle("-fx-font-size: 16; -fx-text-fill: blue;"); // Adjust color and font size
            Label typeLabel = new Label("Type:" + partenaire.getType());
            typeLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size

            Label dateLabel = new Label("DateDebut:" + partenaire.getDateDebut());
            dateLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size
            Label statutLabel = new Label("Name: " + partenaire.getName());

            if (partenaire.getName().equals("non Traitee")) {
                statutLabel.setStyle("-fx-font-size: 14;  -fx-text-fill: red;"); // Adjust color and font size
            } else {

                statutLabel.setStyle("-fx-font-size: 14;  -fx-text-fill: green;"); // Adjust color and font size

            }



            VBox detailsBox = new VBox(5);
            detailsBox.getChildren().addAll(titleLabel, typeLabel, dateLabel, statutLabel);

            HBox cellContent = new HBox(10);

            return cellContent;
        }

    }
}



