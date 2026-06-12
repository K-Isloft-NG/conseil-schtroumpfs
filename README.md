# Conseil des Schtroumpfs

Jeu de rôle développé en Java/JavaFX dans le cadre de la SAÉ 2.01
du BUT Informatique à l'IUT de Marne-la-Vallée.

## Présentation

Simulation d'un conseil de Schtroumpfs où chaque personnage a un rôle,
des capacités et un comportement défini. Le joueur interagit avec
l'interface JavaFX pour faire progresser la partie.

## Fonctionnalités

- Architecture MVC complète
- POO avancée : interfaces, enums, records, collections
- Interface graphique JavaFX
- Versioning Git et travail en équipe (4-5 personnes)

## Stack technique

- Java 17+
- JavaFX (interface graphique)
- Architecture MVC
- Git (versioning)

## Lancer le projet

```bash
# Compiler
javac --module-path /chemin/vers/javafx/lib \
  --add-modules javafx.controls,javafx.fxml \
  src/*.java

# Lancer
java --module-path /chemin/vers/javafx/lib \
  --add-modules javafx.controls,javafx.fxml \
  Main
```

## Équipe

Projet réalisé en équipe de 4 — SAÉ 2.01 · BUT 1 Informatique · 2026