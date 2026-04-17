package Utilisateurs;

import Planification_voyage.Vol;
import Planification_voyage.Itineraire;
import Planification_voyage.Trajet;

/**
 * Classe de base fusionnant les rôles d'Observateur et de Visiteur.
 */
public abstract class ProfilVisiteur {

    /**
     * Méthode du patron Observateur : mise à jour de l'affichage.
     */
    public abstract void update();

    /**
     * Méthodes du patron Visiteur : traitement spécifique par type de voyage.
     */
    public abstract void visitVol(Vol vol);
    public abstract void visitItineraire(Itineraire itineraire);
    public abstract void visitTrajet(Trajet trajet);
}