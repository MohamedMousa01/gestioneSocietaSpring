package com.example.gestionesocieta.service;


import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;
import com.example.gestionesocieta.repository.DipendenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DipendenteServiceImpl implements DipendenteService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DipendenteRepository dipendenteRepository;


    public List<Dipendente> listAllAbitanti() {
        return (List<Dipendente>) dipendenteRepository.findAll() ;
    }

    public Dipendente caricaSingoloDipendente(Long id) {
        return dipendenteRepository.findById(id).orElse(null);
    }

    @Transactional
    public void aggiorna(Dipendente dipendenteInstance) {  dipendenteRepository.save(dipendenteInstance);}

    @Transactional
    public void inserisciNuovo(Dipendente dipendente, Societa societa) {

        if (dipendente.getDataAssunzione().isBefore(societa.getDataFondazione()))
            throw new RuntimeException("La data di assunzione non piò essere prima della data di fondazione della societa");

        dipendente.setSocieta(societa);
        dipendenteRepository.save(dipendente);
    }

    @Transactional
    public void rimuovi(Long idDipendente) { dipendenteRepository.deleteById(idDipendente);}


 @Transactional
    public void aggiungiProgetti(Long idDipendente, List<Progetto> progettiInstance){

        if(idDipendente == null || progettiInstance == null)
            throw new RuntimeException("errore negli input");

        Dipendente dipendente = dipendenteRepository.findById(idDipendente)
                .orElseThrow(() -> new RuntimeException("Dipendente non trovato"));
        Societa societa = dipendente.getSocieta();

        for (Progetto progetto : progettiInstance)
            if (societa.getDataChiusura() != null) {
                LocalDate dataFineProgetto = LocalDate.now().plusMonths(progetto.getDurataInMesi());

                if (dataFineProgetto.isAfter(societa.getDataChiusura())) {
                    throw new RuntimeException("Errore: Il progetto '" + progetto.getNome()
                            + "' dura " + progetto.getDurataInMesi() + " mesi e finirebbe il " + dataFineProgetto
                            + ", ma la società chiude il " + societa.getDataChiusura());
                }

                dipendente.getProgetti().add(progetto);
            }
        dipendenteRepository.save(dipendente);
    }




    @Transactional(readOnly = true)
    public Dipendente recuperaIlPiuAnzianoCriteriSpeciali() {

        java.time.LocalDate data1990 = java.time.LocalDate.of(1990, 1, 1);


        List<Dipendente> risultati = dipendenteRepository.findDipendentiAnzianiConCriteri(
                data1990, 6, PageRequest.of(0, 1));


        return risultati.isEmpty() ? null : risultati.get(0);
    }

}





















