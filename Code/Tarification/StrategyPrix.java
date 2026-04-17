package Tarification;

import Configuration_espace.Section;
import Gestion_Transport.Compagnie;

/**
 * Interface pour le calcul dynamique des prix.
 */
public interface StrategyPrix {
    double calculerPrix(Section section, Compagnie company);
}