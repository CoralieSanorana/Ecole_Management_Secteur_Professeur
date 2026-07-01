package com.ecole.repository;

import com.ecole.entity.EmploiDuTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmploiDuTempsRepository extends JpaRepository<EmploiDuTemps, Long> {
    List<EmploiDuTemps> findByAffectationId(Long affectationId);
}
