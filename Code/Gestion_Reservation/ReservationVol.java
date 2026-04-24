package Gestion_Reservation;

public class ReservationVol extends Reservation {
    private Priorite prioritePlace;

    public Priorite getPrioritePlace() { return prioritePlace; }
    public void setPrioritePlace(Priorite p) { this.prioritePlace = p; }
}