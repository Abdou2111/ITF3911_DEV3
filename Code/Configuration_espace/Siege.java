package Configuration_espace;

import Gestion_Reservation.State;
import Gestion_Reservation.Priorite;

public class Siege {
    private int rangee;
    private char colonne;
    private boolean disponibilite = true; // Par défaut disponible
    private Priorite priorite;
    private String IDSiege;
    private State current_state;

    public void event(String e) {
        if (current_state != null) {
            current_state.event(this); // Délégation
        }
    }

    // --- Getters & Setters ---
    public boolean getDisponibilite() { return disponibilite; }
    public void setDisponibilite(boolean d) { this.disponibilite = d; }
    public void setCurrent_state(State s) { this.current_state = s; }
    public State getCurrent_state() { return current_state; }
    public String getIDSiege() { return IDSiege; }
    public void setIDSiege(String id) { this.IDSiege = id; }
    public int getRangee() { return rangee; }
    public void setRangee(int r) { this.rangee = r; }
    public char getColonne() { return colonne; }
    public void setColonne(char c) { this.colonne = c; }
    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite p) { this.priorite = p; }
}