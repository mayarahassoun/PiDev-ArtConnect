package tn.services;


import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.models.Partenaire;
import tn.utils.MyDatabase;


public class partenaireServices {
    private Connection connection;

    public partenaireServices() {
        connection = MyDatabase.getInstance().getConnection();
    }


    public boolean addPartenaire(Partenaire partenaire) throws SQLException {
        String sql = "INSERT INTO partenaire (Name, Type, Description, Numero, DateDebut, DateFin) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, partenaire.getName());
        statement.setString(2, partenaire.getType());
        statement.setString(3, partenaire.getDescription());
        statement.setInt(4, partenaire.getNumero());
        statement.setString(5, partenaire.getDateDebut());
        statement.setString(6, partenaire.getDateFin());

        statement.executeUpdate();
        return false;
    }

    public boolean updatePartenaire(Partenaire partenaire) {
        boolean result = false;
        try {
            String requete = "UPDATE `partenaire` SET Name='" + partenaire.getName()
                    + "',Type='" + partenaire.getType()
                    + "',Description='" + partenaire.getDescription()
                    + "',Numero='" + partenaire.getNumero()
                    + "',DateDebut='" + partenaire.getDateDebut()
                    + "',DateFin='" + partenaire.getDateFin()
                    + "' WHERE `id` =" + partenaire.getId();
            Statement st;

            st = connection.createStatement();

            st.executeUpdate(requete);
            result = true;
            System.out.println("Mise à jour effectuée avec succès");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }


    public boolean deletePartenaire(int Id) {
        boolean result = false;
        try {
            String req = " DELETE FROM `partenaire` WHERE `id` =" + Id;

            PreparedStatement ps = connection.prepareStatement(req);

            ps.executeUpdate();
            System.out.println("partenaire supprime avec succes");
            result = true;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public ObservableList<Partenaire> displayPartenaire() {
        //instance liste de Partenaire
        ObservableList<Partenaire> list = FXCollections.observableArrayList();
        //ecrire requete sql pour recuperer les terrains
        String req = "select * from partenaire";

        try {
            //creation de statement
            Statement st = connection.createStatement();
            // executer la requette et recuperer le resultat
            ResultSet rs = st.executeQuery(req);
            // tant que on a un resultat
            while (rs.next()) {
                Partenaire prt = new Partenaire();
                prt.setId(rs.getInt("Id"));
                prt.setName(rs.getString("Name"));
                prt.setType(rs.getString("Type"));
                prt.setDescription(rs.getString("description"));
                prt.setNumero(rs.getInt("Numero"));
                prt.setDateDebut(rs.getString("DateDebut"));
                prt.setDateFin(rs.getString("DateFin"));

                //ajouter a la liste
                list.add(prt);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public ObservableList<Partenaire> displayPartenaireByName() {

        ObservableList<Partenaire> list = FXCollections.observableArrayList();
        String req = "select * from partenaire ORDER BY `Name` ";
        try {
            //creation de statement
            Statement st = connection.createStatement();
            // executer la requette et recuperer le resultat
            ResultSet rs = st.executeQuery(req);
            // tant que on a un resultat
            while (rs.next()) {
                Partenaire prt = new Partenaire();
                prt.setId(rs.getInt("Id"));
                prt.setName(rs.getString("Name"));
                prt.setType(rs.getString("Type"));
                prt.setDescription(rs.getString("description"));
                prt.setNumero(rs.getInt("Numero"));
                prt.setDateDebut(rs.getString("DateDebut"));
                prt.setDateFin(rs.getString("DateFin"));

                //ajouter a la liste
                list.add(prt);
                System.out.println(prt);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
