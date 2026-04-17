package Utilisateurs;

import Planification_voyage.*;
import Configuration_espace.*;
import Gestion_Transport.LieuTransport;

public class Client extends ProfilVisiteur {

    @Override public void update() { System.out.println("Client : Données rafraîchies."); }

    @Override public void visitVol(Vol vol) { afficherClient(vol, "[AIRCAN]", ClasseAT.ECONOMIQUE); }
    @Override public void visitTrajet(Trajet t) { afficherClient(t, "[VIARAIL]", ClasseAT.ECONOMIQUE); }
    @Override public void visitItineraire(Itineraire i) { afficherClient(i, "[CRUISCO]", ClassePaquebot.INTERIEURE); }

    private void afficherClient(Voyage v, String tag, Object classeCible) {
        StringBuilder sb = new StringBuilder();
        sb.append(v.getOrigine());

        if (v instanceof Trajet || v instanceof Itineraire) {
            for (LieuTransport e : v.getEscales()) sb.append(" -> [").append(e.getSigle()).append("]");
        }

        sb.append(" -> ").append(v.getDestination()).append(":");
        sb.append(tag).append(v.getVoyageID()).append("(").append(v.formatDates()).append(")");

        for (Section s : v.getSections()) {
            boolean ok = (s instanceof SectionAvionTrain && ((SectionAvionTrain)s).getClasse() == classeCible) ||
                         (s instanceof SectionPaquebot && ((SectionPaquebot)s).getClasse() == classeCible);
            if (ok) {
                sb.append("|").append(s.getPrix()).append("$|Disp:").append(s.calculerPlacesLibres());
            }
        }
        System.out.println(sb.toString());
        System.out.println("");
    }
}