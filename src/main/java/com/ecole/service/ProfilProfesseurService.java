package com.ecole.service;

import com.ecole.entity.ProfilProfesseur;
import com.ecole.repository.ProfilProfesseurRepository;
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
}
