package fr.uge.but.schtroumpf.model;

/**
 * Interface commune à tous les membres du Conseil des Schtroumpfs.
 */
public interface Character {

    /**
     * Retourne le nom du personnage.
     */
    String getName();

    /**
     * Retourne les actions disponibles pour ce personnage.
     * Une action peut être absente si ses conditions ne sont pas remplies.
     */
    String[] getAvailableActions(SmurfVillage village);

    /**
     * Exécute l'action correspondant à l'index donné.
     * @param actionIndex index de l'action choisie (0, 1 ou 2)
     * @param village     le village sur lequel appliquer les effets
     */
    void performAction(int actionIndex, SmurfVillage village);
}