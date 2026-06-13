package com.example.back_ecole.repository;

import com.example.back_ecole.model.TypeFichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFichierRepository extends JpaRepository<TypeFichier, Long> {
}
