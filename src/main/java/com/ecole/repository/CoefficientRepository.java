package repository;

import entity.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoefficientRepository extends JpaRepository<Coefficient, Long> {
}
