package Tarification;

import Configuration_espace.Section;
import Configuration_espace.SectionAvionTrain;
import Configuration_espace.ClasseAT;
import Gestion_Transport.Compagnie;

public class StrategyPrixAT implements StrategyPrix {

    @Override
    public double calculerPrix(Section section, Compagnie company) {
        double prixDeBase = company.getPrixPleinTarif();
        
        if (section instanceof SectionAvionTrain) {
            SectionAvionTrain sat = (SectionAvionTrain) section;
            ClasseAT classe = sat.getClasse();

            // Application des poids selon le type de classe
            switch (classe) {
                case PREMIERE: return prixDeBase * 1.0;
                case AFFAIRE: return prixDeBase * 0.75;
                case ECONOMIQUE_PREMIUM: return prixDeBase * 0.60;
                case ECONOMIQUE: return prixDeBase * 0.50;
                default: return prixDeBase;
            }
        }
        return prixDeBase;
    }
}