package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.ConnectionUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

public class ForgetPasswordController {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField code;

    @FXML
    private PasswordField newpassword;

    @FXML
    private Button verifiercode;

    @FXML
    private Button btnSignup1;

    @FXML
    private Button resetpassword;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet resultSet = null;

    private String generatedCode;

    public ForgetPasswordController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    private void handleResetPassword() {
        String enteredCode = code.getText();


        if (enteredCode.equals(generatedCode)) {

            System.out.println("Code verification successful");

            updatePassword();
        } else {

            System.out.println("Code verification failed");
        }
    }

    @FXML
    private void handleSendCode() {
        String recipient = txtemail.getText();
        generatedCode = generateCode();

        boolean emailExists = checkEmailExists(recipient);
        if (emailExists) {
            // Email properties
            String host = "smtp.gmail.com";
            String port = "587";
            String username = "abdelmajidbenaissia@gmail.com";
            String password = "lqxb dotm nshf acfg";

            // Sender's email address
            String from = "abdelmajidbenaissia@gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {

                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

                message.setSubject("Password Reset Code");

                message.setText("Your verification code is: " + generatedCode);


                Transport.send(message);

                System.out.println("Sent message successfully....");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Email does not exist in the database.");
        }
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

    private boolean checkEmailExists(String email) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }


    private void updatePassword() {
        String newPassword = newpassword.getText();
        String email = txtemail.getText();

        try {

            String query = "UPDATE users SET Password = ? WHERE email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println("Password update successful");

            } else {

                System.out.println("Password update failed");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

