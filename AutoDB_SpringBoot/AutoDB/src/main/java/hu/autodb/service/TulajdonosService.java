package hu.autodb.service;

import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.model.Tulajdonos;
import hu.autodb.repository.TulajdonosRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tulajdonos üzleti logika.
 */
@Service
@Transactional
public class TulajdonosService {

    private final TulajdonosRepository tulajdonosRepository;
    private final MarkaService markaService;

    public TulajdonosService(TulajdonosRepository tulajdonosRepository, MarkaService markaService) {
        this.tulajdonosRepository = tulajdonosRepository;
        this.markaService = markaService;
    }

    @Transactional(readOnly = true)
    public List<Tulajdonos> getAll() {
        return tulajdonosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tulajdonos getById(Long id) {
        return tulajdonosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tulajdonos nem található, id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Tulajdonos> getByMarkaId(Long markaId) {
        markaService.getById(markaId);
        return tulajdonosRepository.findByMarkaId(markaId);
    }

    public Tulajdonos create(Long markaId, Tulajdonos tulajdonos) {
        if (tulajdonosRepository.existsByEmail(tulajdonos.getEmail())) {
            throw new IllegalArgumentException("Ez az e-mail cím már regisztrált: " + tulajdonos.getEmail());
        }
        Marka marka = markaService.getById(markaId);
        tulajdonos.setMarka(marka);
        return tulajdonosRepository.save(tulajdonos);
    }

    public Tulajdonos update(Long id, Tulajdonos adatok) {
        Tulajdonos tulajdonos = getById(id);
        if (!tulajdonos.getEmail().equals(adatok.getEmail())
                && tulajdonosRepository.existsByEmail(adatok.getEmail())) {
            throw new IllegalArgumentException("Ez az e-mail cím már foglalt: " + adatok.getEmail());
        }
        tulajdonos.setNev(adatok.getNev());
        tulajdonos.setEmail(adatok.getEmail());
        tulajdonos.setTelefonszam(adatok.getTelefonszam());
        return tulajdonosRepository.save(tulajdonos);
    }

    public void delete(Long id) {
        if (!tulajdonosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tulajdonos nem található, id: " + id);
        }
        tulajdonosRepository.deleteById(id);
    }
}
