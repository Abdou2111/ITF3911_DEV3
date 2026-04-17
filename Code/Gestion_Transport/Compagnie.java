package Gestion_Transport;

import java.util.ArrayList;
import java.util.List;
import Utilisateurs.ProfilVisiteur;

public abstract class Compagnie {
    protected String companyID;
    protected double prixPleinTarif;
    protected String nomCompany;
    protected List<ProfilVisiteur> observateurs = new ArrayList<>();

    public void attach(ProfilVisiteur p) { if(p != null) observateurs.add(p); }
    public void notifyObservers() { for(ProfilVisiteur p : observateurs) p.update(); }

    public double getPrixPleinTarif() { return prixPleinTarif; }
    public void setPrixPleinTarif(double p) { this.prixPleinTarif = p; notifyObservers(); }
    public String getNomCompany() { return nomCompany; }
    public void setNomCompany(String n) { this.nomCompany = n; notifyObservers(); }
    public String getCompanyID() { return companyID; }
    public void setCompanyID(String id) { this.companyID = id; }
}