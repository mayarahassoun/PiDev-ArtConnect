package services;
import models.Event;
import models.Reservations;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class ResServices {

        private Connection connection;
    public ResServices () {

        connection = (Connection) ConnectionUtil.conDB();
    }
    public void addReservation(Reservations r) {
        String insertQuery = "INSERT INTO reservations (userId, idev, nbrP, email, num, etat) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Récupérer les détails de l'événement correspondant à l'ID de l'événement dans la réservation
            String selectEventQuery = "SELECT nombresP FROM event WHERE id = ?";
            PreparedStatement selectEventStatement = connection.prepareStatement(selectEventQuery);
            selectEventStatement.setInt(1, r.getIdevent());

            ResultSet resultSet = selectEventStatement.executeQuery();

            if (!resultSet.next()) {
                // L'ID de l'événement n'existe pas dans la table event
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("L'événement associé à cette réservation n'existe pas.");
                alert.showAndWait();
                return;
            }

            int nombresP = resultSet.getInt("nombresP");
            int capaciteMax = 50;

            // Vérifier si le nombre de personnes de la réservation dépasse la capacité maximale de l'événement
            if (nombresP + r.getNbrPerssone() > capaciteMax) {
                // Afficher un message d'erreur si le nombre de personnes dépasse la capacité maximale
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Désolé, l'événement est emailplet. Le nombre maximum de participants est atteint.");
                alert.showAndWait();
                return; // Retour prématuré pour éviter l'ajout de la réservation
            }

            // Insertion de la réservation
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, r.getUserId());
            insertStatement.setInt(2, r.getIdevent());
            insertStatement.setInt(3, r.getNbrPerssone());
            insertStatement.setString(4, r.getEmail());
            insertStatement.setInt(5, r.getNum_tel());
            insertStatement.setString(6, r.getEtat());
            insertStatement.executeUpdate();

            // Mise à jour du nombre de personnes pour l'événement correspondant
            String updateQuery = "UPDATE event SET nombresP = nombresP + ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, r.getNbrPerssone());
            updateStatement.setInt(2, r.getIdevent());
            updateStatement.executeUpdate();

            // Afficher un message de confirmation si la réservation est ajoutée avec succès
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("La réservation a été ajoutée avec succès.");
            confirmation.showAndWait();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }




    //        public void addReservation(Reservations r) {
//            String qry = "INSERT INTO `reservations` (`userId`,`idev`, `nbrP`, `email`, `num`, `etat`) VALUES (?,?,?,?, ?, ?)";
//            try {
//                PreparedStatement stm = connection.prepareStatement(qry);
//                stm.setInt(1, r.getUserId());
//                stm.setInt(2, r.getIdevent());
//                stm.setInt(3, r.getNbrPerssone());
//                stm.setString(4, r.getemail());
//                stm.setInt(5, r.getNum_tel());
//                stm.setString(6, r.getEtat());
//
//                stm.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
    public List<Reservations> getReservation() {
        String etat = "en attente";
        String req1 = "SELECT * FROM reservations WHERE etat = '" + etat + "'";
        List<Reservations> reservations = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(req1);

            while (rs.next()) {
                Reservations r = new Reservations();
                r.setIdreservation(rs.getInt("idreserv"));
                r.setUserId(rs.getInt("userId"));
                r.setIdevent(rs.getInt("idev"));
                r.setNbrPerssone(rs.getInt("nbrP"));
                r.setEmail(rs.getString("email"));
                r.setNum_tel(rs.getInt("num"));

                reservations.add(r);

                System.out.println("the event is: " + r.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
    }
    public List<Reservations> getReservationAccepter() {
        String etat = "accepter";
        String req1 = "SELECT * FROM reservations WHERE etat = '" + etat + "'";
        List<Reservations> reservations = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(req1);

            while (rs.next()) {
                Reservations r = new Reservations();
                r.setIdreservation(rs.getInt("idreserv"));
                r.setUserId(rs.getInt("userId"));
                r.setIdevent(rs.getInt("idev"));
                r.setNbrPerssone(rs.getInt("nbrP"));
                r.setEmail(rs.getString("email"));
                r.setNum_tel(rs.getInt("num"));

                reservations.add(r);

                System.out.println("the event is: " + r.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
    }
    public void modifierEtatReservation(int idReservation, String nouvelEtat) {
        // Construction de la requête SQL
        String reqModification = "UPDATE reservations SET etat = ? WHERE idreserv = ?";

        try {
            // Préparation de la requête avec un PreparedStatement pour éviter les attaques par injection SQL
            PreparedStatement statement = connection.prepareStatement(reqModification);
            statement.setString(1, nouvelEtat);
            statement.setInt(2, idReservation);

            // Exécution de la requête
            int lignesModifiees = statement.executeUpdate();

            // Vérification du nombre de lignes modifiées
            if (lignesModifiees > 0) {
                System.out.println("La réservation avec l'identifiant " + idReservation + " a été mise à jour avec succès.");
            } else {
                System.out.println("Aucune réservation n'a été mise à jour. Vérifiez l'identifiant de la réservation.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la réservation : " + e.getMessage());
        }
    }
    public List<Reservations> afficherReservations(int userId) {
        String req1 = "SELECT * FROM reservations WHERE userId = ?";
        List<Reservations> reservations = new ArrayList<>();

        try {
            // Préparation de la requête avec un PreparedStatement pour éviter les attaques par injection SQL
            PreparedStatement stm = connection.prepareStatement(req1);
            stm.setInt(1, userId); // Fournir la valeur de userId pour le paramètre dans la requête
            ResultSet rs = stm.executeQuery();

            // Parcours des résultats et ajout des réservations à la liste
            while (rs.next()) {
                Reservations r = new Reservations();
                r.setIdreservation(rs.getInt("idreserv"));
                r.setUserId(rs.getInt("userId"));
                r.setIdevent(rs.getInt("idev"));
                r.setNbrPerssone(rs.getInt("nbrP"));
                r.setEmail(rs.getString("email"));
                r.setNum_tel(rs.getInt("num"));
                r.setEtat(rs.getString("etat"));

                reservations.add(r);

                System.out.println("the event is: " + r.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reservations;
    }
     public void updateReservation(Reservations r) {
        String qry = "UPDATE reservations SET userId=?, idev=?, nbrP=?, email=?, num=? WHERE idreserv=?";
        try {
            PreparedStatement stm = connection.prepareStatement(qry);
            stm.setInt(1, r.getUserId());
            stm.setInt(2, r.getIdevent());
            stm.setInt(3, r.getNbrPerssone());
            stm.setString(4, r.getEmail());
            stm.setInt(5, r.getNum_tel());
           // stm.setString(6, r.getEtat());
            stm.setInt(6, r.getIdreservation()); // Utilisez r.getIdreservation() pour définir l'ID de la réservation
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void deleteReservation(int reservationId) {
        try {
            // Supprimer la réservation spécifique
            String deleteReservationQuery = "DELETE FROM reservations WHERE idreserv=?";
            PreparedStatement deleteReservationStatement = connection.prepareStatement(deleteReservationQuery);
            deleteReservationStatement.setInt(1, reservationId);
            deleteReservationStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
    // Méthode pour vérifier si une réservation avec les mêmes données existe déjà
    public boolean reservationExists(Reservations reservation) {
        try {
            // Effectuer une requête à votre base de données pour vérifier l'existence de la réservation
            String query = "SELECT COUNT(*) FROM reservations WHERE userId = ? AND idev = ? AND nbrP = ? AND email = ? AND num = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getIdevent());
            statement.setInt(3, reservation.getNbrPerssone());
            statement.setString(4, reservation.getEmail());
            statement.setInt(5, reservation.getNum_tel());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Si le nombre de réservations existantes est supérieur à 0, cela signifie qu'une réservation identique existe déjà
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Si une exception se produit ou si aucun enregistrement n'est trouvé, retourner false
    }
    // Dans la classe ResServices
    public boolean isCapacityExceeded(Reservations r) {
        try {
            String selectEventQuery = "SELECT nombresP FROM event WHERE id = ?";
            PreparedStatement selectEventStatement = connection.prepareStatement(selectEventQuery);
            selectEventStatement.setInt(1, r.getIdevent());

            ResultSet resultSet = selectEventStatement.executeQuery();

            if (resultSet.next()) {
                int nombresP = resultSet.getInt("nombresP");
                int capaciteMax = 50;
                return (nombresP + r.getNbrPerssone() > capaciteMax);
            } else {
                // L'ID de l'événement n'existe pas dans la table event
                return true; // ou false selon votre logique de gestion d'erreur
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}








