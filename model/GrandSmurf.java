package fr.uge.but.schtroumpf.model;

import java.util.Random;

/**
 * Le Grand Schtroumpf — Chef du Village.
 * Actions : organiser une réunion, consulter le grimoire, négocier avec les animaux.
 */
public class GrandSmurf implements Character {

    private static final String NAME = "Grand Schtroumpf";
    private final Random random = new Random();

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Les 3 actions du Grand Schtroumpf sont toujours disponibles.
     */
    @Override
    public String[] getAvailableActions(SmurfVillage village) {
        return new String[]{
            "Organiser une réunion      (+2 Moral)",
            "Consulter le grimoire      (+1 Savoir, -1 Moral si échec)",
            "Négocier avec les animaux  (+1 Or ou +1 Défense)"
        };
    }

    /**
     * Exécute l'action choisie selon son index.
     */
    @Override
    public void performAction(int actionIndex, SmurfVillage village) {
        switch (actionIndex) {
            case 0 -> organizesMeeting(village);
            case 1 -> consultsGrimoire(village);
            case 2 -> negotiatesWithAnimals(village);
            default -> throw new IllegalArgumentException(
                "Action invalide : " + actionIndex
            );
        }
    }

    /**
     * Organiser une réunion — +2 Moral garanti.
     */
    private void organizesMeeting(SmurfVillage village) {
        village.modifyResource(ResourceType.MORALE, 2);
    }

    /**
     * Consulter le grimoire — resultat aleatoire.
     */
    private void consultsGrimoire(SmurfVillage village) {
        int roll = random.nextInt(6) + 1;
        if (roll >= 4) {
            village.modifyResource(ResourceType.KNOWLEDGE, 1);
        } else {
            village.modifyResource(ResourceType.MORALE, -1);
        }
    }

    /**
     * Negocier avec les animaux — resultat aleatoire.
     */
    private void negotiatesWithAnimals(SmurfVillage village) {
        if (random.nextBoolean()) {
            village.modifyResource(ResourceType.GOLD, 1);
        } else {
            village.modifyResource(ResourceType.DEFENSE, 1);
        }
    }
}