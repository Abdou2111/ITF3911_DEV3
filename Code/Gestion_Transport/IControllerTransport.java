package Gestion_Transport;

import java.time.LocalDateTime;

public interface IControllerTransport {
    boolean createLieu(String type, String nom, String ville, String pays);
    boolean createVoyage(LocalDateTime depart, LocalDateTime arrivee, LieuTransport origin, LieuTransport dest);
    boolean deleteVoyage(String voyageID);
    boolean createCompany(String type, String nom, double prixPlein);
}