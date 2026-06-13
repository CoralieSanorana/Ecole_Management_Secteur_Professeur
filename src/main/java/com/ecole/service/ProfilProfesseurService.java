package com.example.back_ecole.service;

import com.example.back_ecole.model.ProfilProfesseur;
import com.example.back_ecole.repository.ProfilProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilProfesseurService {

    @Autowired
    private ProfilProfesseurRepository profilProfesseurRepository;

    public List<ProfilProfesseur> findAll() {
        return profilProfesseurRepository.findAll();
    }

    public Optional<ProfilProfesseur> findById(Long id) {
        return profilProfesseurRepository.findById(id);
    }

    public ProfilProfesseur save(ProfilProfesseur profilProfesseur) {
        return profilProfesseurRepository.save(profilProfesseur);
    }

    public void deleteById(Long id) {
        profilProfesseurRepository.deleteById(id);
    }

    public ProfilProfesseur findByProfilId(Long profilId) {
        return profilProfesseurRepository.findByProfilId(profilId);
    }
}
