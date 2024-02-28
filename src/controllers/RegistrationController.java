package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.ConnectionUtil;

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
    private DatePicker txtCreation;
    @FXML
    private TextField txtRole;


    @FXML
    private ComboBox<String> txtGender;



    PreparedStatement preparedStatement;
    Connection connection;

    public RegistrationController() {
        connection = (Connection) ConnectionUtil.conDB();
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtGender.getItems().addAll("Male", "Female", "Other");
        txtGender.getSelectionModel().select("Male");
    }
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        String firstname = txtFirstname.getText();
        String lastname = txtLastname.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String dob = txtDOB.getValue().toString();
        String gender = txtGender.getValue();

        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        // Perform registration
        String sql = "INSERT INTO Users (email, dob, gender, lastname, firstname, password, image, role) VALUES (?,?,?,?,?,?,?,?) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, txtEmail.getText());
            preparedStatement.setString(2, txtDOB.getValue().toString());;
            preparedStatement.setString(3, txtGender.getValue().toString());
            preparedStatement.setString(4, txtLastname.getText());
            preparedStatement.setString(5, txtFirstname.getText());
            preparedStatement.setString(6, txtPassword.getText());
            preparedStatement.setString(7, null);
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

