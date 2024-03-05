package tn.services;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.models.Partenaire;
import tn.models.PointVente;
import tn.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PointVenteServices {
    private Connection connection;

    public PointVenteServices() {
        connection = MyDatabase.getInstance().getConnection();
    }


    public boolean addPointVente(PointVente pointVente) throws SQLException {
        String sql = "INSERT INTO pointvente (Name, localisation, Description, Numero) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pointVente.getName());
        statement.setString(2, pointVente.getLocalisation());
        statement.setString(3, pointVente.getDescription());
        statement.setInt(4, pointVente.getNumero());


        statement.executeUpdate();
        return false;
    }

    public boolean updatePointVente(PointVente pointVente) {
        boolean result = false;
        try {
            String requete = "UPDATE `pointvente` SET Name='" + pointVente.getName()
                    + "',localisation='" + pointVente.getLocalisation()
                    + "',Description='" + pointVente.getDescription()
                    + "',Numero='" + pointVente.getNumero()
                    + "' WHERE `id` =" + pointVente.getId();
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


    public boolean deletePointVente(int Id) {
        boolean result = false;
        try {
            String req = " DELETE FROM `pointvente` WHERE `id` =" + Id;

            PreparedStatement ps = connection.prepareStatement(req);

            ps.executeUpdate();
            System.out.println("pointvente supprime avec succes");
            result = true;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public ObservableList<PointVente> displayPointVente() {
        //instance liste de Partenaire
        ObservableList<PointVente> list = FXCollections.observableArrayList();
        //ecrire requete sql pour recuperer les terrains
        String req = "select * from pointvente";

        try {
            //creation de statement
            Statement st = connection.createStatement();
            // executer la requette et recuperer le resultat
            ResultSet rs = st.executeQuery(req);
            // tant que on a un resultat
            while (rs.next()) {
                PointVente pv = new PointVente();
                pv.setId(rs.getInt("Id"));
                pv.setName(rs.getString("Name"));
                pv.setLocalisation(rs.getString("localisation"));
                pv.setDescription(rs.getString("description"));
                pv.setNumero(rs.getInt("Numero"));

                //ajouter a la liste
                list.add(pv);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
