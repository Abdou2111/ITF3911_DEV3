package Gestion_Transport;

/**
 * Représente un aéroport dans le système de transport.
 * Hérite de la logique de notification et de gestion de localisation.
 */
public class Aeroport extends LieuTransport {

    public Aeroport() {
        super();
    }

    /**
     * Constructeur utilisé par l'AvionFactory pour l'initialisation par sigle.
     */
    public Aeroport(String sigle) {
        super(sigle);
    }

    /**
     * Constructeur complet pour une configuration immédiate des attributs hérités.
     */
    public Aeroport(String sigle, String ville, String pays) {
        super(sigle);
        this.setVille(ville);
        this.setPays(pays);
    }
}