package com.example.back_ecole.service;

import com.example.back_ecole.model.SupportCours;
import com.example.back_ecole.repository.SupportCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportCoursService {

    @Autowired
    private SupportCoursRepository supportCoursRepository;

    public List<SupportCours> findAll() {
        return supportCoursRepository.findAll();
    }

    public Optional<SupportCours> findById(Long id) {
        return supportCoursRepository.findById(id);
    }

    public SupportCours save(SupportCours supportCours) {
        return supportCoursRepository.save(supportCours);
    }

    public void deleteById(Long id) {
        supportCoursRepository.deleteById(id);
    }

    public List<SupportCours> findByAffectationId(Long affectationId) {
        return supportCoursRepository.findByAffectation_id(affectationId);
    }

}
