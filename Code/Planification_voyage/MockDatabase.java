package Planification_voyage;

import Configuration_espace.*;
import Gestion_Transport.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MockDatabase {
    public static void initialiserDonnees(VoyageDataStructure db) {
        try {
            // Utilisation du chemin relatif direct
            File myObj = new File("database.txt"); 
            Scanner reader = new Scanner(myObj);
            
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.isEmpty()) continue;
                
                String[] parts = data.split(";");
                String type = parts[0];
                String id = parts[1];
                
                Voyage v = null;
                
                // Instanciation selon le type
                if (type.equals("VOL")) {
                    v = new Vol();
                    v.setOrigine(new Aeroport(parts[2]));
                    v.setDestination(new Aeroport(parts[3]));
                } else if (type.equals("TRAJET")) {
                    v = new Trajet();
                    v.setOrigine(new Gare(parts[2]));
                    v.setDestination(new Gare(parts[3]));
                } else if (type.equals("ITINERAIRE")) {
                    v = new Itineraire();
                    v.setOrigine(new Port(parts[2]));
                    v.setDestination(new Port(parts[3]));
                }
                
                if (v != null) {
                    v.setVoyageID(id);
                    v.setDepart(LocalDateTime.parse(parts[4]));
                    v.setArrivee(LocalDateTime.parse(parts[5]));
                    
                    // Gestion dynamique des sections
                    double prix = Double.parseDouble(parts[6]);
                    String sectionType = type.equals("ITINERAIRE") ? "P" : "AT";
                    v.getSections().add(creerSection(id, prix, sectionType));
                    
                    // Traitement des escales multiples (séparées par des virgules)
                    if (parts.length > 7 && !parts[7].isEmpty()) {
                        String[] escalesRaw = parts[7].split(",");
                        for (String escSigle : escalesRaw) {
                            if (v instanceof Trajet) v.addEscale(new Gare(escSigle));
                            else if (v instanceof Itineraire) v.addEscale(new Port(escSigle));
                        }
                    }
                    db.addVoyage(v);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier database.txt introuvable dans " + System.getProperty("user.dir"));
        }
    }

    private static Section creerSection(String id, double prix, String type) {
        if (type.equals("AT")) {
            SectionAvionTrain s = new SectionAvionTrain("S-" + id);
            s.setPrix(prix); s.setNbrePlace(50);
            s.setClasse(ClasseAT.ECONOMIQUE); s.setDisposition(Disposition.MOYEN);
            return s;
        } else {
            SectionPaquebot s = new SectionPaquebot("P-" + id);
            s.setPrix(prix); s.setNbrePlace(20);
            s.setClasse(ClassePaquebot.INTERIEURE);
            return s;
        }
    }
}