package com.example.back_ecole.service;

import com.example.back_ecole.model.AffectationEnseignement;
import com.example.back_ecole.repository.AffectationEnseignementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationEnseignementService {

    @Autowired
    private AffectationEnseignementRepository affectationEnseignementRepository;

    public List<AffectationEnseignement> findAll() {
        return affectationEnseignementRepository.findAll();
    }

    public Optional<AffectationEnseignement> findById(Long id) {
        return affectationEnseignementRepository.findById(id);
    }

    public AffectationEnseignement save(AffectationEnseignement affectationEnseignement) {
        return affectationEnseignementRepository.save(affectationEnseignement);
    }

    public void deleteById(Long id) {
        affectationEnseignementRepository.deleteById(id);
    }
    // getAllAssignmentProfesseur
    public List<AffectationEnseignement> findByProfesseurId(Long professeurId) {
        return affectationEnseignementRepository.findByProfesseurId(professeurId);
    }
}
