import Gestion_Transport.LieuTransport;
import Planification_voyage.Itineraire;
import Planification_voyage.Trajet;
import Planification_voyage.Vol;
import Planification_voyage.Voyage;
import Planification_voyage.VoyageDataStructure;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainApp extends Application {
    private static final Path DATABASE_PATH = Paths.get("Code/database.txt");
    private static final Map<String, String> RAW_LINES_BY_ID = new LinkedHashMap<>();

    private final VoyageDataStructure db = new VoyageDataStructure();

    @Override
    public void start(Stage primaryStage) {
        bootstrapDatabase(db);

        Label title = new Label("Système de gestion de voyages");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label subtitle = new Label(
                "Réservez des voyages, consultez les itinéraires, et gérez les données de voyage. ");
        subtitle.setWrapText(true);

        Button adminButton = new Button("Ouvrir une fenêtre Admin");
        adminButton.setMaxWidth(Double.MAX_VALUE);
        adminButton.setOnAction(e -> new AdminGUI(db).show());

        Button clientButton = new Button("Ouvrir une fenêtre Client");
        clientButton.setMaxWidth(Double.MAX_VALUE);
        clientButton.setOnAction(e -> new ClientGUI(db).show());

        VBox root = new VBox(12, title, subtitle, adminButton, clientButton);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 420, 220);
        primaryStage.setTitle("Voyage System - Main Window");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(220);
        primaryStage.show();
    }

    public static void bootstrapDatabase(VoyageDataStructure db) {
        RAW_LINES_BY_ID.clear();

        if (!Files.exists(DATABASE_PATH)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(DATABASE_PATH, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                Voyage voyage = parseLineToVoyage(line.trim());
                if (voyage != null) {
                    db.addVoyage(voyage);
                    String id = safeString(voyage.getVoyageID());
                    if (!id.isEmpty()) {
                        RAW_LINES_BY_ID.put(id, line.trim());
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    public static String formatVoyage(Voyage voyage) {
        String id = safeString(voyage.getVoyageID());
        if (!id.isEmpty() && RAW_LINES_BY_ID.containsKey(id)) {
            return formatRawLine(RAW_LINES_BY_ID.get(id));
        }

        String type = voyage.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        String depart = safeString(voyage.getOrigine());
        String destination = safeString(voyage.getDestination());
        String dateDepart = safeString(voyage.getDepart());
        String dateArrivee = safeString(voyage.getArrivee());

        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (!id.isEmpty()) sb.append(" | ID: ").append(id);
        if (!depart.isEmpty() || !destination.isEmpty()) {
            sb.append(" | ").append(emptyAsUnknown(depart)).append(" -> ").append(emptyAsUnknown(destination));
        }
        if (!dateDepart.isEmpty()) sb.append(" | Départ: ").append(dateDepart);
        if (!dateArrivee.isEmpty()) sb.append(" | Arrivée: ").append(dateArrivee);

        String escales = safeString(voyage.getEscales());
        if (!escales.isEmpty()) sb.append(" | Escales: ").append(escales);

        String sections = safeString(voyage.getSections());
        if (!sections.isEmpty()) sb.append("\nSections:\n").append(formatSections(sections));
        return sb.toString();
    }

    private static String formatSections(String rawSections) {
        if (rawSections == null || rawSections.isBlank()) {
            return "Aucune section";
        }

        StringBuilder sb = new StringBuilder();

        String[] sections = rawSections.split("/");

        for (String section : sections) {
            String[] detail = section.split(":");

            if (detail.length >= 4) {
                String nom = detail[0].trim();
                String type = detail[1].trim();
                String prix = detail[2].trim();
                String places = detail[3].trim();

                sb.append("   • ").append(nom).append("\n")
                        .append("     Type : ").append(type).append("\n")
                        .append("     Prix : ").append(prix).append(" $\n")
                        .append("     Disponibles : ").append(places).append("\n\n");
            }
        }

        return sb.toString();
    }

    public static void applyBasicVoyageData(
            Voyage voyage,
            String id,
            String depart,
            String destination,
            LocalDateTime dateDepart,
            LocalDateTime dateArrivee,
            String escales,
            String sections) {

        voyage.setVoyageID(id);
        voyage.setOrigine(new SimpleLieuTransport(depart));
        voyage.setDestination(new SimpleLieuTransport(destination));

        voyage.setDepart(dateDepart);
        voyage.setArrivee(dateArrivee);

        if (escales != null && !escales.isBlank()) {
            for (String escale : escales.split(",")) {
                String sigle = escale.trim();
                if (!sigle.isEmpty()) {
                    voyage.addEscale(new SimpleLieuTransport(sigle));
                }
            }
        }

        RAW_LINES_BY_ID.put(id, buildRawLine(voyage, escales, sections));
    }

    private static class SimpleLieuTransport extends LieuTransport {
        public SimpleLieuTransport(String sigle) {
            setSigle(sigle);
        }
    }

    public static void persistCurrentDatabase(VoyageDataStructure db) {
        List<String> lines = new ArrayList<>();
        for (Voyage voyage : db.getListeVoyages()) {
            String id = safeString(voyage.getVoyageID());
            String line = id.isEmpty() ? buildRawLine(voyage, "", "") : RAW_LINES_BY_ID.get(id);
            if (line == null || line.isEmpty()) {
                line = buildRawLine(voyage, "", "");
            }
            if (!line.isEmpty()) {
                lines.add(line);
                if (!id.isEmpty()) {
                    RAW_LINES_BY_ID.put(id, line);
                }
            }
        }

        try {
            Files.write(DATABASE_PATH, lines, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    public static boolean applyReservationToRawData(String voyageId, String sectionType) {
        if (voyageId == null || voyageId.isBlank() || sectionType == null || sectionType.isBlank()) {
            return false;
        }

        String id = voyageId.trim();
        String wantedSection = sectionType.trim().toUpperCase(Locale.ROOT);

        String line = RAW_LINES_BY_ID.get(id);
        if (line == null || line.isBlank()) {
            return false;
        }

        String[] parts = line.split(";", -1);
        if (parts.length < 8) {
            return false;
        }

        String[] sections = parts[7].split("/");

        for (int i = 0; i < sections.length; i++) {
            String[] detail = sections[i].split(":", -1);
            if (detail.length >= 4) {
                String currentSection = detail[0].trim().toUpperCase(Locale.ROOT);

                if (currentSection.equals(wantedSection)) {
                    try {
                        int places = Integer.parseInt(detail[3].trim());
                        if (places <= 0) {
                            return false;
                        }

                        detail[3] = String.valueOf(places - 1);
                        sections[i] = String.join(":", detail);
                        parts[7] = String.join("/", sections);

                        RAW_LINES_BY_ID.put(id, String.join(";", parts));
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public static List<String> getSectionsForVoyage(String voyageId) {
        List<String> result = new ArrayList<>();

        if (voyageId == null || voyageId.isBlank()) {
            return result;
        }

        String line = RAW_LINES_BY_ID.get(voyageId.trim());
        if (line == null || line.isBlank()) {
            return result;
        }

        String[] parts = line.split(";", -1);
        if (parts.length < 8 || parts[7].isBlank()) {
            return result;
        }

        String[] sections = parts[7].split("/");
        for (String section : sections) {
            String[] detail = section.split(":", -1);
            if (detail.length >= 4) {
                String nomSection = detail[0].trim();
                String disponibilite = detail[3].trim();
                result.add(nomSection + " (" + disponibilite + " places restantes)");
            }
        }

        return result;
    }

    public static String extractSectionName(String comboValue) {
        if (comboValue == null || comboValue.isBlank()) {
            return "";
        }

        int idx = comboValue.indexOf(" (");
        if (idx > 0) {
            return comboValue.substring(0, idx).trim();
        }
        return comboValue.trim();
    }

    private static Voyage parseLineToVoyage(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length < 6) {
            return null;
        }

        Voyage voyage = switch (parts[0].trim().toUpperCase(Locale.ROOT)) {
            case "TRAJET" -> new Trajet();
            case "ITINERAIRE" -> new Itineraire();
            default -> new Vol();
        };

        String id = parts[1].trim();
        String depart = parts[2].trim();
        String destination = parts[3].trim();
        LocalDateTime dateDepart = parseDate(parts[4].trim());
        LocalDateTime dateArrivee = parseDate(parts[5].trim());
        String escales = parts.length > 6 ? parts[6].trim() : "";
        String sections = parts.length > 7 ? parts[7].trim() : "";

        applyBasicVoyageData(voyage, id, depart, destination, dateDepart, dateArrivee, escales, sections);
        return voyage;
    }

    private static String buildRawLine(Voyage voyage, String defaultEscales, String defaultSections) {
        String type = voyage == null ? "VOL" : voyage.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        if (type.contains("ITINERAIRE")) type = "ITINERAIRE";
        else if (type.contains("TRAJET")) type = "TRAJET";
        else type = "VOL";

        String id = safeString(voyage.getVoyageID());
        String depart = safeString(voyage.getOrigine());
        String destination = safeString(voyage.getDestination());
        String dateDepart = safeString(voyage.getDepart());
        String dateArrivee = safeString(voyage.getArrivee());

        String escales = defaultEscales == null ? "" : defaultEscales.trim();
        String sections = defaultSections == null ? "" : defaultSections.trim();
        if (sections.isEmpty()) {
            sections = defaultSectionsFor(type);
        }

        return String.join(";",
                type,
                nullToEmpty(id),
                nullToEmpty(depart),
                nullToEmpty(destination),
                nullToEmpty(dateDepart),
                nullToEmpty(dateArrivee),
                escales,
                sections);
    }

    private static String defaultSectionsFor(String type) {
        return switch (type) {
            case "ITINERAIRE" -> "INTERIEURE:NA:1500:50/SUITE:NA:4500:10";
            case "TRAJET" -> "PREMIERE:S:180:20/ECONOMIQUE:S:90:80";
            default -> "PREMIERE:L:1200:20/ECONOMIQUE:M:250:60";
        };
    }

    private static LocalDateTime parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(value.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static String formatRawLine(String line) {
        String[] parts = line.split(";", -1);
        String type = parts.length > 0 ? parts[0] : "VOYAGE";
        String id = parts.length > 1 ? parts[1] : "";
        String depart = parts.length > 2 ? parts[2] : "";
        String destination = parts.length > 3 ? parts[3] : "";
        String dateDepart = parts.length > 4 ? parts[4] : "";
        String dateArrivee = parts.length > 5 ? parts[5] : "";
        String escales = parts.length > 6 ? parts[6] : "";
        String sections = parts.length > 7 ? parts[7] : "";

        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" | ID: ").append(id)
                .append(" | ").append(emptyAsUnknown(depart)).append(" -> ").append(emptyAsUnknown(destination));
        if (!dateDepart.isEmpty()) sb.append(" | Départ: ").append(dateDepart);
        if (!dateArrivee.isEmpty()) sb.append(" | Arrivée: ").append(dateArrivee);
        if (!escales.isEmpty()) sb.append(" | Escales: ").append(escales);
        if (!sections.isEmpty()) sb.append(" | Sections: ").append(sections);
        return sb.toString();
    }

    private static String safeString(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String emptyAsUnknown(String value) {
        return value == null || value.isBlank() ? "?" : value;
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
