package Gestion_Reservation;
import Configuration_espace.Cabine;
import Configuration_espace.Siege;

public class Annule implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(true); 
        context.setEtat(new Reserve()); 
    }
    @Override
    public void event(Cabine context) {
        context.setDisponibilite(true);
        context.setEtat(new Reserve());
    }
}