package it.progetto.energy.configuration;

import it.progetto.energy.model.*;
import it.progetto.energy.repository.ClienteRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.energy.repository.ComuneRepository;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import it.progetto.energy.repository.IndirizzoOperativoRepository;
import it.progetto.energy.service.ComuneService;
import it.progetto.energy.service.IndirizzoLegaleService;
import it.progetto.energy.service.IndirizzoOperativoService;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

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
	public Cliente clienteDefault() {
		Cliente cliente = new Cliente();
		cliente.setNomeContatto("Mario");
		cliente.setCognomeContatto("Rossi");
		cliente.setAnni(33);
		cliente.setTipologia(Tipologia.PA);
		cliente.setEmail("azienda@email.com");
		cliente.setRagioneSociale("Label");
		cliente.setPartitaIva("12345678901");
		cliente.setDataDiNascita(LocalDate.of(1989, 04, 9));
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setFatturatoAnnuale(BigDecimal.valueOf(1000));
		cliente.setPec("utente@pec.com");
		cliente.setTelefono("32711223344");
		cliente.setTelefonoContatto("32711223344");
		cliente.setEmailContatto("utente@email.com");
		cliente.setIndirizzoLegale(indiLegaleRepo.findById(3l).get());
		cliente.setIndirizzoOperativo(indiOperativoRepo.findById(2l).get());
		return cliente;
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
		fattura.setCliente(clienteRepository.findById(5l).get());

		return fattura;
	}

}
