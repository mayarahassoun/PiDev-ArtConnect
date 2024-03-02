//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.User;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
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
    @FXML
    private ImageView profileImage;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private User user;

    public ProfilController() {
    }
    public void initialize(URL url, ResourceBundle rb) {
    }

        public void initData(User user) {
                this.user = user;
                if (user != null) {
                    this.txtFirstname.setText(user.getFirstname());
                    this.txtLastname.setText(user.getLastname());
                    this.txtEmail.setText(user.getEmail());
                    this.txtPassword.setText(user.getPassword());
                    this.txtRole.setText(user.getRole());

                    String imagePath = user.getImage();
                    try {
                        // Create a URL object from the modified image path
                        URL url = new URL(imagePath);

                        // Convert the URL to a string and create an Image object
                        Image image = new Image(url.toString());

                        // Set the image to the ImageView
                        profileImage.setImage(image);
                    } catch (MalformedURLException e) {
                        System.err.println("Error loading image: " + e.getMessage());
                    }
                }
        }
    }
