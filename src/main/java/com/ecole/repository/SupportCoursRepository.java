package com.example.back_ecole.repository;

import com.example.back_ecole.model.SupportCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportCoursRepository extends JpaRepository<SupportCours, Long> {
    Optional<SupportCours> findByAffectation_id(Long affectation_id);
}
