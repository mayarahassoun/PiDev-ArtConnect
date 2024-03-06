package controllers;


import home.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import models.Entreprise;
import services.EntrepriseService;
import models.OffreDemploi;
import services.OffreDemploiService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class AfficherOffreDemploi {

    public TextField rechercherTF;
    @FXML
    private TableColumn<OffreDemploi, String> titreCol;

    @FXML
    private TableColumn<OffreDemploi, String> descriptionCol;

    @FXML
    private TableColumn<OffreDemploi, Float> salaireCol;

    @FXML
    private TableColumn<OffreDemploi, String> statusCol;

    @FXML
    private TableColumn<OffreDemploi, String> entrepriseCol;

    @FXML
    private TableView<OffreDemploi> tableView;

    @FXML
    private TextField titreTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField salaireTF;

    @FXML
    private ComboBox<String> statusCB;

    @FXML
    private ComboBox<String> entrepriseCB;

    private final OffreDemploiService offreDemploiService = new OffreDemploiService();
    private final EntrepriseService entrepriseService = new EntrepriseService();

    @FXML
    void ajouterOffreDemploi(ActionEvent event) {
        // Navigation vers la scène d'ajout d'offre d'emploi
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterOffreDemploi.fxml"));
        try {
            titreTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void modifierOffreDemploi(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (verifierChamps()) {
            OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
            if (selectedOffre != null) {
                // Mettre à jour les données de l'offre avec celles des champs de texte
                selectedOffre.setTitre(titreTF.getText());
                selectedOffre.setDescription(descriptionTF.getText());
                selectedOffre.setSalaire(Float.parseFloat(salaireTF.getText()));
                selectedOffre.setStatus(statusCB.getValue());

                // Récupérer l'entreprise sélectionnée depuis la ComboBox
                String nomEntreprise = entrepriseCB.getValue();
                if (nomEntreprise != null && !nomEntreprise.isEmpty()) {
                    try {
                        // Récupérer l'entreprise par son nom depuis la base de données
                        Entreprise entreprise = entrepriseService.getEntrepriseByNom(nomEntreprise);
                        if (entreprise != null) {
                            // Mettre à jour l'identifiant de l'entreprise dans l'offre d'emploi
                            selectedOffre.setIdEntreprise(entreprise.getId());
                        }
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de la récupération de l'entreprise : " + e.getMessage());
                    }
                }

                // Mettre à jour l'offre dans la base de données
                try {
                    offreDemploiService.modifier(selectedOffre);
                    tableView.refresh(); // Rafraîchir la TableView
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la modification de l'offre d'emploi : " + e.getMessage());
                }
            }
        } else {
            // Afficher un message d'erreur si tous les champs ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
    }

    // Méthode pour vérifier si tous les champs sont remplis
    private boolean verifierChamps() {
        return !titreTF.getText().isEmpty() &&
                !descriptionTF.getText().isEmpty() &&
                !salaireTF.getText().isEmpty() &&
                statusCB.getValue() != null &&
                entrepriseCB.getValue() != null;
    }


    @FXML
    void supprimerOffreDemploi(ActionEvent event) {
        OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            // Supprimer l'offre de la base de données
            try {
                offreDemploiService.supprimer(selectedOffre.getId());
                tableView.getItems().remove(selectedOffre); // Retirer l'offre de la TableView
            } catch (SQLException e) {
                System.err.println("Erreur lors de la suppression de l'offre d'emploi : " + e.getMessage());
            }
        }
    }

    @FXML
    void initialize() {
        try {

            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaire"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
            entrepriseCol.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));

            // Récupérer les noms des entreprises et les ajouter à la ComboBox
            List<String> nomsEntreprises = entrepriseService.recupererNomsEntreprises();
            ObservableList<String> observableNomsEntreprises = FXCollections.observableList(nomsEntreprises);
            entrepriseCB.setItems(observableNomsEntreprises);

            // Initialisation de la TableView
            List<OffreDemploi> offres = offreDemploiService.recuperer();
            ObservableList<OffreDemploi> observableList = FXCollections.observableList(offres);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        ObservableList<String> statutsList = FXCollections.observableArrayList(
                "En attente", "Publiée", "Clôturée");
        statusCB.setItems(statutsList);





        rechercherTF.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherOffreDemploiParTitre(newValue);
        });

    }
    private void rechercherOffreDemploiParTitre(String titreRecherche) {
        try {
            if (titreRecherche.isEmpty()) {
                afficherOffres();
            } else {

                List<OffreDemploi> offres = offreDemploiService.rechercherParTitre(titreRecherche);
                ObservableList<OffreDemploi> observableList = FXCollections.observableList(offres);
                tableView.setItems(observableList);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des offres d'emploi par titre : " + e.getMessage());
        }
    }

    private void afficherOffres() {
        try {
            List<OffreDemploi> offres = offreDemploiService.recuperer();
            ObservableList<OffreDemploi> observableList = FXCollections.observableList(offres);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void handleTableRowClicked(MouseEvent event) throws SQLException {
        OffreDemploi selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            titreTF.setText(selectedOffre.getTitre());
            descriptionTF.setText(selectedOffre.getDescription());
            salaireTF.setText(String.valueOf(selectedOffre.getSalaire()));
            statusCB.setValue(selectedOffre.getStatus());
            try {
                entrepriseCB.setValue(selectedOffre.getNomEntreprise());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void retourBT(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AjouterOffreDemploi.fxml"));
        try {
            titreTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void exportExcelClicked(MouseEvent mouseEvent) {
        try {
            List<OffreDemploi> offres = offreDemploiService.recuperer();

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Offres d'emploi");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Titre", "Description", "Salaire", "Status", "ID Entreprise"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (OffreDemploi offre : offres) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(offre.getId());
                row.createCell(1).setCellValue(offre.getTitre());
                row.createCell(2).setCellValue(offre.getDescription());
                row.createCell(3).setCellValue(offre.getSalaire());
                row.createCell(4).setCellValue(offre.getStatus());
                row.createCell(5).setCellValue(offre.getIdEntreprise());
            }

            // Auto size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Show file chooser dialog to choose file location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(titreTF.getScene().getWindow());

            if (file != null) {
                // Write the workbook to the chosen file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                    System.out.println("Excel file exported successfully.");
                }
            }

            // Close the workbook
            workbook.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
