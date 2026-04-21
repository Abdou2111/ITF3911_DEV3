package Tarification;

import Configuration_espace.Section;
import Configuration_espace.SectionPaquebot;
import Configuration_espace.ClassePaquebot;
import Gestion_Transport.Compagnie;

public class StrategyPrixPaquebot implements StrategyPrix {

    @Override
    public double calculerPrix(Section section, Compagnie company) {
        double prixDeBase = company.getPrixPleinTarif();

        if (section instanceof SectionPaquebot) {
            SectionPaquebot sp = (SectionPaquebot) section;
            ClassePaquebot classe = sp.getClasse();

            switch (classe) {
                case SUITE: return prixDeBase * 1.20;
                case VUE_SUR_OCEAN: return prixDeBase * 1.0;
                case FAMILLE: return prixDeBase * 0.85;
                case INTERIEURE: return prixDeBase * 0.70;
                default: return prixDeBase;
            }
        }
        return prixDeBase;
    }
}