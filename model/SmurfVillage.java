package fr.uge.but.schtroumpf.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Représente le village des Schtroumpfs.
 * Gère les ressources, le conseil et les crises actives.
 */
public class SmurfVillage {

    /** Les 7 ressources du village (valeurs entre 0 et 10). */
    private final Map<ResourceType, Integer> resources;

    /** Les 4 personnages du conseil. */
    private final List<Character> council;

    /** Les crises actives ce tour. */
    private final List<Crisis> activeCrises;

    /**
     * Initialise le village selon la difficulté.
     * Facile = 7, Normal = 5, Difficile = 3.
     */
    public SmurfVillage(Difficulty difficulty) {
        resources    = new EnumMap<>(ResourceType.class);
        activeCrises = new ArrayList<>();

        int initialValue = switch (difficulty) {
            case EASY   -> 7;
            case NORMAL -> 5;
            case HARD   -> 3;
        };
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, initialValue);
        }

        council = new ArrayList<>();
        council.add(new GrandSmurf());
        council.add(new SmurfHandyman());
        council.add(new GluttonousSmurf());
        council.add(new Smurfette());
    }

    /** Retourne la valeur actuelle d'une ressource. */
    public int getResource(ResourceType type) {
        return resources.get(type);
    }

    /**
     * Modifie une ressource en la maintenant entre 0 et 10.
     */
    public void modifyResource(ResourceType type, int amount) {
        int current  = resources.get(type);
        int newValue = Math.clamp(current + amount, 0, 10);
        resources.put(type, newValue);
    }

    /** Phase 1 — +1 Baies, +1 Salsepareille. */
    public void produceResources() {
        modifyResource(ResourceType.BERRIES, 1);
        modifyResource(ResourceType.SMURFBERRY, 1);
    }

    /** Phase 4 — -1 Baies. */
    public void consumeResources() {
        modifyResource(ResourceType.BERRIES, -1);
    }

    /**
     * Verifie les ressources et declenche les crises si necessaire.
     */
    public void checkCrises(int currentTurn) {
        activeCrises.clear();
        if (resources.get(ResourceType.BERRIES)    == 0)
            addCrisis(new Crisis(CrisisType.FAMINE, currentTurn));
        if (resources.get(ResourceType.SMURFBERRY) == 0)
            addCrisis(new Crisis(CrisisType.EPIDEMIC, currentTurn));
        if (resources.get(ResourceType.MORALE)     == 0)
            addCrisis(new Crisis(CrisisType.REVOLT, currentTurn));
        if (resources.get(ResourceType.DEFENSE)    == 0)
            addCrisis(new Crisis(CrisisType.MASSIVE_ATTACK, currentTurn));
        if (resources.get(ResourceType.KNOWLEDGE)  == 0)
            addCrisis(new Crisis(CrisisType.FORGOTTEN_RECIPES, currentTurn));
    }

    private void addCrisis(Crisis crisis) {
        activeCrises.add(crisis);
        crisis.applyConsequence(this);
    }

    /** Retourne le nombre de crises actives ce tour. */
    public int getActiveCrisesCount() {
        return activeCrises.size();
    }

    /** Retourne la liste des crises actives (copie). */
    public List<Crisis> getActiveCrises() {
        return new ArrayList<>(activeCrises);
    }

    /** Retourne les personnages du conseil. */
    public List<Character> getCouncil() {
        return council;
    }

    /** Retourne une copie des ressources pour affichage. */
    public Map<ResourceType, Integer> getAllResources() {
        return new EnumMap<>(resources);
    }

    /** Score final : somme de toutes les ressources × 10 (max 700). */
    public int computeScore() {
        return resources.values().stream().mapToInt(Integer::intValue).sum() * 10;
    }
}
