package Gestion_Reservation;

import Configuration_espace.Siege;
import Configuration_espace.Cabine;

public interface State {
    void event(Siege context);
    void event(Cabine context);
    //Etat getEnumEtat();
}