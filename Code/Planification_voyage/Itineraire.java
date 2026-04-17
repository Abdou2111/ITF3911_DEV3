package Planification_voyage;

import Utilisateurs.ProfilVisiteur;

public class Itineraire extends Voyage {
    @Override
    public void accept(ProfilVisiteur profil) {
        profil.visitItineraire(this); // Redirection spécifique
    }


}