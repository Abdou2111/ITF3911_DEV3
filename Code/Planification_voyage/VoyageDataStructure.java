package Planification_voyage;

import java.util.ArrayList;
import java.util.List;
import Utilisateurs.ProfilVisiteur;

public class VoyageDataStructure {
    private List<Voyage> listeVoyages = new ArrayList<>();
    private List<VoyageObserver> observers = new ArrayList<>();

    public void addVoyage(Voyage v) {
        listeVoyages.add(v);
        notifyObservers();
    }

    public void accept(ProfilVisiteur profil) {
        for (Voyage v : listeVoyages) {
            v.accept(profil); // Déclenche la visite pour chaque voyage
        }
    }
    
    public List<Voyage> getListeVoyages() { return listeVoyages; }

    // Observateur
    public void addObserver(VoyageObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(VoyageObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (VoyageObserver observer : observers) {
            observer.update();
        }
    }
}