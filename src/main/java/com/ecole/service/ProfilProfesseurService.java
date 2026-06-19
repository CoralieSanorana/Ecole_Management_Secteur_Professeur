package com.ecole.service;

import com.ecole.model.ProfilProfesseur;
import com.ecole.repository.ProfilProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilProfesseurService {

    @Autowired
    private ProfilProfesseurRepository profilProfesseurRepository;

    public Optional<ProfilProfesseur> findById(Long id) {
        return profilProfesseurRepository.findById(id);
    }
    // Ajoutez d'autres méthodes de service si nécessaire
}