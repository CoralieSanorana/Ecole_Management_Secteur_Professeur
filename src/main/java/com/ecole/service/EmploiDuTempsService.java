package com.example.back_ecole.service;

import com.example.back_ecole.model.EmploiDuTemps;
import com.example.back_ecole.repository.EmploiDuTempsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmploiDuTempsService {

    @Autowired
    private EmploiDuTempsRepository emploiDuTempsRepository;

    public List<EmploiDuTemps> findAll() {
        return emploiDuTempsRepository.findAll();
    }

    public Optional<EmploiDuTemps> findById(Long id) {
        return emploiDuTempsRepository.findById(id);
    }

    public EmploiDuTemps save(EmploiDuTemps emploiDuTemps) {
        return emploiDuTempsRepository.save(emploiDuTemps);
    }

    public void deleteById(Long id) {
        emploiDuTempsRepository.deleteById(id);
    }
}
