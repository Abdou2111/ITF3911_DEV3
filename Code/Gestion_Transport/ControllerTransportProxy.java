package Gestion_Transport;

import Utilisateurs.ProfilVisiteur;
import Utilisateurs.AdminVisiteur;
import java.time.LocalDateTime;

public class ControllerTransportProxy implements IControllerTransport {
    private ControllerTransport realSubject;
    private ProfilVisiteur user;

    public ControllerTransportProxy(ProfilVisiteur user) {
        this.user = user;
        this.realSubject = new ControllerTransport();
    }

    private boolean estAdmin() {
        return (user instanceof AdminVisiteur);
    }

    @Override
    public boolean createLieu(String type, String nom, String ville, String pays) {
        return estAdmin() ? realSubject.createLieu(type, nom, ville, pays) : false;
    }

    @Override
    public boolean createVoyage(LocalDateTime depart, LocalDateTime arrivee, LieuTransport origin, LieuTransport dest) {
        return estAdmin() ? realSubject.createVoyage(depart, arrivee, origin, dest) : false;
    }

    @Override
    public boolean deleteVoyage(String voyageID) {
        return estAdmin() ? realSubject.deleteVoyage(voyageID) : false;
    }

    @Override
    public boolean createCompany(String type, String nom, double prixPlein) {
        return estAdmin() ? realSubject.createCompany(type, nom, prixPlein) : false;
    }
}