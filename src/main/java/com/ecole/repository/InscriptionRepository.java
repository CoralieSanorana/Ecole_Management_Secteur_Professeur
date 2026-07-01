package com.ecole.repository;

import com.ecole.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    
    List<Inscription> findByClasseId(Long classeId);

    @Query("SELECT i FROM Inscription i JOIN ProfilEtudiant p ON i.etudiantId = p.id " +
           "WHERE i.classeId = :classeId AND " +
           "(LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(p.prenom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(p.matricule) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Inscription> findByClasseIdAndStudentName(
        @Param("classeId") Long classeId, 
        @Param("search") String search, 
        Pageable pageable
    );
}