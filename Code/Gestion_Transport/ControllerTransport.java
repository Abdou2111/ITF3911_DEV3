package Gestion_Transport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Patrons_technique.Fabrique_Abstraite.*;
import Planification_voyage.*;

public class ControllerTransport implements IControllerTransport {
    private List<LieuTransport> listeLieux = new ArrayList<>();
    private List<Compagnie> listeCompagnies = new ArrayList<>();
    private List<Voyage> listeVoyages = new ArrayList<>();

    private AbstractFactory getFactory(String type) {
        switch (type.toUpperCase()) {
            case "AIRPORT": return AvionFactory.getInstance();
            case "GARE": return TrainFactory.getInstance();
            case "PORT": return PaquebotFactory.getInstance();
            default: throw new IllegalArgumentException("Type inconnu");
        }
    }

    @Override
    public boolean createLieu(String type, String nom, String ville, String pays) {
        AbstractFactory factory = getFactory(type);
        LieuTransport lieu = factory.createLieuTransport();
        lieu.setSigle(nom); 
        lieu.setVille(ville);
        lieu.setPays(pays);
        return listeLieux.add(lieu);
    }

    @Override
    public boolean createCompany(String type, String nom, double prixPlein) {
        AbstractFactory factory = getFactory(type);
        Compagnie c = factory.createCompagnie();
        c.setNomCompany(nom);
        c.setPrixPleinTarif(prixPlein);
        return listeCompagnies.add(c);
    }

    @Override
    public boolean createVoyage(LocalDateTime depart, LocalDateTime arrivee, LieuTransport origin, LieuTransport dest) {
        AbstractFactory factory = (origin instanceof Aeroport) ? AvionFactory.getInstance() : 
                         (origin instanceof Gare) ? TrainFactory.getInstance() : PaquebotFactory.getInstance();
        
        Voyage v = factory.createVoyage();
        v.setDepart(depart);
        v.setArrivee(arrivee);
        v.setOrigine(origin);    // Nécessite setOrigine dans Voyage
        v.setDestination(dest); // Nécessite setDestination dans Voyage
        return listeVoyages.add(v);
    }

    @Override
    public boolean deleteVoyage(String voyageID) {
        return listeVoyages.removeIf(v -> v.getVoyageID() != null && v.getVoyageID().equals(voyageID));
    }

    public void ajouterEscale(String voyageID, LieuTransport escale) {
    for (Voyage v : listeVoyages) {
        if (v.getVoyageID().equals(voyageID)) {
            v.addEscale(escale);
            break;
        }
    }
}

    // Getters pour la cohérence inter-contrôleurs
    public List<LieuTransport> getListeLieux() { return listeLieux; }
    public List<Compagnie> getListeCompagnies() { return listeCompagnies; }
    public List<Voyage> getListeVoyages() { return listeVoyages; }
}