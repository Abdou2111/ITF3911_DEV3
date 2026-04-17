package Gestion_Reservation;

import Configuration_espace.Siege;
import Configuration_espace.Cabine;

public class Annule implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(true); // Libération de la place
    }

    @Override
    public void event(Cabine context) {
        context.setDisponibilite(true);
    }
}