package controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.User;
import utils.ConnectionUtil;

/**
 *
 * @author oXCToo
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    /// --
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignin) {
            // Login
            String loginStatus = logIn();
            if (loginStatus.equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();

                    // Check role
                    String role = getRole(txtUsername.getText());
                    if (role.equals("user")) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setScene(scene);
                            ProfilController controller = loader.getController();
                            controller.initData(getUser(txtUsername.getText()));
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }

                    } else if (role.equals("admin")) {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml")));
                        stage.setScene(scene);
                    }
                    stage.show();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }

    //lblerror bch necheki beha
    private String logIn() {
        String status = "Success";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM users Where email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }
    private String getRole(String email) {
        String role = "";
        String sql = "SELECT role FROM users WHERE email = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return role;
    }
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
    private User getUser(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                String dob = resultSet.getString("dob");
                String role = resultSet.getString("role");
                String gender = resultSet.getString("gender");

                user = new User(id, userEmail, dob, gender, lastname, firstname, null, password, null, role);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return user;
    }
    @FXML
    private void handleSignupButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
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


}
