package hu.autodb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marka")
public class Marka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A márka neve nem lehet üres")
    @Column(nullable = false, unique = true)
    private String nev;

    @NotBlank(message = "Az ország neve nem lehet üres")
    @Column(nullable = false)
    private String orszag;

    @NotNull(message = "Az alapítás éve kötelező")
    @Min(value = 1800, message = "Az alapítás éve legalább 1800 kell legyen")
    @Max(value = 2100, message = "Az alapítás éve legfeljebb 2100 lehet")
    @Column(name = "alapitas_eve", nullable = false)
    private Integer alapitasEve;

    @OneToMany(mappedBy = "marka", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modell> modellek = new ArrayList<>();

    @OneToMany(mappedBy = "marka", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tulajdonos> tulajdonosok = new ArrayList<>();

    public Marka() {
    }

    public Marka(String nev, String orszag, Integer alapitasEve) {
        this.nev = nev;
        this.orszag = orszag;
        this.alapitasEve = alapitasEve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }

    public Integer getAlapitasEve() {
        return alapitasEve;
    }

    public void setAlapitasEve(Integer alapitasEve) {
        this.alapitasEve = alapitasEve;
    }

    public List<Modell> getModellek() {
        return modellek;
    }

    public List<Tulajdonos> getTulajdonosok() {
        return tulajdonosok;
    }
}
