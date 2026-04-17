package Configuration_espace;

import java.util.ArrayList;
import java.util.List;

public class SectionAvionTrain extends Section {
    private ClasseAT classe;
    private Disposition disposition;
    private List<Siege> listeSieges = new ArrayList<>();

    public SectionAvionTrain(String id) {
        super(id);
    }
    public int getNbSiegesOccupes() {
        int count = 0;
        for (Siege s : listeSieges) if (!s.getDisponibilite()) count++;
        return count;
    }
    public int getNbTotalSieges() { return listeSieges.size(); }

    public void addSiege(Siege s) { // Ajout de la méthode manquante
            this.listeSieges.add(s);
        }

    @Override
    public int getNbReserves() {
        int count = 0;
        for (Siege s : listeSieges) {
            if (!s.getDisponibilite()) count++; // Si non dispo = réservé
        }
        return count;
    }

    public boolean createSectionAT(ClasseAT classe, Disposition disposition, int nbRangees, double prix) {
        this.classe = classe;
        this.disposition = disposition;
        this.setPrix(prix);
        return true;
    }

    // --- Getters & Setters ---
    public ClasseAT getClasse() { return classe; }
    public void setClasse(ClasseAT c) { this.classe = c; }
    public Disposition getDisposition() { return disposition; }
    public void setDisposition(Disposition d) { this.disposition = d; }
    public List<Siege> getListeSieges() { return listeSieges; }
}