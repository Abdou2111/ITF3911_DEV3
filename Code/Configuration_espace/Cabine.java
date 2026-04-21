package Configuration_espace;
import Gestion_Reservation.Reserve;
import Gestion_Reservation.State;

public class Cabine {
    private int capacity;
    private boolean disponibilite = true;
    private String IDCabine;
    private State current_state;


    public Cabine(String id) { 
        this.IDCabine = id;
        this.current_state = new Reserve(); 
    }


    public void actionner() { 
        current_state.event(this);
    }

    public void setEtat(State s) { this.current_state = s; }

    public void event(String e) {
        if (current_state != null) {
            current_state.event(this);
        }
    }

    
    public int getCapacity() { return capacity; }
    public void setCapacity(int c) { this.capacity = c; }
    public boolean getDisponibilite() { return disponibilite; }
    public void setDisponibilite(boolean d) { this.disponibilite = d; }
    public String getIDCabine() { return IDCabine; }
    public void setIDCabine(String id) { this.IDCabine = id; }
    public void setCurrent_state(State s) { this.current_state = s; }
    public State getCurrent_state() { return current_state; }
}