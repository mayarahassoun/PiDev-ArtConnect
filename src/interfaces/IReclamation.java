package interfaces;

import models.Reclamation;

import java.util.List;

public interface IReclamation {
    int Create(Reclamation P);
    List<Reclamation> Read();
    int update(Reclamation P);
    int delete(int id);
    void   updateReclamationParUser(Reclamation reclamation);
    int deleteReclamationParUSer(int id,int idUser);

    List<Reclamation> afficherReclamationsParUser();
    void modifierStatutReclamation(int idReclamation, String nouveauStatut, int idUser) ;

}
