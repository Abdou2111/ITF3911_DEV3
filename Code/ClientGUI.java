import Planification_voyage.Voyage;
import Planification_voyage.VoyageDataStructure;
import Planification_voyage.VoyageObserver;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientGUI implements VoyageObserver {
    private final VoyageDataStructure db;
    private final Stage stage;

    private TextArea voyageList;
    private TextField filterField;
    private TextField reserveField;
    private Label countLabel;
    private Label statusLabel;
    private ComboBox<String> sectionCombo;

    public ClientGUI(VoyageDataStructure db) {
        this.db = db;
        this.stage = new Stage();
        this.db.addObserver(this);
        buildUI();
        update();
        this.stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> this.db.removeObserver(this));
    }

    private void buildUI() {
        Label title = new Label("Client");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label help = new Label("Consultez les voyages disponibles, entrez un identifiant, chargez les sections, puis réservez la section choisie.");
        help.setWrapText(true);

        filterField = new TextField();
        filterField.setPromptText("Filtrer par texte (id, ville, type, date, etc.)");
        filterField.textProperty().addListener((obs, oldVal, newVal) -> refreshVoyageList());

        voyageList = new TextArea();
        voyageList.setEditable(false);
        voyageList.setPrefRowCount(16);
        VBox.setVgrow(voyageList, Priority.ALWAYS);

        reserveField = new TextField();
        reserveField.setPromptText("Entrer l'identifiant du voyage");

        sectionCombo = new ComboBox<>();
        sectionCombo.setPromptText("Choisir une section");
        sectionCombo.setPrefWidth(260);

        Button loadSectionsButton = new Button("Charger sections");
        loadSectionsButton.setOnAction(e -> loadSectionsForVoyage());

        Button reserveButton = new Button("Réserver section");
        reserveButton.setDefaultButton(true);
        reserveButton.setOnAction(e -> reserveSection());

        Button saveButton = new Button("Sauvegarder");
        saveButton.setOnAction(e -> {
            MainApp.persistCurrentDatabase(db);
            statusLabel.setText("database.txt sauvegardé.");
        });

        Button refreshButton = new Button("Rafraîchir");
        refreshButton.setOnAction(e -> update());

        HBox actions = new HBox(8, reserveField, loadSectionsButton, sectionCombo, reserveButton, saveButton, refreshButton);
        HBox.setHgrow(reserveField, Priority.ALWAYS);

        countLabel = new Label();
        statusLabel = new Label("Prêt.");
        statusLabel.setWrapText(true);

        VBox root = new VBox(10, title, help, filterField, voyageList, actions, countLabel, statusLabel);
        root.setPadding(new Insets(14));

        Scene scene = new Scene(root, 860, 560);
        stage.setTitle("Client GUI");
        stage.setScene(scene);
    }

    private void refreshVoyageList() {
        if (voyageList == null) {
            return;
        }

        String filter = filterField == null || filterField.getText() == null
                ? ""
                : filterField.getText().trim().toLowerCase();

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Voyage v : db.getListeVoyages()) {
            String line = MainApp.formatVoyage(v);
            if (filter.isEmpty() || line.toLowerCase().contains(filter)) {
                sb.append(line).append(System.lineSeparator());
                count++;
            }
        }

        voyageList.setText(sb.toString());
        countLabel.setText("Nombre de voyages affichés : " + count);
    }

    private void loadSectionsForVoyage() {
        String id = reserveField.getText() == null ? "" : reserveField.getText().trim();
        sectionCombo.getItems().clear();

        if (id.isEmpty()) {
            statusLabel.setText("Veuillez entrer un identifiant de voyage.");
            return;
        }

        sectionCombo.getItems().addAll(MainApp.getSectionsForVoyage(id));

        if (sectionCombo.getItems().isEmpty()) {
            statusLabel.setText("Aucune section trouvée pour le voyage : " + id);
        } else {
            sectionCombo.getSelectionModel().selectFirst();
            statusLabel.setText("Sections chargées pour le voyage : " + id);
        }
    }

    private void reserveSection() {
        String id = reserveField.getText() == null ? "" : reserveField.getText().trim();
        String selected = sectionCombo.getValue();

        if (id.isEmpty()) {
            statusLabel.setText("Veuillez entrer un identifiant de voyage.");
            return;
        }

        if (selected == null || selected.isBlank()) {
            statusLabel.setText("Veuillez choisir une section.");
            return;
        }

        String sectionName = MainApp.extractSectionName(selected);
        boolean ok = MainApp.applyReservationToRawData(id, sectionName);

        if (!ok) {
            statusLabel.setText("Réservation impossible pour la section " + sectionName + " du voyage " + id + ".");
            return;
        }

        MainApp.persistCurrentDatabase(db);
        sectionCombo.getItems().setAll(MainApp.getSectionsForVoyage(id));
        if (!sectionCombo.getItems().isEmpty()) {
            sectionCombo.getSelectionModel().selectFirst();
        }

        statusLabel.setText("Réservation confirmée pour " + id + " | Section : " + sectionName);
        update();
    }

    public void show() {
        stage.show();
        stage.toFront();
    }

    @Override
    public void update() {
        refreshVoyageList();
    }
}
