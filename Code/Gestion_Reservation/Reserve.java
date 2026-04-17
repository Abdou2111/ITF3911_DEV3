package Gestion_Reservation;

import Configuration_espace.Siege;
import Configuration_espace.Cabine;

public class Reserve implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(false); // Le siège est bloqué
    }

    @Override
    public void event(Cabine context) {
        context.setDisponibilite(false);
    }
}