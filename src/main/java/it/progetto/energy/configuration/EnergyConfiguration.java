package it.progetto.energy.configuration;

import it.progetto.energy.model.*;
import it.progetto.energy.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import it.progetto.energy.repository.IndirizzoOperativoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe che gestisce la configurazione dei BEAN principalmente di Classi create da terzi,
 * ossia le classi che non sono state create da noi e che non presentano l'annotation @Component.
 * Oppure per impostare un BEAN già configurato
 */
@Configuration
@EnableAsync
@OpenAPIDefinition
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer")
public class EnergyConfiguration {

	@Autowired
	IndirizzoLegaleRepository indiLegaleRepo;
	@Autowired
	IndirizzoOperativoRepository indiOperativoRepo;
	@Autowired
	ClienteRepository clienteRepository;
	
	@Bean(name = "clienteDefault")
	@Lazy
	public ClientDomain clienteDefault() {
		ClientDomain clientDomain = new ClientDomain();
		clientDomain.setNomeContatto("Mario");
		clientDomain.setCognomeContatto("Rossi");
		clientDomain.setAnni(33);
		clientDomain.setTipologia(Tipologia.PA);
		clientDomain.setEmail("azienda@email.com");
		clientDomain.setRagioneSociale("Label");
		clientDomain.setPartitaIva("12345678901");
		clientDomain.setDataDiNascita(LocalDate.of(1989, 04, 9));
		clientDomain.setDataInserimento(LocalDate.now());
		clientDomain.setDataUltimoContatto(LocalDate.now());
		clientDomain.setFatturatoAnnuale(BigDecimal.valueOf(1000));
		clientDomain.setPec("utente@pec.com");
		clientDomain.setTelefono("32711223344");
		clientDomain.setTelefonoContatto("32711223344");
		clientDomain.setEmailContatto("utente@email.com");
		clientDomain.setIndirizzoLegale(indiLegaleRepo.findById(3l).get());
		clientDomain.setIndirizzoOperativo(indiOperativoRepo.findById(2l).get());
		return clientDomain;
	}

	@Bean
	@Lazy
	public Fattura fatturaDefault(){
		Fattura fattura = new Fattura();
		fattura.setAnno(LocalDate.now().getYear());
		fattura.setData(LocalDate.now());
		fattura.setImporto(3000d);
		fattura.setNumero(1);
		fattura.setPercentualeIVA(22d);
		fattura.setImportoIVA(fattura.getImporto()*fattura.getPercentualeIVA()/100);
		fattura.setPercentualeSconto(0d);
		fattura.setImportoSconto(fattura.getImporto()*fattura.getPercentualeSconto()/100);
		fattura.setStato(StatoFattura.PAGATA);
//		fattura.setCliente(clienteDefault());
		fattura.setClientDomain(clienteRepository.findById(5l).get());

		return fattura;
	}

}
