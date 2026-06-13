package com.example.back_ecole.repository;

import com.example.back_ecole.model.EvenementInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvenementInstanceRepository extends JpaRepository<EvenementInstance, Long> {
}
