package Planification_voyage;

import Utilisateurs.ProfilVisiteur;

public class Vol extends Voyage {
    @Override
    public void accept(ProfilVisiteur profil) {
        profil.visitVol(this); // Redirection spécifique
    }
}