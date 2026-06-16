package repository;

import entity.HoraireEdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireEdtRepository extends JpaRepository<HoraireEdt, Long> {
}
