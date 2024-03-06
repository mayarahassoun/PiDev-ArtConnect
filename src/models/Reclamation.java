package models;

public class  Reclamation {

    private int id_reclamation;
    private String message;
    private String type;
    private String statut;
    private int Id;
    private String nom;
    private String date_rec;


    public Reclamation(int id_reclamation, String message, String type, String statut, int id, String nom, String date_rec) {
        this.id_reclamation = id_reclamation;
        this.message = message;
        this.type = type;
        this.statut = statut;
        Id = id;
        this.nom = nom;
        this.date_rec = date_rec;
    }

    public Reclamation(int id_reclamation, String message, String type, String statut, int id, String date_rec) {
        this.id_reclamation = id_reclamation;
        this.message = message;
        this.type = type;
        this.statut = statut;
        Id = id;
        this.date_rec = date_rec;
    }

    public   Reclamation(String message, String type, String statut, String date_rec) {

        this.message = message;
        this.type = type;
        this.statut = statut;
        this.date_rec=date_rec;
    }
    public String getNom() {
        return nom;
    }

    public Reclamation(String message) {
        super();
        this.message = message;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public Reclamation(int id_reclamation, String message, String type, String statut, int Id) {
        this.id_reclamation = id_reclamation;
        this.message = message;
        this.type = type;
        this.statut = statut;
        this.Id = Id;
    }

    public Reclamation(String message, String type, String statut, int Id) {
        this.message = message;
        this.type = type;
        this.statut = statut;
        this.Id = Id;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", statut='" + statut + '\'' +
                ", Id=" + Id +
                ", nom='" + nom + '\'' +
                ", date_rec='" + date_rec + '\'' +
                '}';
    }

    public String getDate_rec() {
        return date_rec;
    }

    public void setDate_rec(String date_rec) {
        this.date_rec = date_rec;
    }
}
