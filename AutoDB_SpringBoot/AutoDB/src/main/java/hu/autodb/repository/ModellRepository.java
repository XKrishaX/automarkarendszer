package hu.autodb.repository;

import hu.autodb.model.Modell;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModellRepository extends JpaRepository<Modell, Long> {

    List<Modell> findByMarkaId(Long markaId);

    List<Modell> findByKategoriaIgnoreCase(String kategoria);

    List<Modell> findByNevContainingIgnoreCase(String nev);
}
