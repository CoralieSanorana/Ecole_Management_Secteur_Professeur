package com.ecole.repository;

import com.ecole.entity.AffectationEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationEnseignementRepository extends JpaRepository<AffectationEnseignement, Long> {
    List<AffectationEnseignement> findByProfesseurId(Long professeurId);
}
