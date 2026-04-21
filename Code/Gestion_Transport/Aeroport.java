package Gestion_Transport;


public class Aeroport extends LieuTransport {

    public Aeroport() {
        super();
    }

    public Aeroport(String sigle) {
        super(sigle);
    }

    
    public Aeroport(String sigle, String ville, String pays) {
        super(sigle);
        this.setVille(ville);
        this.setPays(pays);
    }
}