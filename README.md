# Projet - Niwa

Bienvenue sur notre projet, fait sous Java.

# Quel est le but du projet ?

En groupe de 5, notre objectif était d'implémenter un jeu de plateau nommé Niwa.
Les règles de ce jeu de plateau sont entièrement expliquées dans le fichier PDF ci-dessous : [PDF - Règles Niwa](https://www.ludism.fr/wp-content/uploads/2022/01/DJ08489_FR.pdf)

# Comment lancer le jeu ?

Il suffit de `run` la classe `NiwaWindow.java`. Voici l'emplacement :
```
src/main/java/gui/NiwaWindow.java
```
Ou bien la classe `Launcher.java` située dans un dossier propre à lui :
```
src/main/java/gui/Launcher.java
```

# Comment jouer ?

Lorsque le menu est ouvert, vous pouvez naviguer comme bon vous semble, tout se fait à la souris.
Une fois que vous aurez choisi tous vos paramètres, vous pourrez lancer le jeu avec son plateau.

## Création du plateau à la main

Si vous avez décider de créer votre plateau à la main, voici les choses à savoir :

- `E`, `S`, `D`, `F` pour déplacer les tuiles.

- `ESPACE (première fois)` pour placer la tuile et passer à sa rotation.

- `S`, `F` pour effectuer une rotation sur la tuile posée `(après avoir appuyer une fois sur ESPACE)`.

- `ESPACE (deuxième fois)` pour confirmer l'emplacement de la tuile ainsi que sa rotation.

- `R` pour placer un temple

## Création du plateau automatique