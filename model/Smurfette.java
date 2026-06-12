package fr.uge.but.schtroumpf.model;

import java.util.Random;

/**
 * La Schtroumpfette — diplomate du village.
 */
public class Smurfette implements Character {

    private static final String NAME = "Schtroumpfette";
    private final Random random = new Random();

    @Override
    public String getName() { return NAME; }

    @Override
    public String[] getAvailableActions(SmurfVillage village) {
        String fete = village.getResource(ResourceType.BERRIES) > 1
            ? "Organiser une fete          (-2 Baies, +2 Moral)"
            : "Organiser une fete          [INDISPONIBLE — Baies insuffisantes]";
        return new String[]{
            "Negocier avec les voisins   (+2 Or ou +2 Salsepareille)",
            "Apaiser un conflit          (+2 Moral)",
            fete
        };
    }

    @Override
    public void performAction(int actionIndex, SmurfVillage village) {
        switch (actionIndex) {
            case 0 -> negotiatesWithNeighbors(village);
            case 1 -> soothesConflict(village);
            case 2 -> { if (village.getResource(ResourceType.BERRIES) > 1) organizesFest(village); }
            default -> throw new IllegalArgumentException("Action invalide : " + actionIndex);
        }
    }

    private void negotiatesWithNeighbors(SmurfVillage v) {
        if (random.nextBoolean()) v.modifyResource(ResourceType.GOLD, 2);
        else v.modifyResource(ResourceType.SMURFBERRY, 2);
    }
    private void soothesConflict(SmurfVillage v) { v.modifyResource(ResourceType.MORALE, 2); }
    private void organizesFest(SmurfVillage v)   { v.modifyResource(ResourceType.BERRIES, -2); v.modifyResource(ResourceType.MORALE, 2); }
}
