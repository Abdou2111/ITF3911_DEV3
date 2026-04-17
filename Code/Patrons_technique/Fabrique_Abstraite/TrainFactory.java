package Patrons_technique.Fabrique_Abstraite;

import Gestion_Transport.*;
import Planification_voyage.*;

public class TrainFactory extends AbstractFactory {
    private static TrainFactory instance;

    private TrainFactory() {}

    public static TrainFactory getInstance() {
        if (instance == null) {
            instance = new TrainFactory();
        }
        return instance;
    }

    @Override
    public Compagnie createCompagnie() { return new LigneDeTrain(); }

    @Override
    public LieuTransport createLieuTransport() { return new Gare("TEMP"); }

    @Override
    public Voyage createVoyage() { return new Trajet(); }
}