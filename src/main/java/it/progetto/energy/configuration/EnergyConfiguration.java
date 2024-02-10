package it.progetto.energy.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.Fattura;
import it.progetto.energy.persistence.entity.StatoFattura;
import it.progetto.energy.persistence.entity.Tipologia;
import it.progetto.energy.persistence.repository.ClienteRepository;
import it.progetto.energy.persistence.repository.IndirizzoLegaleRepository;
import it.progetto.energy.persistence.repository.IndirizzoOperativoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;

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
@RequiredArgsConstructor
public class EnergyConfiguration {

	private final IndirizzoLegaleRepository indirizzoLegaleRepository;
	private final IndirizzoOperativoRepository indirizzoOperativoRepository;
	private final ClienteRepository clienteRepository;

	@Lazy
    @Bean(name = "clienteDefault")
	public Cliente clienteDefault() {
		Cliente customerDomain = new Cliente();
		customerDomain.setNomeContatto("Mario");
		customerDomain.setCognomeContatto("Rossi");
		customerDomain.setAnni(33);
		customerDomain.setTipologia(Tipologia.PA);
		customerDomain.setEmail("azienda@email.com");
		customerDomain.setRagioneSociale("Label");
		customerDomain.setPartitaIva("12345678901");
		customerDomain.setDataDiNascita(LocalDate.of(1989, 4, 9));
		customerDomain.setDataInserimento(LocalDate.now());
		customerDomain.setDataUltimoContatto(LocalDate.now());
		customerDomain.setFatturatoAnnuale(BigDecimal.valueOf(1000));
		customerDomain.setPec("utente@pec.com");
		customerDomain.setTelefono("32711223344");
		customerDomain.setTelefonoContatto("32711223344");
		customerDomain.setEmailContatto("utente@email.com");
		customerDomain.setIndirizzoLegale(indirizzoLegaleRepository.findById(3L).orElse(null));
		customerDomain.setIndirizzoOperativo(indirizzoOperativoRepository.findById(2L).orElse(null));
		return customerDomain;
	}

	@Lazy
	@Bean(name = "fatturaDefault")
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
		fattura.setCliente(clienteRepository.findById(5L).orElse(null));

		return fattura;
	}

}
