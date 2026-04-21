package Configuration_espace;

import java.util.ArrayList;
import java.util.List;

public class SectionPaquebot extends Section {
    private ClassePaquebot classe;
    private List<Cabine> listeCabines = new ArrayList<>();

    public SectionPaquebot(String id) {
        super(id);
    }

    public int getNbCabinesOccupees() {
    int count = 0;
    for (Cabine c : listeCabines) if (!c.getDisponibilite()) count++;
    return count;
}
public int getNbTotalCabines() { return listeCabines.size(); }

    public void addCabine(Cabine c) { 
        this.listeCabines.add(c);
    }

    @Override
    public int getNbReserves() {
        int count = 0;
        for (Cabine c : listeCabines) {
            if (!c.getDisponibilite()) count++;
        }
        return count;
    }

    // --- Getters & Setters ---
    public ClassePaquebot getClasse() { return classe; }
    public void setClasse(ClassePaquebot c) { this.classe = c; }
    public List<Cabine> getListeCabines() { return listeCabines; }
}