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
src/main/java/launcher/Launcher.java
```

## Si votre IDE ne veut pas lancer le projet, depuis le CMD, en étant dans le dossier `src`, faites :
```
> javac main/java/gui/*.java main/java/launcher/*.java main/java/model/*.java main/java/model/interfaces/*.java

> java main/java/launcher/Launcher.java 
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

Attention aux contraintes de placement des tuiles :

- Vous devez placer une tuile en restant en `contact` avec une autre tuile.

- Vous ne pouvez pas placer un `temple` à côté d'un autre `temple`.

Pas d'inquiétude, ces cas sont pris en compte dans le programme (pas de bugs).


## Création du plateau automatique

Vous avez donc choisi une création du plateau automatique, il ne vous manque plus qu'à placer les pions autour des temples, et de commencer à jouer.

# Groupe

- `ABICHOU` Roua
- `BENTRAH` Ines
- `CHEKOUR` Gwenaelle
- `TANG` Li Wa
- `YAZICI` Servan