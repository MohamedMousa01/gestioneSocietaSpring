package com.example.gestionesocieta.repository;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDate;
import java.util.List;

public interface ProgettoRepository extends CrudRepository<Progetto, Long>, QueryByExampleExecutor<Progetto> {

    // la query viene costruita in automatico!!!
    List<Progetto> findByNome(String name);


    List<Progetto> searchByDipendenti(Dipendente dipendenteInput);


    @Query("SELECT DISTINCT p FROM Progetto p " +
            "JOIN p.dipendenti d " +
            "WHERE d.redditoAnnuoLordo >= :minRal")
    List<Progetto> findProgettiConDipendentiRALSuperiore(@Param("minRal") Integer minRal);


    @Query("SELECT DISTINCT p FROM Progetto p " +
            "JOIN p.dipendenti d " +
            "JOIN d.societa s " +
            "WHERE s.dataChiusura IS NOT NULL " +
            "AND s.dataChiusura <= :oggi")
    List<Progetto> findProgettiAnomali(@Param("oggi") LocalDate oggi);


//    // se voglio un caricamento EAGER per esempio by cognome
//    // anche se i caricamenti EAGER conviene scriverli in JPQL
//    @EntityGraph(attributePaths = { "dipendente" })
//    List<Dipendente> findByCognome(String cognome);
}
