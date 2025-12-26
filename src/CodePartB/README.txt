README – Partie B des TPs d’Algorithmique Avancée
Auteur : BALDE Asmaou
Année : 2025‑2026

1) Présentation
Ce programme constitue le rendu de la Partie B des TPs d’Algorithmique Avancée.
Il permet :
– de charger un fichier de carte au format .txt
– d’exécuter un algorithme de plus court chemin (Dijkstra ou A*)
– de visualiser le graphe et le chemin optimal dans une interface graphique
– de sauvegarder le résultat dans un fichier texte.

Aucune bibliothèque externe n’est utilisée : uniquement les classes standard de Java.

2) Exécution du programme
Le fichier exécutable fourni est :
GraphShortestPath.jar

Pour lancer l’application (Windows, Linux, macOS) :
java -jar GraphShortestPath.jar

L’interface graphique s’ouvre automatiquement.

3) Utilisation de l’interface
1. Cliquer sur « Charger un fichier » et sélectionner un fichier graph.txt
2. Choisir l’algorithme : Dijkstra ou A*
3. Si A* est sélectionné, choisir l’heuristique : Manhattan, Euclidienne ou Octile
4. Cliquer sur « Lancer le calcul »
5. Le graphe et le chemin optimal s’affichent dans une nouvelle fenêtre
6. Le programme génère automatiquement un fichier texte contenant la liste des sommets du chemin

4) Fichiers de sortie
Le programme crée automatiquement un dossier :
sauvegardes/

Chaque exécution produit un fichier .txt contenant :
– l’algorithme utilisé
– l’heuristique (si A*)
– la liste ordonnée des sommets du chemin optimal

Ce mécanisme fonctionne sur tous les systèmes d’exploitation (Windows, Ubuntu, macOS).

5) Structure du projet

CodePartB/
├── graph/          → structures du graphe (Graph, Vertex, Edge)
├── algorithms/     → implémentations de Dijkstra et A*
├── heuristique/    → heuristiques pour A* (Manhattan, Euclidienne, Octile)
├── ui/             → affichage graphique (Board)
├── cartes/         → fichiers .txt utilisés pour les tests (graph.txt, heart.txt, labyrinth.txt, etc.)
└── sauvegardes/    → fichiers de sortie générés automatiquement

6) Compatibilité
Le programme a été testé sur :
– Windows 10 / 11
– Ubuntu 22.04

L’affichage peut varier légèrement selon l’OS, mais l’interface reste entièrement fonctionnelle.

