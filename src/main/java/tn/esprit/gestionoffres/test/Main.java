package tn.esprit.gestionoffres.test;

import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.services.EntrepriseService;

import java.sql.SQLException;
import java.util.List;

/*public class Main {
    public static void main(String[] args) {
        EntrepriseService es = new EntrepriseService();
       try {
            es.ajouter(new Entreprise("muse", "tunis", "4444"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        try {
            es.modifier(new Entreprise(34,"m", "ITALY", "4444"));
        }catch (SQLException e){
            System.err.println(e.getMessage());
         }
        try {
            es.supprimer(34);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        try {
            System.out.println(es.recuperer());;
        }catch (SQLException e){
            System.err.println(e.getMessage());
    }


      }*/
   public class Main {

    public static void main(String[] args) throws SQLException {

        // Création du service Entreprise
        EntrepriseService service = new EntrepriseService();

           // Ajout d'une nouvelle entreprise
           Entreprise entreprise1 = new Entreprise("Nom Entreprise 1", "Adresse Entreprise 1", "Contact Entreprise 1");
           service.ajouter(entreprise1);


           // **Ajout d'une deuxième entreprise**
           Entreprise entreprise2 = new Entreprise("Nom Entreprise 2", "Adresse Entreprise 2", "Contact Entreprise 2");
           service.ajouter(entreprise2);



        // **Récupération de toutes les entreprises**
        List<Entreprise> entreprises = service.recuperer();

        // **Affichage de la liste des entreprises**
        System.out.println("\nListe des entreprises : ");
        for (Entreprise entreprise : entreprises) {
            System.out.println("-------------------");
            System.out.println("ID : " + entreprise.getId());
            System.out.println("Nom : " + entreprise.getNom());
            System.out.println("Adresse : " + entreprise.getAdresse());
            System.out.println("Contact : " + entreprise.getContact());
        }

       /* try {
            service.modifier(new Entreprise(40, "m", "ITALY", "4444"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());

           try {
               service.supprimer(41);
           }catch (SQLException e){
               System.err.println(e.getMessage());
           }
           }*/


        }
    }



