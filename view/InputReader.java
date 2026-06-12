package fr.uge.but.schtroumpf.view;

import fr.uge.but.schtroumpf.model.Character;
import fr.uge.but.schtroumpf.model.Difficulty;
import java.util.List;

/**
 * Lit les saisies clavier de l'utilisateur.
 */
public class InputReader {

    /**
     * Affiche la liste des personnages et retourne celui choisi.
     */
    public Character chooseCharacter(List<Character> council) {
        IO.println("\nChoisissez un personnage :");
        for (int i = 0; i < council.size(); i++) {
            IO.println("  " + (i + 1) + ". " + council.get(i).getName());
        }
        return council.get(readInt(1, council.size()) - 1);
    }

    /**
     * Affiche les actions disponibles et retourne l'index choisi (base 0).
     */
    public int chooseAction(String[] actions) {
        IO.println("Choisissez une action :");
        for (int i = 0; i < actions.length; i++) {
            IO.println("  " + (i + 1) + ". " + actions[i]);
        }
        return readInt(1, actions.length) - 1;
    }

    /**
     * Retourne le niveau de difficulte choisi par le joueur.
     */
    public Difficulty readDifficulty() {
        IO.println("Choisissez la difficulte :");
        IO.println("  1. Facile   (ressources a 7)");
        IO.println("  2. Normal   (ressources a 5)");
        IO.println("  3. Difficile (ressources a 3)");
        int choice = readInt(1, 3);
        return switch (choice) {
            case 1 -> Difficulty.EASY;
            case 3 -> Difficulty.HARD;
            default -> Difficulty.NORMAL;
        };
    }

    /**
     * Lit un entier valide entre min et max.
     * Redemande en cas de saisie invalide.
     */
    private int readInt(int min, int max) {
        int value = -1;
        while (value < min || value > max) {
            IO.print("Votre choix (" + min + "-" + max + ") : ");
            try {
                String line = IO.readln();
                value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    IO.println("Choix invalide. Recommencez.");
                }
            } catch (NumberFormatException e) {
                IO.println("Erreur : entrez un nombre.");
            }
        }
        return value;
    }
}
