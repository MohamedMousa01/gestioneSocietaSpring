package com.example.gestionesocieta.service.test;

import com.example.gestionesocieta.model.Dipendente;
import com.example.gestionesocieta.model.Progetto;
import com.example.gestionesocieta.model.Societa;
import com.example.gestionesocieta.service.DipendenteService;
import com.example.gestionesocieta.service.ProgettoService;
import com.example.gestionesocieta.service.SocietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class  BatteriaDiTestSocietaService {

    @Autowired
    SocietaService societaService;

    @Autowired
    DipendenteService dipendenteService;

    @Autowired
    ProgettoService progettoService;

    public void testAggiungiSocieta(){
        System.out.println("----------INIZIO testAggiungiSocieta-------");

        int size1 = societaService.listAllSocieta().size();
        Societa societaProva1 = new Societa("ZARA" , "Via Roma 10", LocalDate.of(2000,01,01), LocalDate.of(2025,12,31));
        societaService.inserisciNuovo(societaProva1);

        int size2 = societaService.listAllSocieta().size();
        if(size1 == size2)
            throw new RuntimeException("-----testAggiungiSocieta FALLITO------");
        System.out.println("---------- testAggiungiSocieta SUCCESS------");
        System.out.println("************************************************************");
    }



    public void testRimuoviSocieta(){
        System.out.println("-----------INIZIO testRimuoviSocieta--------");

        List<Societa> listaSocieta = societaService.listAllSocieta();
        int size1 = societaService.listAllSocieta().size();
        if(listaSocieta == null)
            throw new RuntimeException("Non ci sono elementi da rimuovere");

        societaService.rimuovi(1L);
        int size2 = societaService.listAllSocieta().size();
        if(size1 == size2)
            throw new RuntimeException("testRimuoviSocieta FALLITO");
        System.out.println("--------------testRimuoviSocieta SUCCESS--------------");
        System.out.println("************************************************************");
    }



    public void testFindByExample(){
        System.out.println("-----------INIZIO testFindByExample--------");

        Societa societaProva = new Societa("EURONICS" , "Via Milano 20", LocalDate.of(2001,02,02), LocalDate.of(2024,11,30));
        societaService.inserisciNuovo(societaProva);


        List<Societa> societaTrovate =  societaService.findByExample(societaProva);
        if(societaTrovate == null)
            throw new RuntimeException("testFindByExample FALLITO");
        System.out.println("---------------- testFindByExample SUCCESS-----------------");
        System.out.println("************************************************************");
    }



    public void testInserisciDipendente(){
        System.out.println("------------- INIZIO testInserisciDipendente --------------");

        Societa societa1 = new Societa("OVS", "Via Firenze 30",LocalDate.of(2002, 03,14), LocalDate.of(2023,10,31));
        Dipendente dipendente1 = new Dipendente("Marco", "Rossi", LocalDate.of(2016,01,01), 35000, null);

        societaService.inserisciNuovo(societa1);
        dipendenteService.inserisciNuovo(dipendente1,societa1);
        if(dipendente1.getSocieta() == null)
            throw new RuntimeException("------------ testInserisciDipendente FAILURE -------------");
        System.out.println("--------------- testInserisciDipendente SUCCESS -----------------");
        System.out.println("*********************************************************************");
    }



    public void testInserimentoProgetto(){
        System.out.println("--------------Inzio testInserimentoProgetto---------------");

        int size1 = progettoService.listAllProgetti().size();
        Progetto progetto1 = new Progetto("Progetto Nasa", "NASA" ,6);
        progettoService.inserisciProgetto(progetto1);
        int size2 = progettoService.listAllProgetti().size();
        if(size1 == size2)
            throw new RuntimeException("testInserimentoProgetto FAILURE");
        System.out.println("------------- testInserimentoProgetto SUCCESS ------------");

    }



    public void testAssociaProgetto(){

        System.out.println("--------------Inizio testAssociaProgetto-------------");

        Societa s = new Societa();
        s.setDataFondazione(LocalDate.of(2020, 1, 1));
        s.setDataChiusura(LocalDate.now().plusMonths(6));
        societaService.inserisciNuovo(s);

        // Creiamo dipendente
        Dipendente d = new Dipendente();
        d.setNome("Luca");
        d.setSocieta(s);
        d.setDataAssunzione(LocalDate.now());
        dipendenteService.inserisciNuovo(d,s);

        // Progetto 1: Durata 3 mesi (OK)
        Progetto p1 = new Progetto();
        p1.setNome("Sito Web");
        p1.setDurataInMesi(3);
        progettoService.inserisciProgetto(p1);

        // Progetto 2: Durata 12 mesi (ERRORE - sfora la chiusura della società)
        Progetto p2 = new Progetto();
        p2.setNome("App Mobile complessa");
        p2.setDurataInMesi(12);
        progettoService.inserisciProgetto(p2);

        try {
            dipendenteService.aggiungiProgetti(d.getId(), List.of(p1, p2));
        } catch (RuntimeException e) {
            System.out.println("TEST SUCCESS: Bloccato progetto troppo lungo: " + e.getMessage());
        }

    }




    public void testSocietaProgettiLunghi() {
        System.out.println("--- Inizio Test Società con Progetti > 1 anno ---");

        List<String> nomiSocieta = societaService.elencaSocietaConProgettiLontani();

        if (nomiSocieta.isEmpty()) {
            System.out.println("Nessuna società trovata con progetti così lunghi.");
        } else {
            nomiSocieta.forEach(nome -> System.out.println("Società trovata: " + nome));
        }
    }




    public void testDipendenteAnziano() {
        System.out.println("--- Ricerca Dipendente Più Anziano (Società < 1990 & Progetto >= 6m) ---");

        Dipendente anziano = dipendenteService.recuperaIlPiuAnzianoCriteriSpeciali();

        if (anziano != null) {
            System.out.println("Il veterano è: " + anziano.getNome() + " " + anziano.getCognome());
            System.out.println("Assunto il: " + anziano.getDataAssunzione());
            System.out.println("Lavora per: " + anziano.getSocieta().getRagioneSociale());
        } else {
            System.out.println("Nessun dipendente soddisfa tutti i criteri contemporaneamente.");
        }
    }




    



}
