package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;

import java.util.List;

public interface DipendenteService {

    public List<Dipendente> listAllAbitanti();

    public Dipendente caricaSingoloDipendente(Long id);

    public void aggiorna(Dipendente dipendenteInstance);

    public void inserisciNuovo(Dipendente dipendenteInstance, Societa societa);

    public void rimuovi(Long idDipendente);

    public void aggiungiProgetti(Long idDipendente, List<Progetto> progettiInstance);

    public Dipendente recuperaIlPiuAnzianoCriteriSpeciali();
}
