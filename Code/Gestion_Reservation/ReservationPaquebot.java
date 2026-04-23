package Gestion_Reservation;

import Configuration_espace.Cabine;

/**
 * Réservation spécialisée pour un paquebot.
 * Un paquebot réserve une cabine plutôt qu'un siège.
 */
public class ReservationPaquebot extends Reservation {
    private Cabine cabineReservee;

    public ReservationPaquebot() {
        super();
    }

    public ReservationPaquebot(String numReservation, Cabine cabineReservee) {
        super();
        this.numReservation = numReservation;
        this.cabineReservee = cabineReservee;
    }

    public Cabine getCabineReservee() {
        return cabineReservee;
    }

    public void setCabineReservee(Cabine cabineReservee) {
        this.cabineReservee = cabineReservee;
    }

    public boolean aUneCabineReservee() {
        return cabineReservee != null;
    }

    @Override
    public String toString() {
        return "ReservationPaquebot{" +
                "numReservation='" + numReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", etat=" + etat +
                ", cabineReservee=" + cabineReservee +
                '}';
    }
}
