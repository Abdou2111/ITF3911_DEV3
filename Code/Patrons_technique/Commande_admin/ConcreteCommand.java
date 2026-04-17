package Patrons_technique.Commande_admin;

import Gestion_Transport.ControllerTransport;

public class ConcreteCommand implements Command {
    private ControllerTransport receiver;
    private String actionType; // ex: "CREATE_LIEU", "DELETE_VOYAGE"
    private Object data;       // Paramètres de l'action (ex: String[] ou Voyage)
    private Object backupState; // Stocke l'ID ou l'objet pour l'undo

    public ConcreteCommand(ControllerTransport receiver, String actionType, Object data) {
        this.receiver = receiver;
        this.actionType = actionType;
        this.data = data;
    }

    @Override
    public void execute() {
        switch (actionType) {
            case "CREATE_LIEU":
                // data est ici un String[] {type, nom, ville, pays}
                String[] d = (String[]) data;
                receiver.createLieu(d[0], d[1], d[2], d[3]);
                this.backupState = d[1]; // On mémorise le sigle pour le undo
                break;
            case "DELETE_VOYAGE":
                this.backupState = data; // On mémorise l'ID avant suppression
                receiver.deleteVoyage((String) data);
                break;
        }
    }

    @Override
    public void undo() {
        System.out.println("Annulation de l'action : " + actionType);
        if (actionType.equals("CREATE_LIEU")) {
            // L'inverse de créer un lieu est de le supprimer (logique à ajouter au contrôleur)
            System.out.println("Lieu " + backupState + " retiré de l'historique.");
        } else if (actionType.equals("DELETE_VOYAGE")) {
            // L'inverse de supprimer est de recréer (nécessite les données complètes)
            System.out.println("Voyage " + backupState + " restauré.");
        }
    }

    @Override
    public void redo() {
        execute();
    }

    // --- Getters & Setters ---
    public ControllerTransport getReceiver() { return receiver; }
    public void setReceiver(ControllerTransport r) { this.receiver = r; }
    public String getActionType() { return actionType; }
    public void setActionType(String type) { this.actionType = type; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}