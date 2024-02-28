package tn.models;

import java.time.LocalDate;

public class Partenaire {
        private int id;
    private String Name;
    private String Type;
    private String Description;
    private int Numero;
    private String DateDebut;
    private String DateFin;


    public Partenaire(int id, String Name, String Type, String Description, int Numero, String DateDebut, String DateFin) {
        this.id = id;
        this.Name = Name;
        this.Type = Type;
        this.Description = Description;
        this.Numero = Numero;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
    }

    public Partenaire(String Name, String Type, String Description, int Numero, String DateDebut, String DateFin) {
        this.Name = Name;
        this.Type = Type;
        this.Description = Description;
        this.Numero = Numero;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
    }

    public Partenaire() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name ;   }
    public void setName(String Name) {
        this.Name = Name;    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int Numero) {
        this.Numero = Numero;
    }

    public String getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(String DateDebut) {
        this.DateDebut = DateDebut;
    }

    public String getDateFin() {
        return DateFin;
    }

    public void setDateFin(String DateFin) {
        this.DateFin = DateFin;
    }






    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name=" + Name +
                ", Type=" + Type +
                ", Description=" + Description +
                ", Numero=" + Numero +
                ", DateDebut=" + DateDebut +
                ", DateFin='" + DateFin + '\'' +
                '}';
    }
}
