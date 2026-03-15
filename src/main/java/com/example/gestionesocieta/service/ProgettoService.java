package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Progetto;

import java.util.List;

public interface ProgettoService {

    public List<Progetto> listAllProgetti();

    public Progetto caricaSingoloProgetti(Long id);

    public void aggiorna(Progetto progettoInstance);

    public void inserisciProgetto(Progetto progettoInstance);

    public void rimuovi(Long idProgetto);

    List<Progetto> cercaProgettiConAlmenoUnDipendenteBenPagato();

    public List<Progetto> elencaProgettiAnomali();

}
