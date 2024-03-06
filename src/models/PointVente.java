package models;

public class PointVente {
    private int id;
    private String Name;
    private String Localisation;
    private String Description;
    private int Numero;




    public PointVente(int id, String Name, String Localisation, String Description, int Numero) {
        this.id = id;
        this.Name = Name;
        this.Localisation = Localisation;
        this.Description = Description;
        this.Numero = Numero;


    }

    public PointVente(String Name, String Localisation, String Description, int Numero) {
        this.Name = Name;
        this.Localisation = Localisation;
        this.Description = Description;
        this.Numero = Numero;


    }

    public PointVente() {

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

    public String getLocalisation() {
        return Localisation;
    }

    public void setLocalisation(String Localisation) {
        this.Localisation = Localisation;
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








    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name=" + Name +
                ", Localisation=" + Localisation +
                ", Description=" + Description +
                ", Numero=" + Numero + '\'' +
                '}';
    }
}
