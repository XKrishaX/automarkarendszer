package hu.autodb.controller;

import hu.autodb.model.Marka;
import hu.autodb.service.MarkaService;
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
 * Márka REST API végpontok.
 */
@RestController
@RequestMapping("/api/markak")
public class MarkaController {

    private final MarkaService markaService;

    public MarkaController(MarkaService markaService) {
        this.markaService = markaService;
    }

    /** Összes márka lekérdezése. */
    @GetMapping
    public List<Marka> getAll() {
        return markaService.getAll();
    }

    /** Egy márka lekérdezése ID alapján. */
    @GetMapping("/{id}")
    public Marka getById(@PathVariable Long id) {
        return markaService.getById(id);
    }

    /** Keresés márkanévben. */
    @GetMapping("/kereses/nev")
    public List<Marka> searchByNev(@RequestParam String q) {
        return markaService.searchByNev(q);
    }

    /** Keresés ország alapján. */
    @GetMapping("/kereses/orszag")
    public List<Marka> searchByOrszag(@RequestParam String q) {
        return markaService.searchByOrszag(q);
    }

    /** Új márka létrehozása. */
    @PostMapping
    public ResponseEntity<Marka> create(@Valid @RequestBody Marka marka) {
        Marka saved = markaService.create(marka);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Márka módosítása. */
    @PutMapping("/{id}")
    public Marka update(@PathVariable Long id, @Valid @RequestBody Marka marka) {
        return markaService.update(id, marka);
    }

    /** Márka törlése. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        markaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
