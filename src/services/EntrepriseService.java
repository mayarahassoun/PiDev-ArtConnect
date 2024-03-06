package services;

import models.Entreprise;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseService implements IService<Entreprise>{
    private Connection connection;
    public EntrepriseService(){
        connection = (Connection) ConnectionUtil.conDB();
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
    public Entreprise getEntrepriseByNom(String nom) throws SQLException {
        String req = "SELECT * FROM Entreprise WHERE nom = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, nom);
        ResultSet rs = ps.executeQuery();

        Entreprise entreprise = null;
        if (rs.next()) {
            entreprise = new Entreprise();
            entreprise.setId(rs.getInt("id"));
            entreprise.setNom(rs.getString("nom"));
            entreprise.setAdresse(rs.getString("adresse"));
            entreprise.setContact(rs.getString("contact"));
        }
        return entreprise;
    }


    public List<String> recupererNomsEntreprises() throws SQLException {
        String req = "SELECT nom FROM Entreprise";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        List<String> nomsEntreprises = new ArrayList<>();
        while (rs.next()) {
            String nom = rs.getString("nom");
            nomsEntreprises.add(nom);
        }
        return nomsEntreprises;
    }

    public Entreprise recupererParId(int idEntreprise) throws SQLException {
        String req = "SELECT * FROM Entreprise WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, idEntreprise);
        ResultSet rs = ps.executeQuery();

        Entreprise entreprise = null;
        if (rs.next()) {
            entreprise = new Entreprise();
            entreprise.setId(rs.getInt("id"));
            entreprise.setNom(rs.getString("nom"));
            entreprise.setAdresse(rs.getString("adresse"));
            entreprise.setContact(rs.getString("contact"));
        }
        return entreprise;
    }
}
