package hu.autodb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modell")
public class Modell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marka_id", nullable = false)
    @JsonIgnoreProperties({"modellek", "tulajdonosok", "hibernateLazyInitializer", "handler"})
    private Marka marka;

    @NotBlank(message = "A modell neve nem lehet üres")
    @Column(nullable = false)
    private String nev;

    @NotNull(message = "Az évjárat kötelező")
    @Min(value = 1886, message = "Az évjárat legalább 1886 kell legyen")
    @Max(value = 2100, message = "Az évjárat legfeljebb 2100 lehet")
    @Column(nullable = false)
    private Integer ev;

    @NotBlank(message = "A kategória nem lehet üres")
    @Column(nullable = false)
    private String kategoria;

    @NotNull(message = "A motorméret kötelező")
    @Positive(message = "A motorméret pozitív szám kell legyen")
    @Column(name = "motor_cm3", nullable = false)
    private Integer motorCm3;

    public Modell() {
    }

    public Modell(Marka marka, String nev, Integer ev, String kategoria, Integer motorCm3) {
        this.marka = marka;
        this.nev = nev;
        this.ev = ev;
        this.kategoria = kategoria;
        this.motorCm3 = motorCm3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marka getMarka() {
        return marka;
    }

    public void setMarka(Marka marka) {
        this.marka = marka;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Integer getEv() {
        return ev;
    }

    public void setEv(Integer ev) {
        this.ev = ev;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public Integer getMotorCm3() {
        return motorCm3;
    }

    public void setMotorCm3(Integer motorCm3) {
        this.motorCm3 = motorCm3;
    }

}
