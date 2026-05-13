package hu.autodb.controller;

import hu.autodb.model.Modell;
import hu.autodb.service.ModellService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modell REST API végpontok.
 */
@RestController
@RequestMapping("/api/modellek")
public class ModellController {

    private final ModellService modellService;

    public ModellController(ModellService modellService) {
        this.modellService = modellService;
    }

    /** Összes modell lekérdezése. */
    @GetMapping
    public List<Modell> getAll() {
        return modellService.getAll();
    }

    /** Egy modell lekérdezése ID alapján. */
    @GetMapping("/{id}")
    public Modell getById(@PathVariable Long id) {
        return modellService.getById(id);
    }

    /** Modellek egy adott márkához. */
    @GetMapping("/marka/{markaId}")
    public List<Modell> getByMarkaId(@PathVariable Long markaId) {
        return modellService.getByMarkaId(markaId);
    }

    /** Keresés kategória alapján. */
    @GetMapping("/kereses/kategoria")
    public List<Modell> getByKategoria(@RequestParam String q) {
        return modellService.getByKategoria(q);
    }

    /** Keresés modellnévben. */
    @GetMapping("/kereses/nev")
    public List<Modell> searchByNev(@RequestParam String q) {
        return modellService.searchByNev(q);
    }

    /** Új modell létrehozása egy márkához. */
    @PostMapping("/marka/{markaId}")
    public ResponseEntity<Modell> create(@PathVariable Long markaId, @Valid @RequestBody Modell modell) {
        Modell saved = modellService.create(markaId, modell);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Modell módosítása. */
    @PutMapping("/{id}")
    public Modell update(@PathVariable Long id, @Valid @RequestBody Modell modell) {
        return modellService.update(id, modell);
    }

    /** Modell törlése. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modellService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
