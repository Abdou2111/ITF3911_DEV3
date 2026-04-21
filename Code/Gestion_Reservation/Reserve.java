package Gestion_Reservation;
import Configuration_espace.Cabine;
import Configuration_espace.Siege;

public class Reserve implements State {
    @Override
    public void event(Siege context) {
        context.setDisponibilite(false);
        context.setEtat(new Confirme()); 
    }
    @Override
    public void event(Cabine context) {
        context.setDisponibilite(false);
        context.setEtat(new Confirme());
    }
}