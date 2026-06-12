package fr.uge.but.schtroumpf.model;

import java.util.Random;

/**
 * Le Schtroumpf Bricoleur — specialiste des outils et de la defense.
 */
public class SmurfHandyman implements Character {

    private static final String NAME = "Schtroumpf Bricoleur";
    private final Random random;

    public SmurfHandyman() {
        this.random = new Random();
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * L'action "Fabriquer un piege" est indisponible si Outils == 0.
     */
    @Override
    public String[] getAvailableActions(SmurfVillage village) {
        String[] actions = new String[3];
        actions[0] = "Reparer les maisons    (+1 Outils, -1 Salsepareille)";

        if (village.getResource(ResourceType.TOOLS) > 0) {
            actions[1] = "Fabriquer un piege     (+2 Defense, -1 Outils)";
        } else {
            actions[1] = "Fabriquer un piege     [INDISPONIBLE — Outils insuffisants]";
        }

        actions[2] = "Inventer un gadget     (Effet aleatoire)";
        return actions;
    }

    @Override
    public void performAction(int actionIndex, SmurfVillage village) {
        switch (actionIndex) {
            case 0 -> repairsHouses(village);
            case 1 -> {
                if (village.getResource(ResourceType.TOOLS) > 0) {
                    buildsTrap(village);
                }
            }
            case 2 -> inventsGadget(village);
            default -> throw new IllegalArgumentException("Action invalide : " + actionIndex);
        }
    }

    private void repairsHouses(SmurfVillage v) {
        v.modifyResource(ResourceType.TOOLS, 1);
        v.modifyResource(ResourceType.SMURFBERRY, -1);
    }

    private void buildsTrap(SmurfVillage v) {
        v.modifyResource(ResourceType.DEFENSE, 2);
        v.modifyResource(ResourceType.TOOLS, -1);
    }

    private void inventsGadget(SmurfVillage v) {
        ResourceType[] types = ResourceType.values();
        ResourceType target = types[random.nextInt(types.length)];
        int value = random.nextBoolean() ? 1 : -1;
        v.modifyResource(target, value);
    }
}
