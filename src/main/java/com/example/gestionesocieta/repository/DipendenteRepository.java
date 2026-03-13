package com.example.gestionesocieta.repository;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>, QueryByExampleExecutor<Dipendente> {

    // la query viene costruita in automatico!!!
    List<Dipendente> findByNome(String name);


    List<Dipendente> findByProgetti(Progetto progettoInput);


//    // se voglio un caricamento EAGER per esempio by cognome
//    // anche se i caricamenti EAGER conviene scriverli in JPQL
//    @EntityGraph(attributePaths = { "progetti" })
//    List<Dipendente> findByCognome(String cognome);
}
