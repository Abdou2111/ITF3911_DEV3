package Gestion_Reservation;

import Configuration_espace.Siege;
import Gestion_paiement.Paiement;
import java.time.Duration;
import java.time.LocalDateTime;

public class ControllerReservation {

    /**
     * Initie une réservation et change l'état physique du siège.
     */
    public String reserverSiege(Siege s) {
        s.setCurrent_state(new Reserve());
        s.event("reserver");
        
        String idGenere = "RES-" + System.currentTimeMillis();
        return idGenere;
    }

    /**
     * Applique la règle métier de l'UdeM : annulation après 24h sans paiement.
     */
    public void verifierExpiration(Reservation r) {
        if (r.getEtat() == Etat.RESERVE) {
            long heures = Duration.between(r.getDateReservation(), LocalDateTime.now()).toHours();
            if (heures >= 24) {
                r.setEtat(Etat.ANNULE);
                
            }
        }
    }

    /**
     * Confirme définitivement la réservation après succès du paiement.
     */
    public boolean confirmerPaiement(Reservation r, Paiement p) {
        if (p != null && p.effectuerPaiement()) {
            r.setEtat(Etat.CONFIRME);
            return true;
        }
        return false;
    }
}