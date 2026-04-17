package Utilisateurs;

import Planification_voyage.*;
import Configuration_espace.*;
import Gestion_Transport.LieuTransport;

public class AdminVisiteur extends ProfilVisiteur {

    @Override
    public void update() {
        System.out.println("Console Admin : Mise à jour reçue.");
    }

    @Override public void visitVol(Vol vol) { afficherVoyage(vol, "[AIRCAN]"); }
    @Override public void visitTrajet(Trajet t) { afficherVoyage(t, "[VIARAIL]"); }
    @Override public void visitItineraire(Itineraire i) { afficherVoyage(i, "[CRUISCO]"); }

    private void afficherVoyage(Voyage v, String tag) {
        StringBuilder sb = new StringBuilder();
        
        // Construction de la ligne de trajet
        sb.append(v.getOrigine());
        
        // Ajout des escales seulement pour Train et Paquebot
        if (v instanceof Trajet || v instanceof Itineraire) {
            for (LieuTransport escale : v.getEscales()) {
                sb.append(" -> [").append(escale.getSigle()).append("]");
            }
        }
        
        sb.append(" -> ").append(v.getDestination()).append(":");
        sb.append(tag).append(v.getVoyageID());
        sb.append("(").append(v.formatDates()).append(")");

        for (Section s : v.getSections()) {
            if (s instanceof SectionAvionTrain) {
                SectionAvionTrain sat = (SectionAvionTrain) s;
                String code = sat.getClasse().toString().substring(0,1) + sat.getDisposition().toString().substring(0,1);
                sb.append("|").append(code).append("(").append(s.getNbReserves()).append("/").append(s.getNbrePlace()).append(")").append(s.getPrix()).append("$");
            } else if (s instanceof SectionPaquebot) {
                SectionPaquebot sp = (SectionPaquebot) s;
                sb.append("|").append(sp.getClasse().toString().substring(0,1)).append("(").append(s.getNbReserves()).append("/").append(s.getNbrePlace()).append(")").append(s.getPrix()).append("$");
            }
        }
        System.out.println(sb.toString());
        System.out.println("");
    }
}