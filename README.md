# 🧭 Algorithmes de Plus Court Chemin — Dijkstra & A*  
Projet académique — Université Paris Cité

Ce dépôt regroupe deux projets réalisés dans le cadre du module **Algorithmique Avancée** à l’Université Paris Cité.  
Ils explorent l’application des algorithmes de plus court chemin (Dijkstra et A*) sur deux types de données :

1. **Des cartes 2D** (grilles pondérées)  
2. **Des images numériques** converties automatiquement en graphes

Ces travaux ont été réalisés individuellement dans le cadre des mini‑projets et TPs notés.

---

## 🎯 Objectifs pédagogiques

- Implémenter Dijkstra et A* de manière modulaire  
- Comparer leurs performances selon différentes heuristiques  
- Construire un graphe pondéré à partir d’une carte ou d’une image  
- Visualiser le chemin optimal dans une interface graphique  
- Comprendre l’impact de la structure du graphe sur les performances  
- Manipuler des structures de données avancées (files de priorité, maps, matrices)

---

# 🗺️ Partie A — Plus court chemin sur cartes (Dijkstra & A*)

Cette partie consiste à appliquer Dijkstra et A* sur des **cartes 2D** où chaque case est un sommet.

### 🔧 Construction du graphe
- Chaque case devient un **sommet**
- Les 8 voisins (haut, bas, gauche, droite, diagonales) sont connectés
- Le poids d’une arête est :  
  
w = \frac{t_A + t_B}{2}


- Les diagonales sont pondérées par √2

### ⭐ Algorithmes implémentés
- **Dijkstra** (version simple, complexité O(V²))  
- **A\*** avec trois heuristiques :
  - Manhattan  
  - Euclidienne  
  - Octile (adaptée aux grilles 8‑connexes)

### 📊 Résultats expérimentaux
Les tests montrent que :

- A* est généralement plus rapide que Dijkstra  
- Manhattan est très efficace dans les couloirs orthogonaux  
- Octile est la plus cohérente pour les grilles 8‑connexes  
- Dans les cartes piégeuses (obstacles concaves), A* perd son avantage  
- Le choix de l’heuristique dépend fortement de la géométrie du graphe

---

# 🖼️ Partie B — Plus court chemin sur images (Image → Graphe → Dijkstra)

Cette partie consiste à transformer une **image** en un **graphe pondéré**, puis à calculer le plus court chemin entre deux pixels choisis par l’utilisateur.

### 🧩 Étapes principales

#### 1. Chargement et traitement de l’image
- Redimensionnement automatique (max 400×400)
- Conversion RGB → intensité (0–255)
- Stockage dans une matrice `intensities[y][x]`

#### 2. Construction du graphe
- Chaque pixel devient un **sommet**
- 4‑connexité (haut, bas, gauche, droite)
- Poids d’une arête :  
  

\[
  w(u,v) = |I(u) - I(v)|
  \]



#### 3. Interface graphique (Java Swing)
- Sélection du point de départ (vert)
- Sélection du point d’arrivée (rouge)
- Exécution automatique de Dijkstra
- Affichage du chemin optimal (pixels jaunes)
- Panneau latéral affichant :
  - coordonnées
  - longueur du chemin
  - nombre de nœuds explorés

### 🎨 Visualisation
L’application permet d’explorer visuellement la structure interne d’une image à travers un graphe pondéré.

---

## 🛠️ Technologies utilisées

- **Java**
- **Swing** (interface graphique)
- **Java AWT / ImageIO**
- **Structures de données :** Map, PriorityQueue, matrices, listes d’adjacence

---

## 📚 Ce que ce projet m’a apporté

- Compréhension approfondie des algorithmes de graphes  
- Implémentation modulaire de Dijkstra et A*  
- Analyse comparative des heuristiques  
- Construction automatique de graphes à partir de données visuelles  
- Développement d’interfaces interactives  
- Visualisation et débogage graphique de chemins optimaux  

---

## 🚀 Avancement

Les deux projets sont **fonctionnels**.  
