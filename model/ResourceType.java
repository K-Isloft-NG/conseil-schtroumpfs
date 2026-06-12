package fr.uge.but.schtroumpf.model;

/**
 * Enumère les 7 ressources du village des Schtroumpfs. 
 * Chaque ressource est un entier borné entre 0 et 10 dans SmurfVillage
 */

public enum ResourceType{
	BERRIES,     // Baies -- nourriture des Schtroumpfs
	SMURFBERRY,  // Salsepareille -- plante magique (soins, potions)
	GOLD,        // Or - richesse du village
	TOOLS,       // Outils - équipements pour les tâches
	MORALE,      // Moral - cohésion des Schtroumpfs
	DEFENSE,     // Defense - protection contre les menaces
	KNOWLEDGE    // Savoir - connaissances du Grand Schtroumpf
}