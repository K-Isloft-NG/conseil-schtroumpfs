package fr.uge.but.schtroumpf.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gere la sauvegarde et le chargement de l'etat du jeu.
 * Format du fichier : une ligne par donnee, cle=valeur
 */
public class SaveManager {

    private static final String SAVE_FILE = "sauvegarde.txt";

    /**
     * Sauvegarde l'etat actuel du village dans sauvegarde.txt
     * @param village le village a sauvegarder
     * @param turn    le numero du tour actuel
     */
    public static void save(SmurfVillage village, int turn) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            writer.write("tour=" + turn + "\n");
            for (ResourceType type : ResourceType.values()) {
                writer.write(type.name() + "=" + village.getResource(type) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Verifie si une sauvegarde existe.
     */
    public static boolean saveExists() {
        return Files.exists(Path.of(SAVE_FILE));
    }

    /**
     * Charge la sauvegarde et applique les valeurs au village.
     * @param village le village a restaurer
     * @return le numero de tour sauvegarde, ou 1 si echec
     */
    public static int load(SmurfVillage village) {
        int savedTurn = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());

                if (key.equals("tour")) {
                    savedTurn = value;
                } else {
                    try {
                        ResourceType type = ResourceType.valueOf(key);
                        int current = village.getResource(type);
                        village.modifyResource(type, value - current);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
        return savedTurn;
    }

    /**
     * Supprime le fichier de sauvegarde (fin de partie).
     */
    public static void deleteSave() {
        try {
            Files.deleteIfExists(Path.of(SAVE_FILE));
        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}