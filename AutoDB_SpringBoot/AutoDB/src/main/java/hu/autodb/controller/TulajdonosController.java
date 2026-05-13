package hu.autodb.controller;

import hu.autodb.model.Tulajdonos;
import hu.autodb.service.TulajdonosService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Tulajdonos REST API végpontok.
 */
@RestController
@RequestMapping("/api/tulajdonosok")
public class TulajdonosController {

    private final TulajdonosService tulajdonosService;

    public TulajdonosController(TulajdonosService tulajdonosService) {
        this.tulajdonosService = tulajdonosService;
    }

    /** Összes tulajdonos lekérdezése. */
    @GetMapping
    public List<Tulajdonos> getAll() {
        return tulajdonosService.getAll();
    }

    /** Egy tulajdonos lekérdezése ID alapján. */
    @GetMapping("/{id}")
    public Tulajdonos getById(@PathVariable Long id) {
        return tulajdonosService.getById(id);
    }

    /** Tulajdonosok egy adott márkához. */
    @GetMapping("/marka/{markaId}")
    public List<Tulajdonos> getByMarkaId(@PathVariable Long markaId) {
        return tulajdonosService.getByMarkaId(markaId);
    }

    /** Új tulajdonos hozzáadása. */
    @PostMapping("/marka/{markaId}")
    public ResponseEntity<Tulajdonos> create(
            @PathVariable Long markaId,
            @Valid @RequestBody Tulajdonos tulajdonos) {
        Tulajdonos saved = tulajdonosService.create(markaId, tulajdonos);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Tulajdonos módosítása. */
    @PutMapping("/{id}")
    public Tulajdonos update(@PathVariable Long id, @Valid @RequestBody Tulajdonos tulajdonos) {
        return tulajdonosService.update(id, tulajdonos);
    }

    /** Tulajdonos törlése. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tulajdonosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
