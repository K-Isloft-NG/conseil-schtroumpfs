package fr.uge.but.schtroumpf.model;

import java.util.Random;

/**
 * Le Schtroumpf Gourmand — spécialiste des Baies et du Moral.
 */
public class GluttonousSmurf implements Character {

    private static final String NAME = "Schtroumpf Gourmand";
    private final Random random = new Random();

    @Override
    public String getName() { return NAME; }

    @Override
    public String[] getAvailableActions(SmurfVillage village) {
        String festin = village.getResource(ResourceType.BERRIES) > 1
            ? "Organiser un festin        (-2 Baies, +2 Moral)"
            : "Organiser un festin        [INDISPONIBLE — Baies insuffisantes]";
        return new String[]{
            "Cueillir des baies          (+2 Baies)",
            festin,
            "Trouver un champignon rare  (bonus aleatoire)"
        };
    }

    @Override
    public void performAction(int actionIndex, SmurfVillage village) {
        switch (actionIndex) {
            case 0 -> picksBerries(village);
            case 1 -> { if (village.getResource(ResourceType.BERRIES) > 1) organizesFeast(village); }
            case 2 -> findsRareMushroom(village);
            default -> throw new IllegalArgumentException("Action invalide : " + actionIndex);
        }
    }

    private void picksBerries(SmurfVillage v)    { v.modifyResource(ResourceType.BERRIES, 2); }
    private void organizesFeast(SmurfVillage v)  { v.modifyResource(ResourceType.BERRIES, -2); v.modifyResource(ResourceType.MORALE, 2); }
    private void findsRareMushroom(SmurfVillage v) {
        ResourceType[] types = ResourceType.values();
        v.modifyResource(types[random.nextInt(types.length)], random.nextInt(2) + 1);
    }
}
