package Gestion_Reservation;

import Configuration_espace.Cabine;
import Configuration_espace.Siege;

public interface State {
    void event(Siege context);
    void event(Cabine context);
    
}