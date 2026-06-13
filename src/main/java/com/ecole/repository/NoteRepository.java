package com.example.back_ecole.repository;

import com.example.back_ecole.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEtudiantId(Long etudiantId);
    
    @Query("SELECT n FROM Note n JOIN AffectationEnseignement a ON n.affectationId = a.id WHERE n.etudiantId = :etudiantId AND a.matiereId = :matiereId")
    List<Note> findByEtudiantIdByMatiereId(@Param("etudiantId") Long etudiantId, @Param("matiereId") Long matiereId);
}
