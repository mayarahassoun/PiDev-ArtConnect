package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController {

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;;
    @FXML
    private DatePicker txtDOB;
    @FXML
    private TextField txtRole;


    @FXML
    private ComboBox<String> txtGender;

    @FXML
    private ImageView uploadedImageView;
    String imagePath;
    PreparedStatement preparedStatement;
    Connection connection;

    public RegistrationController() {
        connection = (Connection) ConnectionUtil.conDB();
    }
    public void initialize(URL url, ResourceBundle rb) {

        txtGender.getItems().addAll("Male", "Female", "Other");

        txtGender.getSelectionModel().select("Male");
    }
    @FXML
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Store image path in class-level variable
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            uploadedImageView.setImage(image);
        }
    }


    @FXML
    private void handleRegisterButton(ActionEvent event) {
        String firstname = txtFirstname.getText();
        String lastname = txtLastname.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String dob = txtDOB.getValue() != null ? txtDOB.getValue().toString() : null;
        String gender = txtGender.getValue() != null ? txtGender.getValue().toString() : null;

        if (!isValidName(firstname)) {
            showAlert(Alert.AlertType.ERROR, "Error", "First name should contain only letters.");
        } else if (!isValidName(lastname)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Last name should contain only letters.");
        } else if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password should be 8 characters or more.");
        } else if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Enter a valid email address.");
        } else if (dob == null || gender == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
        } else {
            String sql = "INSERT INTO Users (email, dob, gender, lastname, firstname, password, image, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, txtEmail.getText());
                preparedStatement.setString(2, txtDOB.getValue().toString());
                preparedStatement.setString(3, txtGender.getValue().toString());
                preparedStatement.setString(4, txtLastname.getText());
                preparedStatement.setString(5, txtFirstname.getText());
                preparedStatement.setString(6, txtPassword.getText());

                preparedStatement.setString(7, imagePath);
                preparedStatement.setString(8, txtRole.getText());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register user.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while registering the user.");
            }
        }
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidEmail(String email) {

        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    @FXML
    private void handleSignupButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent registerParent = loader.load();
            Scene loginScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) loginScene.getWindow();
            Scene registerScene = new Scene(registerParent);
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

