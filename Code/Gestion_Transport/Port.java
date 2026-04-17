package Gestion_Transport;

/**
 * Représente un port maritime.
 * Hérite automatiquement des méthodes de gestion des observateurs (Admin/Client).
 */
public class Port extends LieuTransport {

    public Port() {
        super();
    }

    /**
     * Constructeur utilisé par la PaquebotFactory pour l'instanciation dynamique.
     */
    public Port(String sigle) {
        super(sigle);
    }
}