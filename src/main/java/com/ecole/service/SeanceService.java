package com.example.back_ecole.service;

import com.example.back_ecole.model.Seance;
import com.example.back_ecole.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {

    @Autowired
    private SeanceRepository seanceRepository;

    public List<Seance> findAll() {
        return seanceRepository.findAll();
    }

    public Optional<Seance> findById(Long id) {
        return seanceRepository.findById(id);
    }

    public Seance save(Seance seance) {
        return seanceRepository.save(seance);
    }

    public void deleteById(Long id) {
        seanceRepository.deleteById(id);
    }
}
