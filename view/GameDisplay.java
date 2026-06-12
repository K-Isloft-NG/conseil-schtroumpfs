package fr.uge.but.schtroumpf.view;

import fr.uge.but.schtroumpf.model.Crisis;
import fr.uge.but.schtroumpf.model.Event;
import fr.uge.but.schtroumpf.model.ResourceType;
import java.util.List;
import java.util.Map;

/**
 * Affiche les informations du jeu dans la console.
 */
public class GameDisplay {

    public void showWelcome() {
        IO.println("=================================================");
        IO.println("   CONSEIL DES SCHTROUMPFS - Sauvez le village !");
        IO.println("=================================================");
        IO.println("Survivez 12 mois. 3 crises simultanees = defaite.");
        IO.println();
    }

    public void showTurnHeader(int turn) {
        IO.println();
        IO.println("--- TOUR " + turn + " / 12 ---");
    }

    public void showResources(Map<ResourceType, Integer> resources) {
        IO.println("Ressources du village :");
        String[] labels = {"Baies", "Salsepareille", "Or", "Outils", "Moral", "Defense", "Savoir"};
        int i = 0;
        for (ResourceType type : ResourceType.values()) {
            int val = resources.get(type);
            IO.println("  " + labels[i++] + " : " + val + "/10");
        }
    }

    public void showEvent(Event event) {
        IO.println();
        IO.println("EVENEMENT : " + event.name());
        IO.println("  " + event.description());
        if (!event.effects().isEmpty()) {
            IO.print("  Effets : ");
            event.effects().forEach((r, v) ->
                IO.print((v > 0 ? "+" : "") + v + " " + r.name() + "  "));
            IO.println();
        }
    }

    public void showCrises(List<Crisis> crises) {
        IO.println();
        IO.println("CRISES DECLENCHEES :");
        for (Crisis c : crises) {
            IO.println("  -> " + c.getDescription());
        }
        IO.println("  Crises actives ce tour : " + crises.size());
    }

    public void showVictory(int score) {
        IO.println();
        IO.println("*** VICTOIRE ! ***");
        IO.println("Le village a survecu 12 mois !");
        IO.println("Score final : " + score + " / 700");
        if      (score > 420) IO.println("Victoire triomphale !");
        else if (score >= 280) IO.println("Victoire modeste.");
        else                   IO.println("Victoire de justesse...");
    }

    public void showDefeat() {
        IO.println();
        IO.println("*** DEFAITE ! ***");
        IO.println("3 crises simultanees. Le village est perdu.");
    }
}
