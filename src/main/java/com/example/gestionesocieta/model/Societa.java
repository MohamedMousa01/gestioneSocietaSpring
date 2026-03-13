package com.example.gestionesocieta.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "societa")
public class Societa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ragionesociale")
    private String ragioneSociale;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "datafondazione")
    private LocalDate dataFondazione;
    @Column(name = "datachiusura")
    private LocalDate dataChiusura;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "societa")
    private Set<Dipendente> dipendenti = new HashSet<>();

    public Societa(){}

    public Societa(String ragioneSociale, String indirizzo, LocalDate dataFondazione, LocalDate dataChiusura) {
        this.ragioneSociale = ragioneSociale;
        this.indirizzo = indirizzo;
        this.dataFondazione = dataFondazione;
        this.dataChiusura = dataChiusura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public LocalDate getDataFondazione() {
        return dataFondazione;
    }

    public void setDataFondazione(LocalDate dataFondazione) {
        this.dataFondazione = dataFondazione;
    }

    public LocalDate getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(LocalDate dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Set<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(Set<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }

    @Override
    public String toString() {
        return "Societa{" +
                "id=" + id +
                ", ragioneSociale='" + ragioneSociale + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", dataFondazione=" + dataFondazione +
                ", dataChiusura=" + dataChiusura +
                '}';
    }
}
