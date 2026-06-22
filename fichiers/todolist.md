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


je veux corriger ces pages dans @templates  , je veux que on include le header et footer dans chaque page au lieu d'avoir un model (layout @layouts ) fixe avec le contenue qui change selon l'url taper, le header est dans @entity.html  , il faut include le header dasn toutes les pages dans @templates   et aussi apporter les modifications pour qu'on puisse naviger fluidement dans les pages au depart il ne faut pas aussi oublier de modifier le script js dans @js 


creer des controller pour chaque role afin  de bien naviger dans les pages @controller 

- [] notes.html @notes.html 
    - [ok] fonction (AffectationEnseignementService @AffectationEnseignementService.java ): findByProfesseurId(): recuperer toutes les affectations
    du professeur connecter (obtenir *classe* et *matiere*)

    - [ok] fonction (InscriptionService @InscriptionService.java ): findByClasseId(): recuperer tous les etudiants d'une classe, a partir
    du classe_id dans la table *inscription*

    - [ok] fonction (NoteService @NoteService.java ): findByEtudiantId(): recuperer tous les notes d'un etudiant

    - [ok] fonction (NoteService @NoteService.java ): findByEtudiantIdByMatiereId(): recuperer tous les notes d'un etudiant dans une matiere

    - [ok] fonction (NoteService @NoteService.java ): findByEtudiantIdByMatiereIdByPeriodeId(): recuperer tous les notes d'un etudiant dans une matiere et une periode

    - [ok] fonction (NoteService @NoteService.java ): findByEtudiantIdByPeriodeId(): recuperer tous les notes d'un etudiant dans une periode

    - [] afficher dans une *section* le liste des classes auxquels le professeur connecter est assigner

    - [] cliquer sur une classe -> affichage de ses etudiants
    
    - [] afficher dans une *section* sous forme de tableau: la liste des etudiants + notes

    - [] bouton *Saisir note* ->redirect()->to('saisir_notes.html' @saisir_notes.html )

- saisir_notes.html
    - [] champ pour saisir: type_evaluation, periode, sur, commentaire
    - [] afficher sous forme de tableau: liste des etudiant + une colonne pour saisir la note
    - [] bouton 'Enregistrer les notes' -> appel fonction: save()
    - [] fonction (NoteService): save(): permet d'enregistrer tous les notes saisi par le professeur connecte


dans cette page @notes.html , le tableau qui affiche la liste des classes auxquelle le proffesseur , il faut l'afficher sous forme de tableau

puis, l'affichage de la liste des elves dans une classe, on affiche sous forme de tableau, avec les colonnes : eleve, puis les type_evaluation des notes qui sont deja enregistrer
on n'affiche pas la moyenne ni le rang

- [] bulletin.html @bulletin.html 
    - [ok] fonction (TitulaireClasseService @TitulaireClasseService.java ): findByProfesseurId(): resuperer la classe auquelle le professeur connecte est 
    titulaire
    - [] afficher sous forme de tableau la liste des etudiants de la classe 
    - [] cliquer sur une ligne d'eleve ->redirect()->to(bulletin_details.html)
    - [] bouton *Export PDF*: exporter en PDF la liste des etudiants -> appel fonction exporPDF()

- [] bulletin_details.html  @bulletin_details.html 
    - [] champ pour saisir la periode 
    - [ok] fonction (NoteService @NoteService.java ): findByEtudiantIdByPeriodeId(): recuperer tous les notes d'un etudiant dans une periode
    - [] fonction (NoteService): getBulletinEtudiant(): recuperer les notes d'un etudiant dans toutes les matieres selon
    la periode choisi, calcul sa moyenne, selon les coefficients de chaque matiere
    - [] afficher le bulletin d'un etudiant  dans la periode choisi
    - [] bouton *Export PDF*: exporter en PDF le bulletin d'un etudiant -> appel fonction exporPDF()


lorsquej'arrive sur cette url: http://localhost:1234/professeur/bulletins

ca affiche : Bulletins — Seconde A
Classe dont vous êtes titulaire

mais aussi des erreurs
voici l'erreur:
200
URL : /professeur/bulletins

18/06/2026 23:01:14


<tbody>
                    <tr th:if="${inscriptions == null or inscriptions.isEmpty()}">
                        <td colspan="4" style="text-align:center;color:var(--txt3);padding:20px;">
                            Aucun élève inscrit dans cette classe.
                        </td>
                    </tr>
                    <tr th:each="inscription : ${inscriptions}" 
                        th:onclick="'window.location.href=\'/professeur/bulletin/' + ${inscription.etudiantId} + '\''"
                        style="cursor:pointer;">
                        <td th:text="${etudiantProfiles.get(inscription.etudiantId) != null ? etudiantProfiles.get(inscription.etudiantId).matricule : ''}">Matricule</td>
                        <td th:text="${etudiantProfiles.get(inscription.etudiantId) != null ? etudiantProfiles.get(inscription.etudiantId).nom : 'Étudiant'}">Nom</td>
                        <td th:text="${etudiantProfiles.get(inscription.etudiantId) != null ? etudiantProfiles.get(inscription.etudiantId).prenom : ''}">Prénom</td>
                        <td>
                            <a th:href="@{/professeur/bulletin/{etudiantId}(etudiantId=${inscription.etudiantId})}" 
                               class="btn btn-primary btn-sm">
                                <i class="fas fa-file-alt"></i> Voir bulletin
                            </a>
                        </td>
                    </tr>
                </tbody>


                <div class="page-header-actions" th:if="${classe != null}">
                    <button class="btn btn-primary" th:onclick="'showToast(\'📋 Bulletins publiés pour ' + ${classe.nom} + ' !\')'">
                        <i class="fas fa-share"></i> Publier les bulletins
                    </button>
                </div>






poursuivons avec @profil.html , ajuster cette page pour qu'elle puisserecevoir le header, prendre exemple de design avec @profil.html 