package Gestion_Reservation;
import Configuration_espace.Siege;
import Configuration_espace.Cabine;

public class Annule implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(true); // On libère la place
        context.setEtat(new Reserve()); // Retour au début
    }
    @Override
    public void event(Cabine context) {
        context.setDisponibilite(true);
        context.setEtat(new Reserve());
    }
}