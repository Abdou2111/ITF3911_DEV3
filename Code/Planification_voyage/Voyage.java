package Planification_voyage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Configuration_espace.Section;
import Utilisateurs.ProfilVisiteur;
import Gestion_Transport.LieuTransport;

public abstract class Voyage {
    protected LocalDateTime depart;
    protected LocalDateTime arrivee;
    protected String voyageID;
    protected LieuTransport origine;
    protected LieuTransport destination;
    
    // Liste des arrêts intermédiaires (Gares ou Ports)
    protected List<LieuTransport> escales = new ArrayList<>();
    
    protected List<Section> sections = new ArrayList<>();
    protected List<ProfilVisiteur> observateurs = new ArrayList<>();

    public abstract void accept(ProfilVisiteur visiteur);

    // --- Patron Observateur ---
    public void attach(ProfilVisiteur p) { 
        if (p != null && !observateurs.contains(p)) observateurs.add(p); 
    }
    public void notifyObservers() {
        for (ProfilVisiteur p : observateurs) p.update();
    }

    // --- Formatage ---
    public String formatDates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd:HH.mm");
        return depart.format(formatter) + "-" + arrivee.format(formatter);
    }

    // --- Getters & Setters ---
    public LocalDateTime getDepart() { return depart; }
    public void setDepart(LocalDateTime d) { this.depart = d; notifyObservers(); }
    public LocalDateTime getArrivee() { return arrivee; }
    public void setArrivee(LocalDateTime a) { this.arrivee = a; notifyObservers(); }
    public String getVoyageID() { return voyageID; }
    public void setVoyageID(String id) { this.voyageID = id; }
    public void setOrigine(LieuTransport o) { this.origine = o; }
    public String getOrigine() { return (origine != null) ? origine.getSigle() : "N/A"; }
    public void setDestination(LieuTransport d) { this.destination = d; }
    public String getDestination() { return (destination != null) ? destination.getSigle() : "N/A"; }
    
    public List<Section> getSections() { return sections; }
    
    // Gestion des escales
    public List<LieuTransport> getEscales() { return escales; }
    public void addEscale(LieuTransport e) { this.escales.add(e); notifyObservers(); }
}