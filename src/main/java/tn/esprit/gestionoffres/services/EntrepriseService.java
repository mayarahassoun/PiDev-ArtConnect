package tn.esprit.gestionoffres.services;

import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseService implements IService<Entreprise>{

    private Connection connection;
    public EntrepriseService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Entreprise entreprise) throws SQLException {
        String req = "INSERT into Entreprise (nom,adresse,contact) VALUES ('" + entreprise.getNom()+"','"+entreprise.getAdresse()+"','"+entreprise.getContact()+"')";
        System.out.println(req);
        Statement st = connection.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void modifier(Entreprise entreprise) throws SQLException {
        String req = "UPDATE Entreprise SET nom = ? ,adresse = ? ,contact = ? WHERE id= ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, entreprise.getNom());
        ps.setString(2, entreprise.getAdresse());
        ps.setString(3, entreprise.getContact());
        ps.setInt(4, entreprise.getId());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM Entreprise WHERE id= ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();


    }

    @Override
    public List<Entreprise> recuperer() throws SQLException {
        String req = "select * from Entreprise";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);


        List<Entreprise> entreprises = new ArrayList<>();
        while (rs.next()) {
            Entreprise e = new Entreprise();
            e.setId(rs.getInt("id"));
            e.setNom(rs.getString("nom"));
            e.setAdresse(rs.getString("adresse"));
            e.setContact(rs.getString("contact"));
            entreprises.add(e);
        }
        return entreprises;
    }


}
