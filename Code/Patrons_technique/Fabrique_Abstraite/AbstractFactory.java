package Patrons_technique.Fabrique_Abstraite;

import Gestion_Transport.Compagnie;
import Gestion_Transport.LieuTransport;
import Planification_voyage.Voyage;

/**
 * Interface de base pour la création coordonnée des composants de transport.
 */
public abstract class AbstractFactory {
    public abstract Compagnie createCompagnie();
    public abstract LieuTransport createLieuTransport();
    public abstract Voyage createVoyage();
}