package fr.uge.but.schtroumpf.model;

/**
 * Représente une crise active dans le village.
 * Record Java : immuable, constructeur automatique.
 */
public record Crisis(CrisisType type, int triggerTurn) {

    /** Applique la conséquence immédiate de la crise sur le village. */
    public void applyConsequence(SmurfVillage village) {
        switch (type) {
            case FAMINE            -> village.modifyResource(ResourceType.BERRIES, -1);
            case EPIDEMIC          -> village.modifyResource(ResourceType.SMURFBERRY, -1);
            case REVOLT            -> village.modifyResource(ResourceType.MORALE, -1);
            case MASSIVE_ATTACK    -> {
                village.modifyResource(ResourceType.DEFENSE, -2);
                village.modifyResource(ResourceType.MORALE, -1);
            }
            case FORGOTTEN_RECIPES -> village.modifyResource(ResourceType.BERRIES, -1);
        }
    }

    /** Retourne une description lisible pour l'affichage. */
    public String getDescription() {
        return switch (type) {
            case FAMINE            -> "FAMINE — manque de nourriture (-1 Baies)";
            case EPIDEMIC          -> "EPIDEMIE — plus de soins (-1 Salsepareille)";
            case REVOLT            -> "REVOLTE — le moral s'effondre (-1 Moral)";
            case MASSIVE_ATTACK    -> "ATTAQUE MASSIVE — le village est pris d'assaut (-2 Defense, -1 Moral)";
            case FORGOTTEN_RECIPES -> "OUBLI DES RECETTES — la nourriture se perd (-1 Baies)";
        };
    }
}
