package tn.esprit.gestionoffres.models;

public class OffreDemploi {
    int id;
    String titre;
    String description;
    Float salaire;
    String status;
    int idEntreprise;

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
}
