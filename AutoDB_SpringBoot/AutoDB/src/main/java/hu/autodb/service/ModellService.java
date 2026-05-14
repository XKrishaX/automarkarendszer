package hu.autodb.service;

import hu.autodb.exception.ResourceNotFoundException;
import hu.autodb.model.Marka;
import hu.autodb.model.Modell;
import hu.autodb.repository.ModellRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModellService {

    private final ModellRepository modellRepository;
    private final MarkaService markaService;

    public ModellService(ModellRepository modellRepository, MarkaService markaService) {
        this.modellRepository = modellRepository;
        this.markaService = markaService;
    }

    @Transactional(readOnly = true)
    public List<Modell> getAll() {
        return modellRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Modell getById(Long id) {
        return modellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modell nem található, id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Modell> getByMarkaId(Long markaId) {
        markaService.getById(markaId);
        return modellRepository.findByMarkaId(markaId);
    }

    @Transactional(readOnly = true)
    public List<Modell> getByKategoria(String kategoria) {
        return modellRepository.findByKategoriaIgnoreCase(kategoria);
    }

    @Transactional(readOnly = true)
    public List<Modell> searchByNev(String nev) {
        return modellRepository.findByNevContainingIgnoreCase(nev);
    }

    public Modell create(Long markaId, Modell modell) {
        Marka marka = markaService.getById(markaId);
        modell.setMarka(marka);
        return modellRepository.save(modell);
    }

    public Modell update(Long id, Modell adatok) {
        Modell modell = getById(id);
        modell.setNev(adatok.getNev());
        modell.setEv(adatok.getEv());
        modell.setKategoria(adatok.getKategoria());
        modell.setMotorCm3(adatok.getMotorCm3());
        return modellRepository.save(modell);
    }

    public void delete(Long id) {
        if (!modellRepository.existsById(id)) {
            throw new ResourceNotFoundException("Modell nem található, id: " + id);
        }
        modellRepository.deleteById(id);
    }
}
