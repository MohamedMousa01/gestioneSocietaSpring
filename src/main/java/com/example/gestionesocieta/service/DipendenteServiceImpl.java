package com.example.gestionesocieta.service;


import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Societa;
import com.example.gestionesocieta.repository.DipendenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



}
