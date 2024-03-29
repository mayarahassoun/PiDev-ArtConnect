
package controllers;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author oXCToo
 */
public class HomeController implements Initializable {

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
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btn_retour;
    @FXML
    private ComboBox<String> txtGender;
    @FXML
    Label lblStatus;

    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    TableView tblData;

    PreparedStatement preparedStatement;
    Connection connection;

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtGender.getItems().addAll("Male", "Female", "Other");
        txtGender.getSelectionModel().select("Male");
        fetColumnList();
        fetRowList();

        tblData.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableViewSelection();
            }
        });

    }

    @FXML
    void btn_retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu1.fxml"));
            Parent root  = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the stage from the event source
            stage.setScene(new Scene(root)); // Set the new scene on the stage
            stage.show(); // Show the stage
        } catch (Exception ex) {
            ex.printStackTrace(); // Keep this for debugging, but consider using a proper error handling mechanism
        }
    }
    @FXML
    private void HandleEvents(MouseEvent event) {
        if (!isValidName(txtFirstname.getText())) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("First name should contain only letters");
        } else if (!isValidName(txtLastname.getText())) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Last name should contain only letters");
        } else if (txtPassword.getText().length() < 8) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Password should be 8 characters or more");
        } else if (!isValidEmail(txtEmail.getText())) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter a valid email address");
        } else {
            saveData();
        }
    }

    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();
        txtRole.clear();
        txtPassword.clear();
        txtPhone.clear();
        txtAddress.clear();

    }

    private String saveData() {

        try {
            String sql = "INSERT INTO Users (email, dob, gender, lastname, firstname, password, image, role,Phone,address) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, txtEmail.getText());
            preparedStatement.setString(2, txtDOB.getValue().toString());;
            preparedStatement.setString(3, txtGender.getValue().toString());
            preparedStatement.setString(4, txtLastname.getText());
            preparedStatement.setString(5, txtFirstname.getText());
            preparedStatement.setString(6, txtPassword.getText());
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, txtRole.getText());
            preparedStatement.setInt(9, Integer.parseInt(txtPhone.getText()));
            preparedStatement.setString(10, txtAddress.getText());


            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");

            fetRowList();
            fetRowList();
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    private ObservableList<ObservableList> data;
    String SQL ="SELECT email, dob, gender, lastname, firstname, password, image, role, Phone, address FROM users";

    //nekhou ken lcolumns
    private void fetColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j) != null ? param.getValue().get(j).toString() : "");
                    }
                });

                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //nekhou data mil list
    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                // Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void handleTableViewSelection() {
        ObservableList selectedRow = (ObservableList) tblData.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            txtFirstname.setText(selectedRow.get(4).toString());
            txtLastname.setText(selectedRow.get(3).toString());
            txtEmail.setText(selectedRow.get(0).toString());
            txtPassword.setText(selectedRow.get(5).toString());
            txtRole.setText(selectedRow.get(7).toString());
            txtPhone.setText(selectedRow.get(8).toString());
            txtAddress.setText(selectedRow.get(9).toString());



        }
    }

    @FXML
    private void handleDeleteButton() {
        ObservableList selectedRow = (ObservableList) tblData.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            String userEmail = selectedRow.get(0).toString();
            deleteData(userEmail);
        } else {

        }
    }

    private void deleteData(String userEmail) {
        try {
            String sql = "DELETE FROM Users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("User deleted successfully");
                fetRowList();
            } else {
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("Failed to delete user");
            }
        } catch (SQLException ex) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }
    private int getUserIdByEmail(String email) {
        int userId = -1;
        try {
            String sql = "SELECT id FROM Users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return userId;
    }
    @FXML
    private void handleUpdateButton() {
        ObservableList selectedRow = (ObservableList) tblData.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            String userEmail = txtEmail.getText();
            int userId = getUserIdByEmail(userEmail);
            if (userId != -1) {

                String newFirstname = txtFirstname.getText();
                String newLastname = txtLastname.getText();
                String newPassword = txtPassword.getText();
                String newRole = txtRole.getText();
                int newPhone = Integer.parseInt(txtPhone.getText());
                String newAddress = txtAddress.getText();
                String userIdString = String.valueOf(userId);

                updateData(userIdString, newFirstname, newLastname, userEmail, newPassword, newRole, newPhone, newAddress);
            } else {
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("User not found with provided email");
            }
        } else {
            // Handle if no row is selected
        }
    }


    private void updateData(String userId, String newFirstname, String newLastname, String newEmail, String newPassword, String newRole, int newPhone, String newAddress) {
        try {
            String sql = "UPDATE Users SET firstname = ?, lastname = ?, email = ?, password = ?, role = ?, Phone = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newFirstname);
            preparedStatement.setString(2, newLastname);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setString(4, newPassword);
            preparedStatement.setString(5, newRole);
            preparedStatement.setInt(6, newPhone);
            preparedStatement.setString(7, newAddress);
            preparedStatement.setString(8, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("User updated successfully");
                fetRowList(); // Refresh the TableView after update
            } else {
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("Failed to update user");
            }
        } catch (SQLException ex) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }


    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidEmail(String email) {

        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}
