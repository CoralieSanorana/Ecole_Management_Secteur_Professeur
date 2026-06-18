package com.ecole.repository;

import com.ecole.entity.SupportCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportCoursRepository extends JpaRepository<SupportCours, Long> {
}
