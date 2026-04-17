package Gestion_Transport;

/**
 * Représente une gare ferroviaire.
 * Utilise le mécanisme 'super' pour garantir que le sigle est stocké au niveau parent.
 */
public class Gare extends LieuTransport {

    public Gare() {
        super();
    }

    /**
     * Constructeur requis par la TrainFactory.
     */
    public Gare(String sigle) {
        super(sigle);
    }
}