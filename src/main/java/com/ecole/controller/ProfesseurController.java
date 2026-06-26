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

    @GetMapping("/professeur/emploi")
    public String emploi(Model model) {
        model.addAttribute("pageTitle", "Emploi du Temps");
        model.addAttribute("currentRole", "professeur");
        return "Professeur/calendar";
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
    public String notesClasse(
        @PathVariable Long classeId,
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model) {
        
        model.addAttribute("pageTitle", "Notes des Élèves");
        model.addAttribute("currentRole", "professeur");
        
        // TODO: Get connected professor ID from authentication
        Long professeurId = 1L; // Temporary hardcoded value
        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        
        // --- BLOC MODIFIÉ POUR LA PAGINATION ET LA RECHERCHE ---
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page - 1, size);
        org.springframework.data.domain.Page<Inscription> inscriptionPage = 
                inscriptionService.findByClasseIdAndStudentName(classeId, search, pageable);
        
        List<Inscription> inscriptions = inscriptionPage.getContent();
        // --------------------------------------------------------
        
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
        
        model.addAttribute("affectations", affectations);
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantProfiles", etudiantProfiles);
        model.addAttribute("etudiantNotes", etudiantNotes);
        model.addAttribute("etudiantNotesByType", etudiantNotesByType);
        model.addAttribute("evaluationTypes", evaluationTypes);
        model.addAttribute("classe", classe);
        model.addAttribute("classeId", classeId);
        model.addAttribute("matiereNames", matiereNames);
        model.addAttribute("periodes", periodeService.findAll());
        
        // --- BLOC ENVOI DES INFOS DE PAGINATION ET RECHERCHE À THYMELEAF ---
        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", inscriptionPage.getTotalPages());
        model.addAttribute("totalRows", inscriptionPage.getTotalElements());
        // -------------------------------------------------------------------
        
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
