package Planification_voyage;

import Utilisateurs.ProfilVisiteur;

public class Trajet extends Voyage {
    @Override
    public void accept(ProfilVisiteur profil) {
        profil.visitTrajet(this); // Redirection spécifique
    }
}