package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Societa;

import java.util.List;

public interface SocietaService {

    public List<Societa> listAllSocieta();

    public Societa caricaSingoloSocieta(Long id);

    public void aggiorna(Societa societaInstance);

    public void inserisciNuovo(Societa societaInstance);

    public void rimuovi(Long idSocieta);

    public List<Societa> findByExample(Societa example);


}
