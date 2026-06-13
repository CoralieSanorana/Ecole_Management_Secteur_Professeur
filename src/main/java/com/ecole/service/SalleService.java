package com.example.back_ecole.service;

import com.example.back_ecole.model.Salle;
import com.example.back_ecole.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    public Optional<Salle> findById(Long id) {
        return salleRepository.findById(id);
    }

    public Salle save(Salle salle) {
        return salleRepository.save(salle);
    }

    public void deleteById(Long id) {
        salleRepository.deleteById(id);
    }
}
