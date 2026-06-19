# Secteur Professeur
Un sous groupe qui est responsable de la partie **Professeur**.

## Tables utiles:
- users, roles, users_roles
- niveaux, classes, salles
- matieres, coefficients
- profils_etudiants, profils_professeurs
- affectations_enseignement
- emploi_du_temps, horaire_edt, seances, absences
- notes, moyennes
- evenements, evenements_instances
- notification_types, notifications
- types_fichiers, supports_cours

## Back:
### Coralie:
- [ok] Creations de base:
    - [ok] Creer un *model* pour chaque *table utile*
    - [ok] Creer un *repository* pour chaque *model*
    - [ok] Creer un *service* pour  chaque *model*

## Front: *pages/Professeur/*
### Itiela:
- [x] profil.html
    - [x] fonction (ProfilProfesseurService): findById(): recuperer les informations du professeur connecte
    de la table *profils_professeurs*
    - [x] afficher les informations du professeur connecter

### Coralie:
- [] notes.html
    - [] fonction (AffectationEnseignementService): findByProfesseurId(): recuperer toutes les affectations
    du professeur connecter (obtenir *classe* et *matiere*)
    - [] fonction (ProfilEtudiantService): findByClasseId(): recuperer tous les etudiants d'une classe, a partir
    du classe_id dans la table *inscription*
    - [] fonction (NoteService): findByEtudiantId(): recuperer tous les notes d'un etudiant
    - [] fonction (NoteService): findByEtudiantIdByMatiereId(): recuperer tous les notes d'un etudiant dans une matiere
    - [] afficher dans une *section* le liste des classes auxquels le professeur connecter est assigne
    - [] cliquer sur une classe -> affichage de ses etudiants
    - [] afficher dans une *section* sous forme de tableau: la liste des etudiants + notes
    - [] bouton *Saisir note* ->redirect()->to('saisir_notes.html')

### Coralie:
- saisir_notes.html
    - [] champ pour saisir: type_evaluation, periode, sur, commentaire
    - [] afficher sous forme de tableau: liste des etudiant + une colonne pour saisir la note
    - [] bouton 'Enregistrer les notes' -> appel fonction: save()
    - [] fonction (NoteService): save(): permet d'enregistrer tous les notes saisi par le professeur connecte

### Itiela:
- [x] devoirs.html
    - [x] afficher dans une *section* la liste des classes auxquels le professeur connecter est assigne
    - [x] cliquer sur une classe -> affichage des cours deja publier par le professeur connecte
    - [x] fonction (AffectationEnseignementService): findByAffectationId(): 
        - recuperer tous les cours publier par le professeur connecter selon la classe choisi
    - [x] afficher dans une *section* la liste des cours deja publier par le professeur connecte pour 
    la classe choisi
    - [x] bouton *Nouveau Cours* -> appel fonction js formCours()
    - [x] fonction js: formCours(): assure l'affichage d'un formulaire pour inserer un nouveau cours ou devoir
        - [x] les champs requises: type_fichier, titre, upload file, type_contenu, date_limite, accepte_retard
    - [x] bouton *Publier Cours* -> appel fonction save()
    - [x] fonction (SupportCourtService): save(): permet d'enregistrer le cours publier dans la table *supports_cours*

### Mbola miandry:
- [] bulletin.html
    - [] fonction: getClasseTitulaireProfesseur(): resuperer la classe auquelle le professeur conncte est 
    titulaire
    - [] afficher sous forme de tableau la liste des etudiants de la classe 
    - [] cliquer sur une ligne d'eleve ->redirect()->to(bulletin_details.html)
    - [] bouton *Export PDF*: exporter en PDF la liste des etudiants -> appel fonction exporPDF()

- [] bulletin_details.html
    - [] champ pour saisir la periode 
    - [] fonction: getBulletinEtudiant(): recuperer les notes d'un etudiant dans toutes les matieres selon
    la periode choisi
    - [] afficher le bulletin d'un etudiant  dans la periode choisi
    - [] bouton *Export PDF*: exporter en PDF le bulletin d'un etudiant -> appel fonction exporPDF()

- [] calendar.html
    - [] fonction: getCalendarProf(): recuperer les emploies du temps du professeur connecte
    - [] afficher sous forme de calendrier hebdomadaire les emploies du temps du professeur connecte
    - [] cliquer sur une ligne de calendrier -> redirect()->to('absences.html')
    - [] bouton *Export PDF*: exporter en PDF l'empoi du temps du professeur -> appel fonction exporPDF()

- [] absences.html
    - [] afficher sous forme de tableau la liste des etudiants + colonne 'absent'(default: false)
    - [] checker la colonne 'absent'-> marquer etudiant absent
    - [] bouton *Enregistrer les absences* -> appel fonction saveAbsences()
    - [] fonction: saveAbsences(): permet d'enregistrer tous les absences saisi par le professeur connecte dans la table *absences*
    - [] afficher une message de confirmation: "Les absences ont ete enregistrer avec success" 


