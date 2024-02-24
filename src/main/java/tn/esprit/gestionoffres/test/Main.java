package tn.esprit.gestionoffres.test;

import tn.esprit.gestionoffres.models.Entreprise;
import tn.esprit.gestionoffres.models.OffreDemploi;
import tn.esprit.gestionoffres.services.EntrepriseService;
import tn.esprit.gestionoffres.services.OffreDemploiService;

import java.sql.SQLException;
import java.util.List;



   public class Main {

    public static void main(String[] args) throws SQLException {

        // Création du service Entreprise
        EntrepriseService service = new EntrepriseService();
        // Création du service Entreprise
        OffreDemploiService serviceOffre = new OffreDemploiService();

          /* // Ajout d'une nouvelle entreprise
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

       try {
            service.modifier(new Entreprise(40, "m", "ITALY", "4444"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());

           try {
               service.supprimer(41);
           }catch (SQLException e){
               System.err.println(e.getMessage());
           }
           }
        // Ajout d'une nouvelle offre d'emploi
        OffreDemploi offre1 = new OffreDemploi("Titre Offre 1", "Description Offre 1", 2000, "Ouvert", 45);
        serviceOffre.ajouter(offre1);
        // Ajout d'une deuxième offre d'emploi
        OffreDemploi offre2 = new OffreDemploi("Titre Offre 2", "Description Offre 2", 2500, "Fermé", 46);
        serviceOffre.ajouter(offre2);*/
      //mofidier offreDemploi
        try {
            serviceOffre.modifier(new OffreDemploi(4, "Nouveau Titre", "Nouvelle Description", 3000, "Ouvert", 45));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Suppression d'une offre d'emploi
        try {
            serviceOffre.supprimer(3);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // Récupération de toutes les offres d'emploi
        List<OffreDemploi> offres = serviceOffre.recuperer();

        // Affichage de la liste des offres d'emploi
        System.out.println("\nListe des offres d'emploi : ");
        for (OffreDemploi offre : offres) {
            System.out.println("-------------------");
            System.out.println("ID : " + offre.getId());
            System.out.println("Titre : " + offre.getTitre());
            System.out.println("Description : " + offre.getDescription());
            System.out.println("Salaire : " + offre.getSalaire());
            System.out.println("Statut : " + offre.getStatus());
            System.out.println("ID Entreprise : " + offre.getIdEntreprise());
        }


        }
    }



