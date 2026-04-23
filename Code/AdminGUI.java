import Planification_voyage.Itineraire;
import Planification_voyage.Trajet;
import Planification_voyage.Vol;
import Planification_voyage.Voyage;
import Planification_voyage.VoyageDataStructure;
import Planification_voyage.VoyageObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDateTime;

public class AdminGUI implements VoyageObserver {
    private final VoyageDataStructure db;
    private final Stage stage;

    private TextArea voyageList;
    private TextField idField;
    private TextField departField;
    private TextField destinationField;
    private TextField dateDepartField;
    private TextField dateArriveeField;
    private TextField escalesField;
    private ComboBox<String> typeCombo;
    private Label countLabel;
    private Label statusLabel;

    public AdminGUI(VoyageDataStructure db) {
        this.db = db;
        this.stage = new Stage();
        this.db.addObserver(this);
        buildUI();
        update();
        this.stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> this.db.removeObserver(this));
    }

    private void buildUI() {
        Label title = new Label("Administration");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label help = new Label("Ajoutez un voyage complet. Le fichier database.txt sera mis à jour automatiquement.");
        help.setWrapText(true);

        voyageList = new TextArea();
        voyageList.setEditable(false);
        voyageList.setPrefRowCount(14);
        VBox.setVgrow(voyageList, Priority.ALWAYS);

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("VOL", "TRAJET", "ITINERAIRE");
        typeCombo.setValue("VOL");

        idField = new TextField();
        departField = new TextField();
        destinationField = new TextField();
        dateDepartField = new TextField();
        dateArriveeField = new TextField();
        escalesField = new TextField();

        idField.setPromptText("AC999");
        departField.setPromptText("YUL");
        destinationField.setPromptText("YYZ");
        dateDepartField.setPromptText("2026-05-01T08:00");
        dateArriveeField.setPromptText("2026-05-01T09:30");
        escalesField.setPromptText("optionnel: MARS,GEN ou FREE,COCO,BIM");

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.addRow(0, new Label("Type"), typeCombo, new Label("ID"), idField);
        form.addRow(1, new Label("Départ"), departField, new Label("Destination"), destinationField);
        form.addRow(2, new Label("Date départ"), dateDepartField, new Label("Date arrivée"), dateArriveeField);
        form.addRow(3, new Label("Escales"), escalesField);

        Button addButton = new Button("Ajouter");
        addButton.setDefaultButton(true);
        addButton.setOnAction(e -> addVoyage());

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            MainApp.persistCurrentDatabase(db);
            statusLabel.setText("database.txt sauvegardé.");
        });

        Button refreshButton = new Button("Rafraîchir");
        refreshButton.setOnAction(e -> update());

        HBox actions = new HBox(8, addButton, saveButton, refreshButton);

        countLabel = new Label();
        statusLabel = new Label("Prêt.");
        statusLabel.setWrapText(true);

        VBox root = new VBox(10, title, help, voyageList, form, actions, countLabel, statusLabel);
        root.setPadding(new Insets(14));

        Scene scene = new Scene(root, 860, 560);
        stage.setTitle("Admin GUI");
        stage.setScene(scene);
    }

    private void addVoyage() {
        String type = typeCombo.getValue();
        String id = clean(idField.getText());
        String depart = clean(departField.getText());
        String destination = clean(destinationField.getText());
        String dateDepartText = clean(dateDepartField.getText());
        String dateArriveeText = clean(dateArriveeField.getText());
        String escales = clean(escalesField.getText());

        if (id.isEmpty() || depart.isEmpty() || destination.isEmpty() || dateDepartText.isEmpty() || dateArriveeText.isEmpty()) {
            statusLabel.setText("Veuillez remplir au minimum: type, ID, départ, destination, date départ, date arrivée.");
            return;
        }

        LocalDateTime dateDepart;
        LocalDateTime dateArrivee;
        try {
            dateDepart = LocalDateTime.parse(dateDepartText);
            dateArrivee = LocalDateTime.parse(dateArriveeText);
        } catch (Exception ex) {
            statusLabel.setText("Dates invalides. Format attendu: 2026-05-01T08:00");
            return;
        }

        Voyage voyage = createVoyage(type);
        MainApp.applyBasicVoyageData(voyage, id, depart, destination, dateDepart, dateArrivee, escales, "");
        db.addVoyage(voyage);
        MainApp.persistCurrentDatabase(db);

        idField.clear();
        departField.clear();
        destinationField.clear();
        dateDepartField.clear();
        dateArriveeField.clear();
        escalesField.clear();
        statusLabel.setText("Voyage ajouté et sauvegardé: " + id);
    }

    private Voyage createVoyage(String type) {
        if ("TRAJET".equals(type)) return new Trajet();
        if ("ITINERAIRE".equals(type)) return new Itineraire();
        return new Vol();
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    public void show() {
        stage.show();
        stage.toFront();
    }

    @Override
    public void update() {
        if (voyageList == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Voyage v : db.getListeVoyages()) {
            sb.append(MainApp.formatVoyage(v)).append(System.lineSeparator());
            count++;
        }

        voyageList.setText(sb.toString());
        countLabel.setText("Nombre total de voyages visibles : " + count);
    }
}
