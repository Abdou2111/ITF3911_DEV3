package Patrons_technique.Fabrique_Abstraite;

import Gestion_Transport.*;
import Planification_voyage.*;

public class AvionFactory extends AbstractFactory {
    private static AvionFactory instance;

    private AvionFactory() {} // Constructeur privé

    public static AvionFactory getInstance() {
        if (instance == null) {
            instance = new AvionFactory();
        }
        return instance;
    }

    @Override
    public Compagnie createCompagnie() { return new Aerienne(); }

    @Override
    public LieuTransport createLieuTransport() { return new Aeroport("TEMP"); }

    @Override
    public Voyage createVoyage() { return new Vol(); }
}