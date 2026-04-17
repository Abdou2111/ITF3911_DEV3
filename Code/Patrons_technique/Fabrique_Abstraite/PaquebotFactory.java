package Patrons_technique.Fabrique_Abstraite;

import Gestion_Transport.*;
import Planification_voyage.*;

public class PaquebotFactory extends AbstractFactory {
    private static PaquebotFactory instance;

    private PaquebotFactory() {}

    public static PaquebotFactory getInstance() {
        if (instance == null) {
            instance = new PaquebotFactory();
        }
        return instance;
    }

    @Override
    public Compagnie createCompagnie() { return new Croisiere(); }

    @Override
    public LieuTransport createLieuTransport() { return new Port("TEMP"); }

    @Override
    public Voyage createVoyage() { return new Itineraire(); }
}