package services;

import models.OffreDemploi;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OffreDemploiService  implements IService<OffreDemploi> {

    private Connection connection;

    public OffreDemploiService() {
        connection = (Connection) ConnectionUtil.conDB();
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

    public List<OffreDemploi> rechercherParTitre(String titreRecherche) throws SQLException {
        List<OffreDemploi> offresTrouvees = new ArrayList<>();

        // Remplacez cette partie par votre propre logique de recherche dans la base de données
        List<OffreDemploi> toutesLesOffres = recuperer();
        for (OffreDemploi offre : toutesLesOffres) {
            if (offre.getTitre().toLowerCase().contains(titreRecherche.toLowerCase())) {
                offresTrouvees.add(offre);
            }
        }

        return offresTrouvees;
    }


    public List<OffreDemploi> recupererTrieParTitre() {
        // Récupérer toutes les offres d'emploi depuis la base de données
        List<OffreDemploi> offres = null;
        try {
            offres = recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Trier les offres par titre
        Collections.sort(offres, Comparator.comparing(OffreDemploi::getTitre));

        return offres;
    }

    public List<OffreDemploi> recupererTrieParSalaire() {
        // Récupérer toutes les offres d'emploi depuis la base de données
        List<OffreDemploi> offres = null;
        try {
            offres = recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Trier les offres par salaire
        Collections.sort(offres, Comparator.comparing(OffreDemploi::getSalaire));

        return offres;
    }
}
