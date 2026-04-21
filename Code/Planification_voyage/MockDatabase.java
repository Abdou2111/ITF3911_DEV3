package Planification_voyage;

import Configuration_espace.*;
import Gestion_Transport.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MockDatabase {
    public static void initialiserDonnees(VoyageDataStructure db) {
        try {
            File myObj = new File("Code/database.txt");
            Scanner reader = new Scanner(myObj);
            
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.isEmpty()) continue;
                String[] parts = data.split(";");
                
                Voyage v = creerInstanceVoyage(parts[0], parts[2], parts[3]);
                if (v == null) continue;

                v.setVoyageID(parts[1]);
                v.setDepart(LocalDateTime.parse(parts[4]));
                v.setArrivee(LocalDateTime.parse(parts[5]));

                if (parts.length > 6 && !parts[6].isEmpty()) {
                    for (String s : parts[6].split(",")) {
                        if (v instanceof Trajet) v.addEscale(new Gare(s));
                        else if (v instanceof Itineraire) v.addEscale(new Port(s));
                    }
                }

                if (parts.length > 7) {
                    for (String sectData : parts[7].split("/")) {
                        v.getSections().add(genererContenuSection(v, sectData));
                    }
                }
                db.addVoyage(v);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement : " + e.getMessage());
        }
    }

    private static Section genererContenuSection(Voyage v, String data) {
        String[] p = data.split(":");
        String nomType = p[0];
        
       
        Disposition disp = mapLettreADisposition(p[1]); 
        
        double prix = Double.parseDouble(p[2]);
        int capacite = Integer.parseInt(p[3]);

        if (v instanceof Itineraire) {
            SectionPaquebot sp = new SectionPaquebot("S-" + nomType);
            sp.setClasse(ClassePaquebot.valueOf(nomType));
            sp.setPrix(prix);
            //sp.setNbrePlace(capacite);
            for (int i = 1; i <= capacite; i++) {
                Cabine c = new Cabine(nomType.substring(0,1) + "-" + i);
                if (i % 5 == 0) c.actionner();
                sp.addCabine(c);
            }
            return sp;
        } else {
            SectionAvionTrain sat = new SectionAvionTrain("S-" + nomType);
            sat.setClasse(ClasseAT.valueOf(nomType));
            sat.setDisposition(disp);
            sat.setPrix(prix);
            //sat.setNbrePlace(capacite);

            int colonnes = getNbColonnes(disp);
            int rangees = (int) Math.ceil((double) capacite / colonnes);

            for (int r = 1; r <= rangees; r++) {
                for (int c = 0; c < colonnes; c++) {
                    if (((r-1) * colonnes + c) >= capacite) break;
                    char lettre = (char) ('A' + c);
                    Siege s = new Siege(nomType.substring(0,1) + "-" + r + lettre);
                    if (r % 2 == 0) s.actionner(); 
                    sat.addSiege(s);
                }
            }
            return sat;
        }
    }

    
    private static Disposition mapLettreADisposition(String lettre) {
        switch (lettre) {
            case "S": return Disposition.ETROIT;
            case "C": return Disposition.CONFORT;
            case "M": return Disposition.MOYEN;
            case "L": return Disposition.LARGE;
            default: return Disposition.ETROIT;
        }
    }

    private static int getNbColonnes(Disposition d) {
        if (d == null) return 3;
        switch(d) {
            case ETROIT: return 3;
            case CONFORT: return 4;
            case MOYEN: return 6;
            case LARGE: return 10;
            default: return 3;
        }
    }

    private static Voyage creerInstanceVoyage(String type, String o, String d) {
        if (type.equals("VOL")) { Vol v = new Vol(); v.setOrigine(new Aeroport(o)); v.setDestination(new Aeroport(d)); return v; }
        if (type.equals("TRAJET")) { Trajet t = new Trajet(); t.setOrigine(new Gare(o)); t.setDestination(new Gare(d)); return t; }
        if (type.equals("ITINERAIRE")) { Itineraire i = new Itineraire(); i.setOrigine(new Port(o)); i.setDestination(new Port(d)); return i; }
        return null;
    }
}