package com.ecole.controller;

import com.example.back_ecole.model.AffectationEnseignement;
import com.example.back_ecole.model.Inscription;
import com.example.back_ecole.model.Note;
import com.example.back_ecole.model.ProfilEtudiant;
import com.example.back_ecole.service.AffectationEnseignementService;
import com.example.back_ecole.service.InscriptionService;
import com.example.back_ecole.service.NoteService;
import com.example.back_ecole.service.ProfilEtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                           @RequestParam String typeEvaluation,
                           @RequestParam String periode,
                           @RequestParam String sur,
                           @RequestParam(required = false) List<String> notes,
                           Model model) {
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester
        
        // TODO: Implémenter la logique de sauvegarde des notes
        // Pour l'instant, rediriger vers la page notes
        return "redirect:/professeur/notes/" + classeId + "/" + matiereId;
    }

    // Page profil professeur
    @GetMapping("/professeur/profil")
    public String profil(Model model) {
        // TODO: Récupérer l'ID du professeur connecté depuis la session
        Long professeurId = 1L; // Valeur temporaire pour tester
        // TODO: Récupérer et afficher les informations du professeur
        return "Professeur/profil";
    }

    // Page devoirs
    @GetMapping("/professeur/devoirs")
    public String devoirs(Model model) {
        return "Professeur/devoirs";
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
