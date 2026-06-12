package fr.uge.but.schtroumpf.model;

/**
 * Énumère les 5 types de crises possibles.
 * Chaque type correspond à une ressource tombée à 0.
 */
public enum CrisisType {
    FAMINE,            // Baies à 0
    EPIDEMIC,          // Salsepareille à 0
    REVOLT,            // Moral à 0
    MASSIVE_ATTACK,    // Défense à 0
    FORGOTTEN_RECIPES  // Savoir à 0
}
