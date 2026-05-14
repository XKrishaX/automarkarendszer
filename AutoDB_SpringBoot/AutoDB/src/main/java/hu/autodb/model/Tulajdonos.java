package hu.autodb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tulajdonos")
public class Tulajdonos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marka_id", nullable = false)
    @JsonIgnoreProperties({"modellek", "tulajdonosok", "hibernateLazyInitializer", "handler"})
    private Marka marka;

    @NotBlank(message = "A tulajdonos neve nem lehet üres")
    @Column(nullable = false)
    private String nev;

    @NotBlank(message = "Az e-mail cím nem lehet üres")
    @Email(message = "Érvényes e-mail cím szükséges")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A telefonszám nem lehet üres")
    @Column(nullable = false)
    private String telefonszam;

    public Tulajdonos() {
    }

    public Tulajdonos(Marka marka, String nev, String email, String telefonszam) {
        this.marka = marka;
        this.nev = nev;
        this.email = email;
        this.telefonszam = telefonszam;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonszam() {
        return telefonszam;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

}
