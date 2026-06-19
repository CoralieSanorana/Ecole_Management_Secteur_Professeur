# Lancer le Projet École Management

## Prérequis

- **Java** (JDK 21)
- **Maven** (pour le build Spring Boot)
- **PostgreSQL** (port 5432)
- **Python** (pour le serveur HTTP de développement du frontend)

## Configuration de la Base de Données

1. **Créer la base de données PostgreSQL :**
   ```bash
   # Ouvrir PostgreSQL et créer la base
   createdb ecole
   ```

2. **Exécuter le script SQL :**
   ```bash
   psql -d ecole -f ScriptSQL/schema_ecole_v2.sql
   ```

3. **Vérifier la connexion :**
   ```bash
   psql -d ecole -U postgres
   ```

## Lancer le Backend (Spring Boot)

1. **Compiler le projet avec Maven :**
   ```bash
   ./mvnw clean install
   ```

2. **Lancer l'application Spring Boot :**
   ```bash
   ./mvnw spring-boot:run
   ```

   L'application sera accessible sur `http://localhost:8080`

## Lancer le Frontend

1. **Lancer le serveur HTTP de développement (pour les prototypes statiques) :**
   ```bash
   python -m http.server 8081
   ```

   Le frontend sera accessible sur `http://localhost:8081/pages/layouts/model.html`

## Accéder à l'Application

- **Frontend (Statique)** : http://localhost:8081/pages/layouts/model.html
- **Backend API** : http://localhost:8080 (Spring Boot)

## Structure des Contrôleurs

Les contrôleurs Spring MVC sont configurés pour la navigation :

- **Directeur** : `/directeur/*`
  - `/directeur/dashboard`
  - `/directeur/finances`
  - `/directeur/professeurs`
  - `/directeur/profil-professeur`
  - `/directeur/ecolages`

- **Secrétaire** : `/secretaire/*`
  - `/secretaire/paiements`
  - `/secretaire/bilan`
  - `/secretaire/eleves`
  - `/secretaire/profil-eleve`

- **Professeur** : `/professeur/*`
  - `/professeur/emploi`
  - `/professeur/notes`
  - `/professeur/devoirs`
  - `/professeur/bulletins`
  - `/professeur/profil`

- **Étudiant** : `/etudiant/*`
  - `/etudiant/emploi`
  - `/etudiant/notes`
  - `/etudiant/bulletin`
  - `/etudiant/devoirs`

## Dépannage

### Erreur de connexion PostgreSQL
- Vérifier que PostgreSQL est en cours d'exécution
- Vérifier les identifiants dans `src/main/resources/application.properties`
- Assurer que la base `ecole` existe

### Port déjà utilisé
- Si le port 8080 est occupé, utiliser un autre port :
  ```bash
  python -m http.server 8081
  
  ```

### Problèmes de dépendances Maven
- Nettoyer et réinstaller :
  ```bash
  mvn clean install -U
  ```

  Import du base
docker exec -i postgis psql -U postgres -d ecole < /home/itiela/Documents/Bao/Ecole_Management_Secteur_Professeur/ScriptSQL/schema_ecole_v2.sql
  Import des donnees test
docker exec -i postgis psql -U postgres -d ecole < "/home/itiela/Documents/Bao/Ecole_Management_Secteur_Professeur/ScriptSQL/Directeur/Donne_test/25-05-2026-Donne-Test-Fu.sql"