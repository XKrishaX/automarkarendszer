package hu.autodb.repository;

import hu.autodb.model.Marka;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkaRepository extends JpaRepository<Marka, Long> {

    List<Marka> findByNevContainingIgnoreCase(String nev);

    List<Marka> findByOrszagContainingIgnoreCase(String orszag);

    boolean existsByNev(String nev);
}
