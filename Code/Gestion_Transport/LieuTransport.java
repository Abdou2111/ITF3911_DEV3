package Gestion_Transport;

import java.util.ArrayList;
import java.util.List;
import Utilisateurs.ProfilVisiteur;

public abstract class LieuTransport {
    protected String sigle;
    protected String ville;
    protected String pays;
    protected List<ProfilVisiteur> observateurs = new ArrayList<>();

    public LieuTransport() {}
    public LieuTransport(String sigle) { this.sigle = sigle; }

    public void attach(ProfilVisiteur p) { if(p != null) observateurs.add(p); }
    public void notifyObservers() { for(ProfilVisiteur p : observateurs) p.update(); }

    public String getSigle() { return sigle; }
    public void setSigle(String s) { this.sigle = s; notifyObservers(); }
    public String getVille() { return ville; }
    public void setVille(String v) { this.ville = v; notifyObservers(); }
    public String getPays() { return pays; }
    public void setPays(String p) { this.pays = p; notifyObservers(); }
}