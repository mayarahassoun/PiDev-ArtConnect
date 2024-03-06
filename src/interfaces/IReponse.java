package interfaces;

import models.Reponse;

import java.util.List;

public interface IReponse <R>{
    // Ajouter une réponse à la base de données
    public void ajouterReponse(R reponse,int idUser);
   public Reponse recupererReponseParIdReclamation(int idReclamation);

    // Supprimer une réponse de la base de données
    public void supprimerReponse(R reponse);

    // Modifier une réponse dans la base de données
    public void modifierReponse(R reponse);

    // Récupérer toutes les réponses de la base de données
    public List<R> afficherTousReponses();

    // Récupérer toutes les réponses associées à une réclamation donnée
    public List<R> afficherReponsesParReclamation(int id_reclamation);
}
