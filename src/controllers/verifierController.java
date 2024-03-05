package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class verifierController {

    private static final String ACCOUNT_SID = "AC5b0782d4325b2f664fd73e346c8dd9e5";
    private static final String AUTH_TOKEN = "7995c184a3dd57597f9578da829d480e";
    private static final String TWILIO_NUMBER = "+14157232928";

    @FXML
    private TextField txtphone;

    @FXML
    private TextField txtcode;

    @FXML
    private Button btncode;

    @FXML
    private Button btnsignup;

    @FXML
    private Button btnverifier;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet resultSet = null;
    private String generatedCode;

    public verifierController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    private void handleSendCode() {
        String phoneNumber = txtphone.getText();

        if (phoneNumberExists(phoneNumber)) {
            phoneNumber = "+216" + phoneNumber;
            generatedCode = generateCode(); // Assign the generated code to the class variable
            sendMessage(phoneNumber, "Your verification code is: " + generatedCode);
        } else {
            System.out.println("Phone number not found in the database.");
        }
    }

    private boolean phoneNumberExists(String phoneNumber) {
        try {
            String query = "SELECT COUNT(*) FROM users WHERE Phone = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String generateCode() {
        Random random = new Random();
        int codeLength = 6;
        StringBuilder sb = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private void sendMessage(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(TWILIO_NUMBER),
                        body)
                .create();
        System.out.println("Message SID: " + message.getSid());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
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

    @FXML
    private void handleVerifyCode() {
        String enteredCode = txtcode.getText();

        if (enteredCode.equals(generatedCode)) {
            System.out.println("Code is correct. Verification successful.");
            String phoneNumber = "+216" + txtphone.getText();
            updateIsVerifiedTo1(txtphone.getText());
        } else {
            System.out.println("Code is incorrect. Verification failed.");
        }
    }

    private void updateIsVerifiedTo1(String phoneNumber) {
        try {
            String query = "UPDATE users SET isVerified = 1 WHERE Phone = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("isVerified updated to 1 for user with phone number: " + phoneNumber);
            } else {
                System.out.println("No user found with phone number: " + phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
