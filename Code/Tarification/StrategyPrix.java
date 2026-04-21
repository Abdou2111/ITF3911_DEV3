package Tarification;

import Configuration_espace.Section;
import Gestion_Transport.Compagnie;

public interface StrategyPrix {
    double calculerPrix(Section section, Compagnie company);
}