voici la liste des trucs a faire:
- [] notes.html @notes.html 
    - [] fonction (AffectationEnseignementService @AffectationEnseignementService.java ): findByProfesseurId(): recuperer toutes les affectations
    du professeur connecter (obtenir *classe* et *matiere*)
    - [] fonction (ProfilEtudiantService @ProfilEtudiantService.java ): findByClasseId(): recuperer tous les etudiants d'une classe, a partir
    du classe_id dans la table *inscription*
    - [] fonction (NoteService @NoteService.java ): findByEtudiantId(): recuperer tous les notes d'un etudiant
    - [] fonction (NoteService @NoteService.java ): findByEtudiantIdByMatiereId(): recuperer tous les notes d'un etudiant dans une matiere
    - [] afficher dans une *section* le liste des classes auxquels le professeur connecter est assigne
    - [] cliquer sur une classe -> affichage de ses etudiants
    - [] afficher dans une *section* sous forme de tableau: la liste des etudiants + notes
    - [] bouton *Saisir note* ->redirect()->to('saisir_notes.html @saisir_notes.html ')

voici le controller a utiliser pour la partie professeur : @ProfesseurController.java  et mettre tous les routes (url) utiles


lorsque j'arrive sur cet url: http://localhost:8080/professeur/notes, ca affcihe une erreur 404
regarde bien dans @script.js et @ProfesseurController.java puis les autres pages dans @templates , corrige les erreurs possibles afin que les url taper puissent bien fonctionner

je veux des donnees de teste pour tester les fonctionnalites du sectur profosseur Taches.md , mettre dans 2026-06-13-n02-Co.sql les scripts pour inserer les donnees de teste

voici comment la base de donnees s'organise ScriptSQL , ca commence par schema_ecole_v2.sql puis Directeur (par date) et ensuite 2026-06-13-n01-Co.sql

voici les tables utiles pour le secteur professeur Taches.md 4-14


je veux corriger ces pages dans @templates  , je veux que on include le header et footer dans chaque page au lieu d'avoir un model (layout @layouts ) fixe avec le contenue qui change selon l'url taper, le header est dans @model.html  , il faut include le header dasn toutes les pages dans @templates   et aussi apporter les modifications pour qu'on puisse naviger fluidement dans les pages au depart il ne faut pas aussi oublier de modifier le script js dans @js 


creer des controller pour chaque role afin  de bien naviger dans les pages @controller 