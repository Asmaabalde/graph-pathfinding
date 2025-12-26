README – Mini-Projet d’Algorithmique Avancée
Auteur : BALDE Asmaou
Année : 2025‑2026

1) Présentation
Ce programme constitue le rendu du mini-projet d’Algorithmique Avancée.
Il permet :
– de charger une image
– de sélectionner deux pixels (départ et arrivée)
– de calculer le plus court chemin entre ces deux points dans le graphe image
– de visualiser le chemin sur l’image
– d’afficher des métriques (longueur du chemin, nombre de pixels explorés).

L’image est modélisée comme un graphe non orienté valué, où chaque pixel est un sommet, et les arêtes sont pondérées par la différence d’intensité.

2) Exécution du programme
Le fichier exécutable fourni est :
ImageShortestPath.jar

Pour lancer l’application (Windows, Linux, macOS) :
java -jar ImageShortestPath.jar

L’interface graphique s’ouvre automatiquement.

3) Utilisation de l’interface
1. Cliquer sur « Charger une image »
2. Sélectionner deux pixels (départ en vert, arrivée en rouge)
3. Le chemin optimal s’affiche en jaune sur l’image
4. Les métriques s’affichent :
   – coordonnées du départ et de l’arrivée
   – longueur du chemin
   – nombre de pixels explorés.

4) Fichiers de sortie
Aucun fichier texte n’est généré automatiquement.
Le programme se concentre sur la visualisation directe du chemin et des métriques dans l’interface.

5) Structure du projet
CodeMiniProjet/
├── imagegraph/          → structure du graphe image (PixelNode, ImageGraph, Dijkstra, etc.)
├── ui/                  → interface graphique (ImagePanel, MainWindow, etc.)
└── images/              → images de test (ex. Mona Lisa, 16x16, etc.)

6) Compatibilité
Le programme a été testé sur :
– Windows 10 / 11
– Ubuntu 22.04

L’affichage peut varier selon l’OS, mais l’interface reste fonctionnelle et adaptable.

