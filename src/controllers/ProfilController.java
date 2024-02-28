package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.User;
import utils.ConnectionUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import utils.ConnectionUtil;

public class ProfilController implements Initializable {

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
        private Button btnSave;
        @FXML
        private Button btnDelete;
        @FXML
        private Button btnUpdate;
        @FXML
        private ComboBox<String> txtGender;
        @FXML
        Label lblStatus;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

        private User user;

    public  ProfilController(){

    }
    public void initData(User user) {
            this.user = user;
            if (user != null) {
                txtFirstname.setText(user.getFirstname());
                txtLastname.setText(user.getLastname());
                txtEmail.setText(user.getEmail());
                txtPassword.setText(user.getPassword());
                txtRole.setText(user.getRole());

            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
