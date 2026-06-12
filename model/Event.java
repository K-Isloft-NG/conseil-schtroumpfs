package fr.uge.but.schtroumpf.model;

import java.util.Map;
import java.util.Random;

/**
 * Represente un evenement aleatoire du jeu.
 * Record Java : immuable, constructeur automatique.
 */
public record Event(String name, String description, Map<ResourceType, Integer> effects) {

    private static final Random RANDOM = new Random();

    /**
     * Applique chaque effet de la map sur le village.
     */
    public void applyEffects(SmurfVillage village) {
        for (Map.Entry<ResourceType, Integer> effect : effects.entrySet()) {
            village.modifyResource(effect.getKey(), effect.getValue());
        }
    }

    /**
     * Tire un evenement aleatoire parmi les 8 definis.
     * Poids : [20, 15, 10, 10, 15, 10, 10, 10] = 100
     */
    public static Event createRandom() {
        int roll = RANDOM.nextInt(100);

        if (roll < 20) {
            return new Event(
                "Attaque de Gargamel",
                "Gargamel rode pres du village. Les defenses et le moral sont menaces !",
                Map.of(ResourceType.DEFENSE, -2, ResourceType.MORALE, -1));
        } else if (roll < 35) {
            return new Event(
                "Decouverte de baies magiques",
                "Un bosquet de baies magiques a ete decouvert pres de la riviere !",
                Map.of(ResourceType.BERRIES, 2, ResourceType.SMURFBERRY, 1));
        } else if (roll < 45) {
            return new Event(
                "Visite d'un village ami",
                "Des voisins amicaux arrivent avec des cadeaux. Le moral remonte !",
                Map.of(ResourceType.GOLD, 2, ResourceType.MORALE, 1));
        } else if (roll < 55) {
            return new Event(
                "Tempete de Salsepareille",
                "Une violente tempete endommage les outils mais le Grand Schtroumpf en tire des lecons.",
                Map.of(ResourceType.TOOLS, -1, ResourceType.KNOWLEDGE, 1));
        } else if (roll < 70) {
            return new Event(
                "Fete des Schtroumpfs",
                "Les Schtroumpfs organisent une grande fete ! Le moral est au beau fixe.",
                Map.of(ResourceType.MORALE, 2, ResourceType.BERRIES, -1));
        } else if (roll < 80) {
            return new Event(
                "Malediction de la foret",
                "Une magie noire se repand. Le savoir du village se brouille.",
                Map.of(ResourceType.KNOWLEDGE, -2));
        } else if (roll < 90) {
            return new Event(
                "Pluie de champignons",
                "Des champignons poussent partout ! La nourriture et le commerce en profitent.",
                Map.of(ResourceType.BERRIES, 1, ResourceType.GOLD, 1));
        } else {
            return new Event(
                "Calme plat",
                "Rien de particulier ne se produit. Le village vit une journee paisible.",
                Map.of());
        }
    }
}
