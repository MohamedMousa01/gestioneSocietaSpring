package com.example.gestionesocieta.repository;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ProgettoRepository extends CrudRepository<Progetto, Long>, QueryByExampleExecutor<Progetto> {

    // la query viene costruita in automatico!!!
    List<Progetto> findByNome(String name);


    List<Progetto> searchByDipendenti(Dipendente dipendenteInput);


//    // se voglio un caricamento EAGER per esempio by cognome
//    // anche se i caricamenti EAGER conviene scriverli in JPQL
//    @EntityGraph(attributePaths = { "dipendente" })
//    List<Dipendente> findByCognome(String cognome);
}
