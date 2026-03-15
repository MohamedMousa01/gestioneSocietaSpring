package com.example.gestionesocieta.repository;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface SocietaRepository extends CrudRepository<Societa, Long>, QueryByExampleExecutor<Societa> {


    // la query viene costruita in automatico!!!
    List<Societa> findByRagioneSociale(String ragioneSociale);

    boolean existsByRagioneSociale(String ragioneSociale);

    @Query("SELECT DISTINCT s.ragioneSociale " +
            "FROM Societa s " +
            "JOIN s.dipendenti d " +
            "JOIN d.progetti p " +
            "WHERE p.durataInMesi > 12")
    List<String> findNomiSocietaConProgettiPiuLunghiDiUnAnno();



    @Query("SELECT DISTINCT s FROM Societa s " +
            "JOIN s.dipendenti d " +
            "WHERE d.dataAssunzione < s.dataFondazione")
    List<Societa> findSocietaConAssunzioniAnomale();

}
