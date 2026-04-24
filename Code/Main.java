import Planification_voyage.*;
import Utilisateurs.*;

public class Main {
    public static void main(String[] args) {
        
        VoyageDataStructure db = new VoyageDataStructure();

        // Chargement automatique depuis le fichier texte
        MockDatabase.initialiserDonnees(db);

        // Démo d'affichage
        ProfilVisiteur admin = new AdminVisiteur();
        System.out.println("\n--- VUE ADMINISTRATEUR ---");
        db.accept(admin);

        ProfilVisiteur client = new Client();
        System.out.println("\n--- VUE CLIENT ---");
        db.accept(client);
    }
}