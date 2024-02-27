package com.example.pi_salma.services;

import com.example.pi_salma.interfaces.IReponse;
import com.example.pi_salma.models.Reclamation;
import com.example.pi_salma.models.Reponse;
import com.example.pi_salma.utils.Connexion_database;
import com.twilio.Twilio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;

 import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceReponse implements IReponse<Reponse> {
    private Connection connection;
    public ServiceReponse() {
        try {
            this.connection = Connexion_database.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        @Override
        public void ajouterReponse(Reponse reponse,int to) {
            String req = "INSERT INTO reponse (id_reclamation, message_rep, date_rep) VALUES (?, ?, ?)";
            try {
                PreparedStatement st = connection.prepareStatement(req);
                st.setInt(1, reponse.getId_reclamation());
                st.setString(2, reponse.getMessage_rep());
                st.setString(3, reponse.getDate_rep());
                st.executeUpdate();
                System.out.println("Reponse ajoutÃ©e avec succÃ¨s !");

                // Send SMS to user
                String accountSid = "AC5f7714ef2cabf2fc1af93cb5296cd871";
                String authToken = "3f183683a03d0a433b5332ef9dd17eaa";
                Twilio.init(accountSid, authToken);
                String fromPhoneNumber = "+14092150103"
                        ; // Change this to your Twilio phone number
                String toPhoneNumber = "+21695220959"; // Change this to the user's phone number
                String smsBody = "Bonjour, une nouvelle réponse a été ajoutée à votre réclamation. Cordialement, L'équipe de support";
                Message message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), smsBody).create();
                System.out.println("SMS sent successfully to " + toPhoneNumber);
            } catch (SQLException ex) {

            }
        }








        private String getUserEmail(int id_reclamation) {
            String email = null;

            String req = "SELECT email FROM utilisateur WHERE id_reclamation = ?";
            try {
                PreparedStatement st = connection.prepareStatement(req);
                st.setInt(1, id_reclamation);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    email = rs.getString("email");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            return email;
        }

        @Override
        public void supprimerReponse(Reponse reponse) {
            String req = "DELETE FROM reponse WHERE id_reclamation = ?";
            try {
                PreparedStatement st = connection.prepareStatement(req);
                st.setInt(1, reponse.getId_reclamation());
                st.executeUpdate();
                System.out.println("RÃ©ponse supprimÃ©e avec succÃ¨s !");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        @Override
        public void modifierReponse(Reponse reponse) {
            String req = "UPDATE reponse SET message_rep = ?, date_rep = ? WHERE id_reclamation = ?";
            try {
                PreparedStatement st = connection.prepareStatement(req);
                st.setString(1, reponse.getMessage_rep());
                st.setString(2, reponse.getDate_rep());
                st.setInt(3, reponse.getId_reclamation());
                st.executeUpdate();
                System.out.println("RÃ©ponse modifiÃ©e avec succÃ¨s !");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        @Override

             public ObservableList<Reponse> afficherTousReponses() {
                ObservableList<Reponse> listeReponses= FXCollections.observableArrayList();
            String req = "SELECT us.nom, u.type, r.*, u.statut FROM reponse r LEFT JOIN reclamation u ON r.id_reclamation = u.id_reclamation LEFT JOIN user us ON u.id_user = us.Id";

            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    Reponse reponse = new Reponse(
                            rs.getInt("id_reponse"),
                            rs.getInt("id_reclamation"),
                            rs.getString("message_rep"),
                            rs.getString("date_rep"),
                            rs.getString("statut"),
                             rs.getString("nom"),
                            rs.getString("type")
                    );

                    listeReponses.add(reponse);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            System.err.println(listeReponses);
            return listeReponses;
        }

        @Override
        public ObservableList<Reponse> afficherReponsesParReclamation(int id_reclamation) {
            ObservableList<Reponse> listeReponses  = FXCollections.observableArrayList();
            String req = "SELECT * FROM reponse WHERE id_reclamation = ?";
            try {
                PreparedStatement st = connection.prepareStatement(req);
                st.setInt(1, id_reclamation);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Reponse reponse = new Reponse(
                            rs.getInt("id_reponse"),
                            rs.getInt("id_reclamation"),
                            rs.getString("message_rep"),
                            rs.getString("date_rep")
                    );
                    listeReponses.add(reponse);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            return listeReponses;
        }
    @Override
    public Reponse recupererReponseParIdReclamation(int idReclamation) {
        System.out.println("entrzzz");

        String req = "SELECT * FROM reponse WHERE id_reclamation = ?";
        try {
            System.out.println(req);

            PreparedStatement st = connection.prepareStatement(req);
            st.setInt(1, idReclamation);
            System.out.println(idReclamation);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                    Reponse r=new Reponse(
                        rs.getInt("id_reponse"),
                        rs.getInt("id_reclamation"),
                        rs.getString("message_rep"),
                        rs.getString("date_rep")
                );
                System.out.println("//////////"+r);

                return r;

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null; // Aucune réponse trouvée
    }


}
