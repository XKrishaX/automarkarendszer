package hu.autodb.service;

import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.repository.MarkaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Márka üzleti logika.
 */
@Service
@Transactional
public class MarkaService {

    private final MarkaRepository markaRepository;

    public MarkaService(MarkaRepository markaRepository) {
        this.markaRepository = markaRepository;
    }

    @Transactional(readOnly = true)
    public List<Marka> getAll() {
        return markaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marka getById(Long id) {
        return markaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Márka nem található, id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Marka> searchByNev(String nev) {
        return markaRepository.findByNevContainingIgnoreCase(nev);
    }

    @Transactional(readOnly = true)
    public List<Marka> searchByOrszag(String orszag) {
        return markaRepository.findByOrszagContainingIgnoreCase(orszag);
    }

    public Marka create(Marka marka) {
        if (markaRepository.existsByNev(marka.getNev())) {
            throw new IllegalArgumentException("Ez a márkanév már létezik: " + marka.getNev());
        }
        return markaRepository.save(marka);
    }

    public Marka update(Long id, Marka adatok) {
        Marka marka = getById(id);
        if (!marka.getNev().equals(adatok.getNev()) && markaRepository.existsByNev(adatok.getNev())) {
            throw new IllegalArgumentException("Ez a márkanév már létezik: " + adatok.getNev());
        }
        marka.setNev(adatok.getNev());
        marka.setOrszag(adatok.getOrszag());
        marka.setAlapitasEve(adatok.getAlapitasEve());
        return markaRepository.save(marka);
    }

    public void delete(Long id) {
        if (!markaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Márka nem található, id: " + id);
        }
        markaRepository.deleteById(id);
    }
}
