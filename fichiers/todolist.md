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