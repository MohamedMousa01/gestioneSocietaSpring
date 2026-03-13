package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.repository.ProgettoRepository;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProgettoServiceImpl implements ProgettoService{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProgettoRepository progettoRepository;

    public List<Progetto> listAllProgetti(){ return (List<Progetto>) progettoRepository.findAll();}

    public Progetto caricaSingoloProgetti(Long id) { return progettoRepository.findById(id).orElse(null); }

    @Transactional
    public void aggiorna( Progetto progettoInstance) { progettoRepository.save(progettoInstance);}


    @Transactional
    public void rimuovi(Long id){ progettoRepository.deleteById(id);}

    @Transactional
    public void inserisciProgetto(Progetto progettoInstance) {
        if(progettoInstance == null)
            throw new RuntimeException("Progetto da inserire Invalido");
        progettoRepository.save(progettoInstance);
    }




}
