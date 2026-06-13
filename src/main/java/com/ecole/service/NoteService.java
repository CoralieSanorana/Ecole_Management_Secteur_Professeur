package com.example.back_ecole.service;

import com.example.back_ecole.model.Note;
import com.example.back_ecole.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> findByEtudiantId(Long etudiantId) {
        return noteRepository.findByEtudiant_id(etudiantId);
    }

    public List<Note> findByEtudiantIdByMatiereId(Long etudiantId, Long matiereId) {
        return noteRepository.findByEtudiantIdByMatiereId(etudiantId, matiereId);
    }
}
