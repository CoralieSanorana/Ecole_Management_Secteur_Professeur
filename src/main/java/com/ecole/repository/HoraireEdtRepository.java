package com.example.back_ecole.repository;

import com.example.back_ecole.model.HoraireEdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireEdtRepository extends JpaRepository<HoraireEdt, Long> {
}
