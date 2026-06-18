package com.ecole.repository;

import com.ecole.entity.ProfilProfesseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilProfesseurRepository extends JpaRepository<ProfilProfesseur, Long> {
}
