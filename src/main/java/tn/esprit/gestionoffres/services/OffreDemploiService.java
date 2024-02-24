package tn.esprit.gestionoffres.services;

import tn.esprit.gestionoffres.models.OffreDemploi;
import tn.esprit.gestionoffres.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreDemploiService implements IService<OffreDemploi> {

    private Connection connection;

    public OffreDemploiService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(OffreDemploi offreDemploi) throws SQLException {
        String req = "INSERT INTO OffreDemploi (titre, description, salaire, status, idEntreprise) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, offreDemploi.getTitre());
        ps.setString(2, offreDemploi.getDescription());
        ps.setFloat(3, offreDemploi.getSalaire());
        ps.setString(4, offreDemploi.getStatus());
        ps.setInt(5, offreDemploi.getIdEntreprise());
        ps.executeUpdate();
    }

    @Override
    public void modifier(OffreDemploi offreDemploi) throws SQLException {
        String req = "UPDATE OffreDemploi SET titre = ?, description = ?, salaire = ?, status = ?, idEntreprise = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, offreDemploi.getTitre());
        ps.setString(2, offreDemploi.getDescription());
        ps.setFloat(3, offreDemploi.getSalaire());
        ps.setString(4, offreDemploi.getStatus());
        ps.setInt(5, offreDemploi.getIdEntreprise());
        ps.setInt(6, offreDemploi.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM OffreDemploi WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<OffreDemploi> recuperer() throws SQLException {
        String req = "SELECT * FROM OffreDemploi";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        List<OffreDemploi> offres = new ArrayList<>();
        while (rs.next()) {
            OffreDemploi offre = new OffreDemploi();
            offre.setId(rs.getInt("id"));
            offre.setTitre(rs.getString("titre"));
            offre.setDescription(rs.getString("description"));
            offre.setSalaire(rs.getFloat("salaire"));
            offre.setStatus(rs.getString("status"));
            offre.setIdEntreprise(rs.getInt("idEntreprise"));
            offres.add(offre);
        }
        return offres;
    }
}


