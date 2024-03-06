package models;

import services.EntrepriseService;

import java.sql.SQLException;

public class OffreDemploi {
    int id;
    String titre;
    String description;
    Float salaire;
    String status;
    private int idEntreprise;

    public OffreDemploi() {}

    public OffreDemploi(int id, String titre, String description, float salaire, String status, int idEntreprise) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.salaire = salaire;
        this.status = status;
        this.idEntreprise = idEntreprise;
    }

    public OffreDemploi(String titre, String description, float salaire, String status, int idEntreprise) {
        this.titre = titre;
        this.description = description;
        this.salaire = salaire;
        this.status = status;
        this.idEntreprise = idEntreprise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    @Override
    public String toString() {
        return "OffreDemploi{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", salaire=" + salaire +
                ", status='" + status + '\'' +
                ", idEntreprise=" + idEntreprise +
                '}';
    }

    public String getNomEntreprise() throws SQLException {
        // Utilisez le service EntrepriseService pour récupérer l'entreprise correspondant à idEntreprise
        EntrepriseService entrepriseService = new EntrepriseService();
        Entreprise entreprise = entrepriseService.recupererParId(idEntreprise);

        // Vérifiez si l'entreprise a été trouvée
        if (entreprise != null) {
            // Si oui, retournez le nom de l'entreprise
            return entreprise.getNom();
        } else {
            // Sinon, retournez une chaîne vide ou null, selon vos besoins
            return ""; // ou return null;
        }
    }

}
