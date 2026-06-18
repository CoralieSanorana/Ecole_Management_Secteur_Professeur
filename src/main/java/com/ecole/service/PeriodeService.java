package com.ecole.service;

import com.ecole.entity.Periode;
import com.ecole.repository.PeriodeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodeService {

    private final PeriodeRepository periodeRepository;

    public PeriodeService(PeriodeRepository periodeRepository) {
        this.periodeRepository = periodeRepository;
    }

    public List<Periode> getAllPeriodes() {
        return periodeRepository.findAll();
    }

    public Optional<Periode> getPeriodeById(Integer id) {
        return periodeRepository.findById(id);
    }

    public List<Periode> getPeriodesByAnneeScolaire(Long anneeScolaireId) {
        return periodeRepository.findByAnneeScolaireId(anneeScolaireId);
    }

    public Periode savePeriode(Periode periode) {
        return periodeRepository.save(periode);
    }

    public void deletePeriode(Integer id) {
        periodeRepository.deleteById(id);
    }
}