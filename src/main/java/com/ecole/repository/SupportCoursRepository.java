package repository;

import entity.SupportCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportCoursRepository extends JpaRepository<SupportCours, Long> {
}
