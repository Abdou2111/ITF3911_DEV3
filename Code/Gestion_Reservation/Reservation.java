package Gestion_Reservation;

import Utilisateurs.ProfilVisiteur;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Reservation {
    protected String numReservation;
    protected LocalDateTime dateReservation;
    protected Etat etat;
    protected List<ProfilVisiteur> observateurs = new ArrayList<>();

    public Reservation() {
        this.dateReservation = LocalDateTime.now();
        this.etat = Etat.RESERVE;
    }

    // --- Patron Observateur ---
    public void attach(ProfilVisiteur p) { if(!observateurs.contains(p)) observateurs.add(p); }
    public void detach(ProfilVisiteur p) { observateurs.remove(p); }
    
    public void notifyObservers() {
        for (ProfilVisiteur p : observateurs) { p.update(); } 
    }

    // --- Getters & Setters ---
    public void setEtat(Etat nouvelEtat) {
        this.etat = nouvelEtat;
        notifyObservers(); 
    }

    public Etat getEtat() { return etat; }
    public String getNumReservation() { return numReservation; }
    public void setNumReservation(String num) { this.numReservation = num; }
    public LocalDateTime getDateReservation() { return dateReservation; }
}