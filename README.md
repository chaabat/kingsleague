# Gestion de la Kings League

## Description du Projet
Ce projet est une application de gestion pour organiser et suivre la Kings League, une compétition innovante de football. Elle permet de gérer les joueurs, les équipes, les tournois et les matchs.

## Fonctionnalités Principales

### Gestion des Joueurs
- Inscription, modification et suppression de joueurs
- Affichage des informations d'un joueur ou de tous les joueurs

### Gestion des Équipes
- Création et modification d'équipes
- Ajout et retrait de joueurs dans une équipe
- Affichage des informations d'une équipe ou de toutes les équipes

### Gestion des Tournois
- Création et modification de tournois
- Ajout et retrait d'équipes dans un tournoi
- Affichage des informations d'un tournoi ou de tous les tournois
- Calcul de la durée estimée d'un tournoi
- Modification du statut d'un tournoi (PLANIFIE, EN_COURS, TERMINE, ANNULE)

### Gestion des Matchs
- Création et modification de matchs
- Enregistrement des résultats
- Affichage des statistiques de match

## Comment Exécuter le Projet

### Utilisation du fichier JAR
1. Assurez-vous d'avoir Java 8 ou une version supérieure installée sur votre machine.
2. Téléchargez le fichier JAR depuis le dossier `@src` du dépôt.
3. Ouvrez un terminal et naviguez jusqu'au dossier contenant le fichier JAR.
4. Exécutez la commande suivante :
   ```
   java -jar AYOUB_CHAABAT_S3_B1_KingsLeague.jar
   ```

### Cloner et Exécuter le Projet
1. Clonez le dépôt :
   ```
   git clone https://github.com/JavaAura/AYOUB_CHAABAT_S3_B1_KingsLeague.git
   ```
2. Naviguez dans le dossier du projet :
   ```
   cd AYOUB_CHAABAT_S3_B1_KingsLeague
   ```
3. Compilez le projet avec Maven :
   ```
   mvn clean package
   ```
4. Exécutez le fichier JAR généré :
   ```
   java -jar target/AYOUB_CHAABAT_S3_B1_KingsLeague.jar
   ```

## Structure du Projet
Le projet suit une architecture en couches :
- Modèle : Entités JPA (Joueur, Equipe, Tournoi, Match)
- DAO : Couche d'accès aux données utilisant JPA/Hibernate
- Service : Logique métier
- Présentation : Interface console pour interagir avec l'application

Le projet implémente le principe Open/Closed, notamment avec des extensions de DAO pour ajouter des fonctionnalités sans modifier le code source existant.

## Technologies Utilisées
- Java 8
- Spring Core pour l'IoC et la DI (configuration XML)
- JPA et Hibernate pour la persistance des données
- H2 comme base de données embarquée
- Maven pour la gestion des dépendances
- JUnit et Mockito pour les tests unitaires
- SLF4J pour le logging

## Gestion de Projet
- Git pour le contrôle de version
- JIRA pour la gestion de projet Scrum : [Lien JIRA]

## Diagramme UML
Le diagramme UML du projet est disponible dans le fichier `UML.mdj` dans le dossier `@src` du projet.

## Auteur
Ayoub Chaabat
