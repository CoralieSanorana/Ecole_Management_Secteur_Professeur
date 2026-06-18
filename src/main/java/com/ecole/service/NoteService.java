package com.ecole.service;

import com.ecole.entity.Note;
import com.ecole.repository.NoteRepository;
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
        return noteRepository.findByEtudiantId(etudiantId);
    }

    public List<Note> findByEtudiantIdByMatiereId(Long etudiantId, Long matiereId) {
        return noteRepository.findByEtudiantIdByMatiereId(etudiantId, matiereId);
    }

    public List<Note> findByEtudiantIdByMatiereIdByPeriodeId(Long etudiantId, Long matiereId, Long periodeId) {
        return noteRepository.findByEtudiantIdByMatiereIdByPeriodeId(etudiantId, matiereId, periodeId);
    }

    public List<Note> findByEtudiantIdByPeriodeId(Long etudiantId, Long periodeId) {
        return noteRepository.findByEtudiantIdByPeriodeId(etudiantId, periodeId);
    }
}

