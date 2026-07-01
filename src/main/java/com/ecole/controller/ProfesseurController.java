package com.ecole.controller;

import com.ecole.entity.*;
import com.ecole.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Controller
public class ProfesseurController {

    @Autowired
    private AffectationEnseignementService affectationEnseignementService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private ProfilEtudiantService profilEtudiantService;

    @Autowired
    private SupportCoursService supportCoursService;

    @Autowired
    private ProfilProfesseurService profilProfesseurService;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private TitulaireClasseService titulaireClasseService;

    @Autowired
    private TypeFichierService typeFichierService;

    @Autowired
    private AnneeScolaireService anneeScolaireService;

    @Autowired
    private EmploiDuTempsService emploiDuTempsService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private AbsenceService absenceService;

    /*
    @GetMapping("/professeur/emploi")
    public String emploi(Model model) {
        model.addAttribute("pageTitle", "Emploi du Temps");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        
        // TEMPORARY: Get all emploi du temps to test data display
        List<EmploiDuTemps> emploiDuTemps = emploiDuTempsService.findAll();
        
        System.out.println("DEBUG: Professeur ID: " + professeurId);
        System.out.println("DEBUG: All Emploi du temps retrieved: " + emploiDuTemps.size());
        
        // Enrich schedule data with subject, class, and room information
        List<Map<String, Object>> enrichedSchedule = new ArrayList<>();
        for (EmploiDuTemps edt : emploiDuTemps) {
            Map<String, Object> scheduleItem = new HashMap<>();
            scheduleItem.put("id", edt.getId());
            scheduleItem.put("jourSemaine", edt.getJourSemaine());
            scheduleItem.put("heureDebut", edt.getHeureDebut());
            scheduleItem.put("heureFin", edt.getHeureFin());
            
            System.out.println("DEBUG: EDT - ID: " + edt.getId() + ", Jour: " + edt.getJourSemaine() + ", Heure: " + edt.getHeureDebut() + "-" + edt.getHeureFin());
            
            // Get affectation details
            AffectationEnseignement affectation = affectationEnseignementService.findById(edt.getAffectationId()).orElse(null);
            if (affectation != null) {
                System.out.println("DEBUG: Affectation found - Prof ID: " + affectation.getProfesseurId());
                Matiere matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
                Classe classe = classeService.findById(affectation.getClasseId()).orElse(null);
                
                scheduleItem.put("matiere", matiere != null ? matiere.getNom() : "Matière inconnue");
                scheduleItem.put("classe", classe != null ? classe.getNom() : "Classe inconnue");
                scheduleItem.put("affectationId", affectation.getId());
                scheduleItem.put("classeId", classe != null ? classe.getId() : null);
            } else {
                System.out.println("DEBUG: Affectation not found for ID: " + edt.getAffectationId());
            }
            
            // Get room details
            Salle salle = salleService.findById(edt.getSalleId()).orElse(null);
            scheduleItem.put("salle", salle != null ? salle.getNom() : "Salle inconnue");
            
            enrichedSchedule.add(scheduleItem);
        }
        
        System.out.println("DEBUG: Enriched schedule size: " + enrichedSchedule.size());
        model.addAttribute("emploiDuTemps", enrichedSchedule);
        return "Professeur/calendar";
    }
    */
    @GetMapping("/professeur/emploi")
    public String emploi(Model model) {
        model.addAttribute("pageTitle", "Emploi du Temps");
        model.addAttribute("currentRole", "professeur");
        
        // Simuler le professeur connecté (ID: 1) pour le test
        Long professeurId = 1L;
        
        // Récupérer la liste des emplois du temps
        List<EmploiDuTemps> edtList = emploiDuTempsService.getCalendarProf(professeurId);
        
        // Tableau pour faire la correspondance entre le numéro (Integer) et le texte attendu par le JS
        String[] joursTexte = {"", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
        
        List<Map<String, Object>> formattedSchedule = new ArrayList<>();
        
        for (EmploiDuTemps edt : edtList) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", edt.getId());
            
            // Sécurité pour le jour qui est un Integer en BDD (ex: 1 -> "lundi")
            String jourStr = "lundi";
            if (edt.getJourSemaine() != null && edt.getJourSemaine() >= 1 && edt.getJourSemaine() <= 7) {
                jourStr = joursTexte[edt.getJourSemaine()];
            }
            item.put("jourSemaine", jourStr);
            
            // Formatage des heures (ex: "08:00")
            item.put("heureDebut", edt.getHeureDebut() != null ? edt.getHeureDebut().toString().substring(0, 5) : "08:00");
            item.put("heureFin", edt.getHeureFin() != null ? edt.getHeureFin().toString().substring(0, 5) : "10:00");
            
            // Récupération des IDs d'après tes colonnes de base de données
            Long affectationId = edt.getAffectationId();
            Long classeId = affectationEnseignementService.findById(affectationId).map(AffectationEnseignement::getClasseId).orElse(null);
            Long matiereId = affectationEnseignementService.findById(affectationId).map(AffectationEnseignement::getMatiereId).orElse(null);
            
            item.put("classeId", affectationId);
            
            // Récupérer dynamiquement les noms depuis les services associés
            String classeNom = (classeId != null) ? classeService.findById(classeId).map(Classe::getNom).orElse("Classe") : "Classe";
            String matiereNom = (matiereId != null) ? matiereService.findById(matiereId).map(Matiere::getNom).orElse("Matière") : "Matière";
            
            // Pour la salle, on affiche son ID ou son nom si tu as un salleService, sinon "Salle " + id
            String salleNom = (edt.getSalleId() != null) ? "Salle " + edt.getSalleId() : "N/A";
            
            item.put("classe", classeNom);
            item.put("matiere", matiereNom);
            item.put("salle", salleNom);
            
            formattedSchedule.add(item);
        }
        
        model.addAttribute("scheduleData", formattedSchedule);
        return "Professeur/calendar";
    }

    @GetMapping("/professeur/absences")
    public String absences(@RequestParam(required = false) Long emploiDuTempsId,
                          @RequestParam(required = false) String matiere,
                          @RequestParam(required = false) String classe,
                          @RequestParam(required = false) String salle,
                          @RequestParam(required = false) String heureDebut,
                          @RequestParam(required = false) String heureFin,
                          @RequestParam(required = false) Long classeId,
                          Model model) {
        model.addAttribute("pageTitle", "Gestion des Absences");
        model.addAttribute("currentRole", "professeur");
        
        model.addAttribute("emploiDuTempsId", emploiDuTempsId);
        model.addAttribute("matiere", matiere);
        model.addAttribute("classe", classe);
        model.addAttribute("salle", salle);
        model.addAttribute("heureDebut", heureDebut);
        model.addAttribute("heureFin", heureFin);
        model.addAttribute("classeId", classeId);
        
        // Get students for the class
        List<Inscription> inscriptions = null;
        Map<Long, ProfilEtudiant> etudiantProfiles = new HashMap<>();
        
        if (classeId != null) {
            inscriptions = inscriptionService.findByClasseId(classeId);
            for (Inscription inscription : inscriptions) {
                ProfilEtudiant etudiant = profilEtudiantService.findById(inscription.getEtudiantId()).orElse(null);
                if (etudiant != null) {
                    etudiantProfiles.put(inscription.getEtudiantId(), etudiant);
                }
            }
        }
        
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantProfiles", etudiantProfiles);
        
        return "Professeur/absences";
    }

    @PostMapping("/professeur/absences/save")
    public String saveAbsences(@RequestParam Long emploiDuTempsId,
                               @RequestParam String matiere,
                               @RequestParam String classe,
                               @RequestParam String salle,
                               @RequestParam String heureDebut,
                               @RequestParam String heureFin,
                               @RequestParam Long classeId,
                               @RequestParam(required = false) List<Long> absents,
                               RedirectAttributes redirectAttributes) {
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        
        // Get or create seance for this emploiDuTemps
        EmploiDuTemps emploiDuTemps = emploiDuTempsService.findById(emploiDuTempsId).orElse(null);
        if (emploiDuTemps == null) {
            redirectAttributes.addFlashAttribute("error", "Emploi du temps non trouvé");
            return "redirect:/professeur/absences";
        }
        
        // For now, we'll use the emploiDuTempsId as seanceId
        // In a real implementation, you would create a Seance entity
        Long seanceId = emploiDuTempsId;
        
        // Get all students in the class
        List<Inscription> inscriptions = inscriptionService.findByClasseId(classeId);
        
        // Process absences
        for (Inscription inscription : inscriptions) {
            Long etudiantId = inscription.getEtudiantId();
            boolean isAbsent = absents != null && absents.contains(etudiantId);
            
            if (isAbsent) {
                // Create absence record
                Absence absence = new Absence();
                absence.setSeanceId(seanceId);
                absence.setEtudiantId(etudiantId);
                absence.setType("non_justifiee");
                absence.setSaisiPar(professeurId);
                
                // Save absence
                absenceService.save(absence);
            }
        }
        
        redirectAttributes.addAttribute("success", "true");
        return "redirect:/professeur/absences";
    }

    @GetMapping("/professeur/notes")
    public String notes(Model model) {
        model.addAttribute("pageTitle", "Notes des Élèves");
        model.addAttribute("currentRole", "professeur");
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        
        // Fetch related entities for display
        Map<Long, String> classeNames = new HashMap<>();
        Map<Long, String> matiereNames = new HashMap<>();
        for (AffectationEnseignement affectation : affectations) {
            Classe classe = classeService.findById(affectation.getClasseId()).orElse(null);
            Matiere matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
            if (classe != null) {
                classeNames.put(affectation.getClasseId(), classe.getNom());
            }
            if (matiere != null) {
                matiereNames.put(affectation.getMatiereId(), matiere.getNom());
            }
        }
        
        model.addAttribute("affectations", affectations);
        model.addAttribute("classeNames", classeNames);
        model.addAttribute("matiereNames", matiereNames);
        model.addAttribute("periodes", periodeService.findAll());
        return "Professeur/notes";
    }

    @GetMapping("/professeur/notes/classe/{classeId}")
    public String notesClasse(@PathVariable Long classeId, Model model) {
        
        model.addAttribute("pageTitle", "Notes des Élèves");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        
        // --- ERADICATION DE LA PAGINATION BACKEND ---
        // On récupère TOUS les élèves de la classe d'un seul coup
        List<Inscription> inscriptions = inscriptionService.findByClasseId(classeId);
        // ---------------------------------------------
        
        // Fetch student profiles and notes
        Map<Long, ProfilEtudiant> etudiantProfiles = new HashMap<>();
        Map<Long, List<Note>> etudiantNotes = new HashMap<>();
        for (Inscription inscription : inscriptions) {
            ProfilEtudiant etudiant = profilEtudiantService.findById(inscription.getEtudiantId()).orElse(null);
            if (etudiant != null) {
                etudiantProfiles.put(inscription.getEtudiantId(), etudiant);
            }
            List<Note> notes = noteService.findByEtudiantId(inscription.getEtudiantId());
            etudiantNotes.put(inscription.getEtudiantId(), notes);
        }
        
        // Get unique evaluation types across all students
        java.util.Set<String> evaluationTypes = new java.util.TreeSet<>();
        for (List<Note> notes : etudiantNotes.values()) {
            for (Note note : notes) {
                if (note.getTypeEvaluation() != null) {
                    evaluationTypes.add(note.getTypeEvaluation());
                }
            }
        }
        
        // Organize notes by student and evaluation type
        Map<Long, Map<String, Note>> etudiantNotesByType = new HashMap<>();
        for (Map.Entry<Long, List<Note>> entry : etudiantNotes.entrySet()) {
            Map<String, Note> notesByType = new HashMap<>();
            for (Note note : entry.getValue()) {
                if (note.getTypeEvaluation() != null) {
                    notesByType.put(note.getTypeEvaluation(), note);
                }
            }
            etudiantNotesByType.put(entry.getKey(), notesByType);
        }
        
        // Fetch class and subject names
        Classe classe = classeService.findById(classeId).orElse(null);
        Map<Long, String> matiereNames = new HashMap<>();
        for (AffectationEnseignement affectation : affectations) {
            if (affectation.getClasseId().equals(classeId)) {
                Matiere matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
                if (matiere != null) {
                    matiereNames.put(affectation.getMatiereId(), matiere.getNom());
                }
            }
        }
        
        // --- ENVOI DES DONNÉES ÉPURÉES À THYMELEAF ---
        model.addAttribute("affectations", affectations);
        model.addAttribute("inscriptions", inscriptions); // Contient la liste complète pour le JS
        model.addAttribute("etudiantProfiles", etudiantProfiles);
        model.addAttribute("etudiantNotes", etudiantNotes);
        model.addAttribute("etudiantNotesByType", etudiantNotesByType);
        model.addAttribute("evaluationTypes", evaluationTypes);
        model.addAttribute("classe", classe);
        model.addAttribute("classeId", classeId);
        model.addAttribute("matiereNames", matiereNames);
        model.addAttribute("periodes", periodeService.findAll());
        
        return "Professeur/notes";
    }

    @GetMapping("/professeur/saisir_notes/{classeId}/{affectationId}")
    public String saisirNotes(@PathVariable Long classeId, @PathVariable Long affectationId, Model model) {
        model.addAttribute("pageTitle", "Saisir des Notes");
        model.addAttribute("currentRole", "professeur");
        
        List<Inscription> inscriptions = inscriptionService.findByClasseId(classeId);
        
        // Fetch student profiles
        Map<Long, ProfilEtudiant> etudiantProfiles = new HashMap<>();
        for (Inscription inscription : inscriptions) {
            ProfilEtudiant etudiant = profilEtudiantService.findById(inscription.getEtudiantId()).orElse(null);
            if (etudiant != null) {
                etudiantProfiles.put(inscription.getEtudiantId(), etudiant);
            }
        }
        
        // Fetch class and subject info
        Classe classe = classeService.findById(classeId).orElse(null);
        AffectationEnseignement affectation = affectationEnseignementService.findById(affectationId).orElse(null);
        Matiere matiere = null;
        if (affectation != null) {
            matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
        }
        
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantProfiles", etudiantProfiles);
        model.addAttribute("classe", classe);
        model.addAttribute("classeId", classeId);
        model.addAttribute("affectationId", affectationId);
        model.addAttribute("affectation", affectation);
        model.addAttribute("matiere", matiere);
        model.addAttribute("periodes", periodeService.findAll());
        return "Professeur/saisir_notes";
    }

    @PostMapping("/professeur/saisir_notes")
    public String enregistrerNotes(
            @RequestParam Long affectationId,
            @RequestParam Long periodeId,
            @RequestParam String typeEvaluation,
            @RequestParam BigDecimal sur,
            @RequestParam String commentaire,
            @RequestParam(required = false) List<Long> etudiantIds,
            @RequestParam(required = false) List<BigDecimal> valeurs,
            Model model) {
        
        if (etudiantIds != null && valeurs != null && etudiantIds.size() == valeurs.size()) {
            for (int i = 0; i < etudiantIds.size(); i++) {
                if (valeurs.get(i) != null) {
                    Note note = new Note();
                    note.setEtudiantId(etudiantIds.get(i));
                    note.setAffectationId(affectationId);
                    note.setPeriodeId(periodeId);
                    note.setTypeEvaluation(typeEvaluation);
                    note.setValeur(valeurs.get(i));
                    note.setSur(sur);
                    note.setCommentaire(commentaire);
                    // TODO: Set saisi_par from authentication
                    note.setSaisiPar(1L);
                    noteService.save(note);
                }
            }
        }
        
        return "redirect:/professeur/notes";
    }

    // Page profil professeur
    @GetMapping("/professeur/profil")
    public String profil(Model model) {
        Long professeurId = 1L; // TODO: Remplacer par l'ID du professeur connecté (via Spring Security)

        profilProfesseurService.findById(professeurId).ifPresent(professeur -> {
            model.addAttribute("professeur", professeur);
        });
        // Si le professeur n'est pas trouvé, l'attribut "professeur" ne sera pas dans le modèle,
        // et la vue devra gérer ce cas (ex: afficher un message d'erreur).
        return "Professeur/profil";
    }

    // Page devoirs
    @GetMapping("/professeur/devoirs")
    public String devoirs(Model model) {
        model.addAttribute("pageTitle", "Supports de Cours & Devoirs");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester

        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        model.addAttribute("affectations", affectations);
        
        // Fetch related entities for display
        Map<Long, String> classeNames = new HashMap<>();
        Map<Long, String> matiereNames = new HashMap<>();
        for (AffectationEnseignement affectation : affectations) {
            Classe classe = classeService.findById(affectation.getClasseId()).orElse(null);
            Matiere matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
            if (classe != null) {
                classeNames.put(affectation.getClasseId(), classe.getNom());
            }
            if (matiere != null) {
                matiereNames.put(affectation.getMatiereId(), matiere.getNom());
            }
        }
        
        model.addAttribute("classeNames", classeNames);
        model.addAttribute("matiereNames", matiereNames);

        return "Professeur/devoirs";
    }

    // Page devoirs détails
    @GetMapping("/professeur/devoirs/details")
    public String devoirsDetails(@RequestParam Long affectationId, Model model) {
        model.addAttribute("pageTitle", "Supports de Cours & Devoirs");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester

        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        model.addAttribute("affectations", affectations);
        
        // Fetch related entities for display
        Map<Long, String> classeNames = new HashMap<>();
        Map<Long, String> matiereNames = new HashMap<>();
        for (AffectationEnseignement affectation : affectations) {
            Classe classe = classeService.findById(affectation.getClasseId()).orElse(null);
            Matiere matiere = matiereService.findById(affectation.getMatiereId()).orElse(null);
            if (classe != null) {
                classeNames.put(affectation.getClasseId(), classe.getNom());
            }
            if (matiere != null) {
                matiereNames.put(affectation.getMatiereId(), matiere.getNom());
            }
        }
        
        model.addAttribute("classeNames", classeNames);
        model.addAttribute("matiereNames", matiereNames);
        
        // Récupérer les types de fichiers pour le select du formulaire
        model.addAttribute("typesFichiers", typeFichierService.findAll());

        // Récupérer l'affectation sélectionnée et ses supports
        affectationEnseignementService.findById(affectationId).ifPresent(aff -> {
            model.addAttribute("selectedClasse", aff); 
            model.addAttribute("supports", supportCoursService.findByAffectationId(affectationId));
        });

        return "Professeur/devoirs_details";
    }

    // POST - Publier un nouveau support (Cours ou Devoir)
    @PostMapping("/professeur/devoirs/save")
    public String saveSupport(@ModelAttribute SupportCours support,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            supportCoursService.save(support, file);
            redirectAttributes.addFlashAttribute("success", "Le support a été publié avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'envoi du fichier: " + e.getMessage());
        }

        return "redirect:/professeur/devoirs/details?affectationId=" + support.getAffectationId();
    }


    @GetMapping("/professeur/bulletins")
    public String bulletins(Model model) {
        model.addAttribute("pageTitle", "Bulletins");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        
        // Get current active school year
        AnneeScolaire anneeScolaire = anneeScolaireService.findByEstActive(true).orElse(null);
        Integer anneeScolaireId = (anneeScolaire != null) ? anneeScolaire.getId() : null;
        
        // Get the professor's titular class for the current year
        TitulaireClasse titulaireClasse = null;
        Classe classe = null;
        List<Inscription> inscriptions = null;
        Map<Long, ProfilEtudiant> etudiantProfiles = new HashMap<>();
        
        if (anneeScolaireId != null) {
            titulaireClasse = titulaireClasseService.findByProfesseurIdAndAnneeScolaireId(professeurId, anneeScolaireId).orElse(null);
            if (titulaireClasse != null) {
                classe = classeService.findById(titulaireClasse.getClasseId()).orElse(null);
                if (classe != null) {
                    inscriptions = inscriptionService.findByClasseId(classe.getId());
                    for (Inscription inscription : inscriptions) {
                        ProfilEtudiant etudiant = profilEtudiantService.findById(inscription.getEtudiantId()).orElse(null);
                        if (etudiant != null) {
                            etudiantProfiles.put(inscription.getEtudiantId(), etudiant);
                        }
                    }
                }
            }
        }
        
        model.addAttribute("titulaireClasse", titulaireClasse);
        model.addAttribute("classe", classe);
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantProfiles", etudiantProfiles);
        model.addAttribute("anneeScolaire", anneeScolaire);
        return "Professeur/bulletin";
    }

    @GetMapping("/professeur/bulletin/{etudiantId}")
    public String bulletinDetails(@PathVariable Long etudiantId, @RequestParam(required = false) Long periodeId, Model model) {
        model.addAttribute("pageTitle", "Bulletin de l'Élève");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        
        // Get current active school year
        AnneeScolaire anneeScolaire = anneeScolaireService.findByEstActive(true).orElse(null);
        Integer anneeScolaireId = (anneeScolaire != null) ? anneeScolaire.getId() : null;
        
        // Get the professor's titular class
        TitulaireClasse titulaireClasse = null;
        Classe classe = null;
        if (anneeScolaireId != null) {
            titulaireClasse = titulaireClasseService.findByProfesseurIdAndAnneeScolaireId(professeurId, anneeScolaireId).orElse(null);
            if (titulaireClasse != null) {
                classe = classeService.findById(titulaireClasse.getClasseId()).orElse(null);
            }
        }
        
        // Get student profile
        ProfilEtudiant etudiant = profilEtudiantService.findById(etudiantId).orElse(null);
        
        // Get all periods
        List<Periode> periodes = periodeService.findAll();
        
        // Get bulletin data if period is selected
        Map<String, Object> bulletin = null;
        if (periodeId != null && classe != null) {
            bulletin = noteService.getBulletinEtudiant(etudiantId, periodeId, classe.getId());
        }
        
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("etudiantId", etudiantId);
        model.addAttribute("classe", classe);
        model.addAttribute("titulaireClasse", titulaireClasse);
        model.addAttribute("anneeScolaire", anneeScolaire);
        model.addAttribute("periodes", periodes);
        model.addAttribute("bulletin", bulletin);
        model.addAttribute("selectedPeriodeId", periodeId);
        return "Professeur/bulletin_details";
    }

}
