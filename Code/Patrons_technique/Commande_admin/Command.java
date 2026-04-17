package Patrons_technique.Commande_admin;

/**
 * Interface définissant les opérations de base pour le patron Commande.
 */
public interface Command {
    void execute(); // Exécute l'action initiale
    void undo();    // Annule l'action (opération inverse)
    void redo();    // Ré-exécute l'action après une annulation
}