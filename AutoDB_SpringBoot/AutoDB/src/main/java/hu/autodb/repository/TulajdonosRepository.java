package hu.autodb.repository;

import hu.autodb.model.Tulajdonos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TulajdonosRepository extends JpaRepository<Tulajdonos, Long> {

    List<Tulajdonos> findByMarkaId(Long markaId);

    boolean existsByEmail(String email);
}
