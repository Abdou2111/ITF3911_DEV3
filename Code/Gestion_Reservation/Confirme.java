package Gestion_Reservation;

import Configuration_espace.Siege;
import Configuration_espace.Cabine;

public class Confirme implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(false); // Confirmé : reste occupé
    }

    @Override
    public void event(Cabine context) {
        context.setDisponibilite(false);
    }
}