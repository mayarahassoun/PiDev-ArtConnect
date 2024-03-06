package services;

import interfaces.IReclamation;
import models.Reclamation;
import utils.ConnectionUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceReclamation  implements IReclamation {

    private Connection connection;
    public ServiceReclamation() {connection = (Connection) ConnectionUtil.conDB();}


    @Override
    public int Create(Reclamation r) {
        int result = 0;

        r.setId(15);
         String req = "INSERT INTO reclamation (message, type, statut,id_user,date_rec) VALUES (?, ?, ?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, r.getMessage());
            st.setString(2, r.getType());
            st.setString(3, r.getStatut());

            st.setInt(4,r.getId());
            st.setString(5, r.getDate_rec());


            result = st.executeUpdate();
            System.out.println("Reclamation ajouter avec success !");
             String to = "zouari.salma@esprit.tn"; // Get user email from database
                String subject = "Nouvelle reclamation ";
                String body = "Bonjour,\n\n Une nouvelle reclamation ajoute pour votre compte  Votre Message est :\n\n          "+r.getMessage()+" .\n\nCordialement,\nL'equipe de support";
                sendEmail(to, subject, body); // Call function to send email
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;
    }
  private void sendEmail(String to, String subject, String body) {
      String username = "farouksana6@gmail.com";
      String password = "lhwdtwizgzeeudfw";
      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com"); // Change this to your SMTP server host(yahoo...)
      props.put("mail.smtp.port", "587"); // Change this to your SMTP server port
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.setProperty("mail.smtp.starttls.enable", "true");
      props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
          protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
              return new javax.mail.PasswordAuthentication(username, password);
          }
      });


      try {
          // Create a MimeMessage object

          // Create a new message
          MimeMessage message = new MimeMessage(session);
          // Set the From, To, Subject, and Text fields of the message
          message.setFrom(new InternetAddress(username));
          message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
          message.setSubject(subject);
          message.setText(body);

          // Send the message using Transport.send
          Transport.send(message);

          System.out.println("Email sent successfully");
      } catch (MessagingException ex) {
          System.err.println("Failed to send email: " + ex.getMessage());
      }
  }




    @Override
    public int update(Reclamation R) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE reclamation SET message = ?, type = ?  WHERE id_reclamation = ?"
            );
            statement.setString(1, R.getMessage());
            statement.setString(2, R.getType());
            statement.setInt(3, R.getId_reclamation());

        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM reclamation WHERE id_reclamation = ?"
            );
            statement.setInt(1, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;

    }

    @Override
    public ObservableList<Reclamation> Read() {

        ObservableList<Reclamation> reclamations  = FXCollections.observableArrayList();

        String req = "SELECT r.*, u.firstname FROM reclamation r INNER JOIN users u ON r.id_user= u.id ";
        try {
            PreparedStatement st = connection.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Reclamation r = new Reclamation(
                        rs.getInt("id_reclamation"),
                        rs.getString("message"),
                        rs.getString("type"),
                        rs.getString("statut"),
                        rs.getInt("id_user"),
                        rs.getString("firstname"),
                        rs.getString("date_rec")

                );
                reclamations.add(r);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println(reclamations.toString());
        return reclamations;
    }

    @Override
    public List<Reclamation> afficherReclamationsParUser() {
        List<Reclamation> reclamations = new ArrayList<>();

        String req = "SELECT r.* FROM reclamation r INNER JOIN users u ON r.id_user = u.id WHERE u.id LIKE  ?";
        try {
            PreparedStatement st = connection.prepareStatement(req);
            st.setInt(1, 15);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Reclamation r = new Reclamation(
                        rs.getInt("id_reclamation"),
                        rs.getString("message"),
                        rs.getString("type"),
                        rs.getString("statut"),
                        rs.getInt("id_user"),
                        rs.getString("date_rec")
                );
                reclamations.add(r);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println(reclamations);
        return reclamations;
    }

    @Override
    public void modifierStatutReclamation(int idReclamation, String nouveauStatut, int idUser) {


        try {
            String query = "UPDATE reclamation r JOIN user u ON r.id_user = u.id SET r.statut = ? WHERE r.id_reclamation = ? and u.id=?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idReclamation);
            stmt.setInt(3, idUser);
            int rowsUpdated = stmt.executeUpdate();
             System.out.println("Statut de la reclamation modifer © avec succes ¨s !");

        } catch (SQLException ex) {

            System.out.println("Reclamation non trouveee !");
            System.err.println(ex.getMessage());
        }
    }

    public void updateReclamationParUser(Reclamation reclamation) {


        String query = "UPDATE reclamation SET message = ?, type = ? WHERE id_reclamation = ? AND id_user = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, reclamation.getMessage());
            st.setString(2, reclamation.getType());
            st.setInt(3, reclamation.getId_reclamation());
            st.setInt(4, reclamation.getId()); // Ajouter l'ID de l'utilisateur

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated > 0) {
                // La mise à jour a réussi
                System.out.println("Réclamation mise à jour avec succès");
            } else {
                // Aucune mise à jour effectuée (peut-être l'id_reclamation ou id_user n'existe pas)
                System.out.println("Aucune réclamation mise à jour");
            }
        } catch (SQLException ex) {
            // Gérer les exceptions liées à la base de données
            ex.printStackTrace();
        }
    }
    @Override
    public int deleteReclamationParUSer(int idReclamation,int idUser) {
        int result = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM reclamation  WHERE id_reclamation = ? AND id_user = ?"
            );
            statement.setInt(1, idReclamation);
            statement.setInt(2, idUser);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;

    }

}
