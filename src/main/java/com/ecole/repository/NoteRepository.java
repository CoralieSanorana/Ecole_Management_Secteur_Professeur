package com.example.back_ecole.repository;

import com.example.back_ecole.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByEtudiant_id(Long etudiant_id);
}
