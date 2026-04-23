package Gestion_Reservation;

import Configuration_espace.Siege;

/**
 * Réservation spécialisée pour un trajet de train.
 * Un train utilise des sièges, sans priorité spécifique comme pour l'avion.
 */
public class ReservationTrain extends Reservation {
    private Siege siegeReserve;

    public ReservationTrain() {
        super();
    }

    public ReservationTrain(String numReservation, Siege siegeReserve) {
        super();
        this.numReservation = numReservation;
        this.siegeReserve = siegeReserve;
    }

    public Siege getSiegeReserve() {
        return siegeReserve;
    }

    public void setSiegeReserve(Siege siegeReserve) {
        this.siegeReserve = siegeReserve;
    }

    public boolean aUnSiegeReserve() {
        return siegeReserve != null;
    }

    @Override
    public String toString() {
        return "ReservationTrain{" +
                "numReservation='" + numReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", etat=" + etat +
                ", siegeReserve=" + siegeReserve +
                '}';
    }
}
