package com.example.gestionesocieta;

import com.example.gestionesocieta.service.test.BatteriaDiTestSocietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionesocietaApplication implements CommandLineRunner {

	@Autowired
	private BatteriaDiTestSocietaService batteriaDiTestSocietaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		System.out.println("########### AVVIO DAL RUN ##############");

		batteriaDiTestSocietaService.testAggiungiSocieta();
		batteriaDiTestSocietaService.testRimuoviSocieta();
		batteriaDiTestSocietaService.testFindByExample();
		batteriaDiTestSocietaService.testInserisciDipendente();
		batteriaDiTestSocietaService.testInserimentoProgetto();

		System.out.println("########### FINE DEL RUN ##############");
	}
}


