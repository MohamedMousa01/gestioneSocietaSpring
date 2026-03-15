package com.example.gestionesocieta.service;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;
import com.example.gestionesocieta.repository.ProgettoRepository;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProgettoServiceImpl implements ProgettoService{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    DipendenteService dipendenteService;

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


    @Transactional
    public void collegaDipendentiAProgetto(Long idProgetto, List<Long> idsDipendenti) {
        // 1. Recupero il progetto
        Progetto progetto = progettoRepository.findById(idProgetto)
                .orElseThrow(() -> new RuntimeException("Progetto non trovato"));

        // 2. Recupero tutti i dipendenti dalla lista di ID
        List<Dipendente> dipendentiDaAggiungere = dipendenteRepository.findAllById(idsDipendenti);

        // 3. Calcoliamo la data di fine del progetto (basata sulla durata)
        // Se il progetto non ha una data inizio specifica, usiamo oggi
        LocalDate dataFineProgetto = LocalDate.now().plusMonths(progetto.getDurataInMesi());

        // 4. Ciclo sui dipendenti per verificare le rispettive società
        for (Dipendente dipendente : dipendentiDaAggiungere) {
            Societa societaAppartenenza = dipendente.getSocieta();

            // Se la società di questo specifico dipendente ha una data chiusura
            if (societaAppartenenza.getDataChiusura() != null) {

                if (dataFineProgetto.isAfter(societaAppartenenza.getDataChiusura())) {
                    throw new RuntimeException("Impossibile collegare il dipendente " + dipendente.getNome() +
                            " " + dipendente.getCognome() + ". La sua società (" + societaAppartenenza.getRagioneSociale() +
                            ") chiude il " + societaAppartenenza.getDataChiusura() +
                            ", ma il progetto termina il " + dataFineProgetto);
                }
            }
        }

        progetto.getDipendenti().addAll(dipendentiDaAggiungere);

        // 6. Salvataggio (Hibernate aggiorna la tabella di join dipendente_progetto)
        progettoRepository.save(progetto);
    }




    @Override
    @Transactional(readOnly = true)
    public List<Progetto> cercaProgettiConAlmenoUnDipendenteBenPagato() {
        // Chiamiamo la query passando 30000 come soglia
        return progettoRepository.findProgettiConDipendentiRALSuperiore(30000);
    }



    @Override
    @Transactional(readOnly = true)
    public List<Progetto> elencaProgettiAnomali() {
        // Passiamo la data odierna come parametro di confronto
        return progettoRepository.findProgettiAnomali(LocalDate.now());
    }





}
