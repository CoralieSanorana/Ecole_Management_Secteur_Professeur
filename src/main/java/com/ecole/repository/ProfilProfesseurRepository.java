package com.ecole.repository;

import com.ecole.model.ProfilProfesseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilProfesseurRepository extends JpaRepository<ProfilProfesseur, Long> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées ici si nécessaire
}