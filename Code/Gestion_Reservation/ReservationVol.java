package Gestion_Reservation;

import Configuration_espace.Siege;

/**
 * Réservation spécialisée pour un vol.
 * Elle conserve le siège éventuellement associé à la réservation
 * ainsi que la préférence de placement du client.
 */
public class ReservationVol extends Reservation {
    private Priorite prioritePlace;
    private Siege siegeReserve;

    public ReservationVol() {
        super();
    }

    public ReservationVol(String numReservation, Siege siegeReserve, Priorite prioritePlace) {
        super();
        this.numReservation = numReservation;
        this.siegeReserve = siegeReserve;
        this.prioritePlace = prioritePlace;
    }

    public Priorite getPrioritePlace() {
        return prioritePlace;
    }

    public void setPrioritePlace(Priorite p) {
        this.prioritePlace = p;
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
        return "ReservationVol{" +
                "numReservation='" + numReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", etat=" + etat +
                ", prioritePlace=" + prioritePlace +
                ", siegeReserve=" + siegeReserve +
                '}';
    }
}
