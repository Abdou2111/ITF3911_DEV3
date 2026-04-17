package Patrons_technique.Commande_admin;

import java.util.Stack;

public class Invoker {
    private Stack<Command> history = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     * Exécute une commande et l'ajoute à l'historique.
     */
    public void executeCommand(Command cmd) {
        if (cmd != null) {
            cmd.execute();
            history.push(cmd);
            redoStack.clear(); 
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.redo();
            history.push(cmd);
        }
    }

    // --- Accesseurs pour l'état des piles ---
    public boolean canUndo() { return !history.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }
    public Stack<Command> getHistory() { return history; }
}