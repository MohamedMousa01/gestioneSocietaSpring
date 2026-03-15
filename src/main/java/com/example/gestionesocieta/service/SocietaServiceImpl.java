package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;
import com.example.gestionesocieta.repository.ProgettoRepository;
import com.example.gestionesocieta.repository.SocietaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SocietaServiceImpl implements SocietaService {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SocietaRepository societaRepository;

    public List<Societa> listAllSocieta(){ return (List<Societa>) societaRepository.findAll();}

    public Societa caricaSingoloSocieta(Long id) { return societaRepository.findById(id).orElse(null); }

    @Transactional
    public void aggiorna( Societa societaInstance) { societaRepository.save(societaInstance);}

    @Transactional
    public void inserisciNuovo(Societa societaInstance) {
        if(societaRepository.existsByRagioneSociale(societaInstance.getRagioneSociale()))
            throw new RuntimeException("Societa con ragione Sociale già esistente");

        societaRepository.save(societaInstance);
    }

    @Transactional
    public void rimuovi(Long id){

        if(caricaSingoloSocieta(id).getDipendenti().size() != 0)
            throw new RuntimeException("NON è POSSIBILE ELIMINARE LA SOCIETA' POICHE HA DIPENDENTI");
        societaRepository.deleteById(id);
    }



    public List<Societa> findByExample(Societa example){

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return (List<Societa>) societaRepository.findAll(Example.of(example, matcher));
    }


    @Transactional(readOnly = true)
    public List<String> elencaClientiProgettiSocieta(Long idSocieta) {
        Societa societaInstance = caricaSingoloSocieta(idSocieta);

        List<String> clienti = societaInstance.getDipendenti().stream()
                .flatMap(d -> d.getProgetti().stream())
                .map(p -> p.getCliente())
                .distinct()
                .toList();

        return clienti;
    }



    @Transactional(readOnly = true)
    public List<String> elencaSocietaConProgettiLontani() {
        // Richiama semplicemente il metodo della repository che abbiamo scritto sopra
        return societaRepository.findNomiSocietaConProgettiPiuLunghiDiUnAnno();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Societa> elencaSocietaConAnomalieAssunzione() {
        // Recupera le società dove almeno un dipendente è stato assunto prima della fondazione
        return societaRepository.findSocietaConAssunzioniAnomale();
    }


}
