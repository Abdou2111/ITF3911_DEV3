package Planification_voyage;

import java.util.ArrayList;
import java.util.List;
import Utilisateurs.ProfilVisiteur;

public class VoyageDataStructure {
    private List<Voyage> listeVoyages = new ArrayList<>();

    public void addVoyage(Voyage v) { listeVoyages.add(v); }

    public void accept(ProfilVisiteur profil) {
        for (Voyage v : listeVoyages) {
            v.accept(profil); // Déclenche la visite pour chaque voyage
        }
    }
    
    public List<Voyage> getListeVoyages() { return listeVoyages; }
}