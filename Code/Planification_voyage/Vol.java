package Planification_voyage;

import Utilisateurs.ProfilVisiteur;

public class Vol extends Voyage {
    @Override
    public void accept(ProfilVisiteur profil) {
        profil.visitVol(this); // Redirection spécifique
    }
}

// Les classes Trajet et Itineraire suivent la même logique avec visitTrajet et visitItineraire.