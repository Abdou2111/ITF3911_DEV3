package Gestion_paiement;

import java.time.LocalDateTime;


public class Paiement {
    private String numConfirmation;
    private double montant;
    private LocalDateTime datePaiement;
    private String numPassportClient;


    public Paiement() {
    }

    public Paiement(double montant, String numPassportClient) {
        this.montant = montant;
        this.numPassportClient = numPassportClient;
    }

    /**
     * Valide la transaction et génère un numéro de confirmation unique.
     * Cette opération est le déclencheur pour confirmer une réservation.
     */
    public boolean effectuerPaiement() {
        
        if (this.montant > 0 && this.numPassportClient != null) {
            this.numConfirmation = "CONF-" + System.currentTimeMillis();
            this.datePaiement = LocalDateTime.now();
            return true; 
        }
        return false;
    }

    // --- Getters & Setters ---

    public String getNumConfirmation() {
        return numConfirmation;
    }

    public void setNumConfirmation(String numConfirmation) {
        this.numConfirmation = numConfirmation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getNumPassportClient() {
        return numPassportClient;
    }

    public void setNumPassportClient(String numPassportClient) {
        this.numPassportClient = numPassportClient;
    }
}