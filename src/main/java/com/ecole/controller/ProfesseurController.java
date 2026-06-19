package com.ecole.controller;

import com.ecole.model.AffectationEnseignement;
import com.ecole.model.Inscription;
import com.ecole.model.Note;
import com.ecole.model.ProfilEtudiant;
import com.ecole.model.SupportCours;
import com.ecole.model.TypeFichier;
import com.ecole.service.AffectationEnseignementService;
import com.ecole.service.InscriptionService;
import com.ecole.service.NoteService;
import com.ecole.service.ProfilProfesseurService;
import com.ecole.service.ProfilEtudiantService;
import com.ecole.service.SupportCoursService;
import com.ecole.service.TypeFichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfesseurController {

    @Autowired
    private AffectationEnseignementService affectationEnseignementService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private ProfilEtudiantService profilEtudiantService;

    @Autowired
    private SupportCoursService supportCoursService;

    @Autowired
    private TypeFichierService typeFichierService;

    @Autowired
    private ProfilProfesseurService profilProfesseurService; // Nouvelle injection

    // Page notes - affiche les classes du professeur
    @GetMapping("/professeur/notes")
    public String notes(Model model) {
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester

        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        model.addAttribute("affectations", affectations);
        
        return "Professeur/notes";
    }

    // Page notes avec classe sélectionnée - affiche les étudiants et leurs notes
    @GetMapping("/professeur/notes/{classeId}/{matiereId}")
    public String notesClasse(@PathVariable Long classeId, 
                              @PathVariable Long matiereId,
                              Model model) {
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester

        // Récupérer les affectations du professeur
        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        model.addAttribute("affectations", affectations);

        // Récupérer les inscriptions (étudiants) de la classe
        List<Inscription> inscriptions = inscriptionService.findByClasseId(classeId);
        
        // Récupérer les profils des étudiants
        Map<Long, ProfilEtudiant> etudiantsMap = new HashMap<>();
        for (Inscription inscription : inscriptions) {
            profilEtudiantService.findById(inscription.getEtudiantId())
                .ifPresent(profil -> etudiantsMap.put(inscription.getEtudiantId(), profil));
        }

        // Récupérer les notes des étudiants pour cette matière
        Map<Long, List<Note>> notesMap = new HashMap<>();
        for (Inscription inscription : inscriptions) {
            List<Note> notes = noteService.findByEtudiantIdByMatiereId(
                inscription.getEtudiantId(), matiereId);
            notesMap.put(inscription.getEtudiantId(), notes);
        }

        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantsMap", etudiantsMap);
        model.addAttribute("notesMap", notesMap);
        model.addAttribute("classeId", classeId);
        model.addAttribute("matiereId", matiereId);

        return "Professeur/notes";
    }

    // Page saisir_notes - pour saisir les notes
    @GetMapping("/professeur/saisir_notes/{classeId}/{matiereId}")
    public String saisirNotes(@PathVariable Long classeId,
                              @PathVariable Long matiereId,
                              Model model) {
        // Récupérer les inscriptions (étudiants) de la classe
        List<Inscription> inscriptions = inscriptionService.findByClasseId(classeId);
        
        // Récupérer les profils des étudiants
        Map<Long, ProfilEtudiant> etudiantsMap = new HashMap<>();
        for (Inscription inscription : inscriptions) {
            profilEtudiantService.findById(inscription.getEtudiantId())
                .ifPresent(profil -> etudiantsMap.put(inscription.getEtudiantId(), profil));
        }

        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("etudiantsMap", etudiantsMap);
        model.addAttribute("classeId", classeId);
        model.addAttribute("matiereId", matiereId);

        return "Professeur/saisir_notes";
    }

    // POST - Sauvegarder les notes
    @PostMapping("/professeur/saisir_notes/{classeId}/{matiereId}")
    public String saveNotes(@PathVariable Long classeId,
                           @PathVariable Long matiereId,
                           @RequestParam("typeEvaluation") String typeEvaluation,
                           @RequestParam("periode") Long periodeId,
                           @RequestParam("sur") Double sur,
                           @RequestParam("etudiantIds") List<Long> etudiantIds,
                           @RequestParam("valeurs") List<Double> valeurs,
                           @RequestParam(value = "commentaires", required = false) List<String> commentaires,
                           RedirectAttributes redirectAttributes) {
        
        Long professeurId = 1L; // TODO: SecurityContextHolder...

        for (int i = 0; i < etudiantIds.size(); i++) {
            Note note = new Note();
            note.setEtudiantId(etudiantIds.get(i));
            note.setValeur(BigDecimal.valueOf(valeurs.get(i)));
            note.setCommentaire(commentaires != null && i < commentaires.size() ? commentaires.get(i) : "");
            note.setTypeEvaluation(typeEvaluation);
            note.setPeriodeId(periodeId);
            note.setSur(BigDecimal.valueOf(sur));
            note.setSaisiPar(professeurId);
            // Note: Il faudrait aussi l'affectation_id ici selon votre schéma SQL
            noteService.save(note);
        }

        redirectAttributes.addFlashAttribute("success", "Les notes ont été enregistrées.");
        return "redirect:/professeur/notes/" + classeId + "/" + matiereId;
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
    public String devoirs(@RequestParam(required = false) Long affectationId, Model model) {
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester

        List<AffectationEnseignement> affectations = affectationEnseignementService.findByProfesseurId(professeurId);
        model.addAttribute("affectations", affectations);
        
        // Récupérer les types de fichiers pour le select du formulaire
        model.addAttribute("typesFichiers", typeFichierService.findAll());

        if (affectationId != null) {
            affectationEnseignementService.findById(affectationId).ifPresent(aff -> {
                model.addAttribute("selectedClasse", aff); 
                model.addAttribute("supports", supportCoursService.findByAffectationId(affectationId));
            });
        }

        return "Professeur/devoirs";
    }

    // POST - Publier un nouveau support (Cours ou Devoir)
    @PostMapping("/professeur/devoirs/save")
    public String saveSupport(@ModelAttribute SupportCours support, 
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {
                // Créer le dossier uploads s'il n'existe pas
                Path uploadPath = Paths.get("uploads");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Nettoyer le nom du fichier et le sauvegarder
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                support.setUrlFichier("/uploads/" + fileName);
            }
            
            supportCoursService.save(support, file);
            redirectAttributes.addFlashAttribute("success", "Le support a été publié avec succès.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'envoi du fichier.");
        }

        return "redirect:/professeur/devoirs?affectationId=" + support.getAffectation().getId();
    }

    // Page bulletin
    @GetMapping("/professeur/bulletin")
    public String bulletin(Model model) {
        return "Professeur/bulletin";
    }

    // Page calendar (emploi du temps)
    @GetMapping("/professeur/calendar")
    public String calendar(Model model) {
        return "Professeur/calendar";
    }
}
