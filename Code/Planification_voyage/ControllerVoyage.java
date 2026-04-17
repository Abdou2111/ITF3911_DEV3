package Planification_voyage;

import java.util.List;
import java.util.ArrayList;
import Configuration_espace.ClasseAT;
import Gestion_Transport.LieuTransport;

public class ControllerVoyage {
    private VoyageDataStructure db = new VoyageDataStructure();

    public List<Voyage> rechercheVoyage(LieuTransport origin, LieuTransport destination, ClasseAT classe) {
        List<Voyage> resultats = new ArrayList<>();
        for (Voyage v : db.getListeVoyages()) {
            // Logique de filtrage par origine et destination
            if (v.getOrigine().equals(origin.getSigle()) && v.getDestination().equals(destination.getSigle())) {
                resultats.add(v);
            }
        }
        return resultats; 
    }
    
    public VoyageDataStructure getDb() { return db; }
}