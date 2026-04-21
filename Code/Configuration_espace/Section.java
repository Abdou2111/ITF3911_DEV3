package Configuration_espace;

import Gestion_Transport.Compagnie;
import Tarification.StrategyPrix;
import Utilisateurs.ProfilVisiteur;
import java.util.ArrayList;
import java.util.List;

public abstract class Section {
    protected double prix;
    protected int nbrePlace;
    protected String IDSection; 
    protected StrategyPrix strategiePrix;
    protected List<ProfilVisiteur> observateurs = new ArrayList<>();

    public Section(String id) {
        this.IDSection = id;
    }

    
    public double calculerPrix(Compagnie c) {
        if (this.strategiePrix != null) {
            return this.strategiePrix.calculerPrix(this, c);
        }
        return this.prix;
    }

    
    public abstract int getNbReserves();

    public int calculerPlacesLibres() {
        return this.nbrePlace - getNbReserves(); 
    }

    // --- Patron Observateur ---
    public void attach(ProfilVisiteur p) { if(!observateurs.contains(p)) observateurs.add(p); }
    public void detach(ProfilVisiteur p) { observateurs.remove(p); }
    public void notifyObservers() {
        for (ProfilVisiteur p : observateurs) { p.update(); } //
    }

    // --- Getters & Setters ---
    public String getIDSection() { return IDSection; }
    public void setIDSection(String id) { this.IDSection = id; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; notifyObservers(); }
    public int getNbrePlace() { return nbrePlace; }
    public void setNbrePlace(int n) { this.nbrePlace = n; notifyObservers(); }
    public void setStrategiePrix(StrategyPrix s) { this.strategiePrix = s; }
    public StrategyPrix getStrategiePrix() { return strategiePrix; }
}