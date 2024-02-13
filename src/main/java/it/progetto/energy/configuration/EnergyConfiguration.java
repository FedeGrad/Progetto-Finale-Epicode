package it.progetto.energy.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.model.Tipologia;
import it.progetto.energy.persistence.entity.CustomerEntity;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.CustomerRepository;
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

	private final AddressRepository addressRepository;
	private final CustomerRepository customerRepository;

	@Lazy
    @Bean(name = "clienteDefault")
	public CustomerEntity clienteDefault() {
		CustomerEntity customerEntityDomain = new CustomerEntity();
		customerEntityDomain.setName("Mario");
		customerEntityDomain.setSurname("Rossi");
		customerEntityDomain.setAge(33);
		customerEntityDomain.setType(Tipologia.PA);
		customerEntityDomain.setEmail("azienda@email.com");
		customerEntityDomain.setCompanyName("Label");
		customerEntityDomain.setNpi("12345678901");
		customerEntityDomain.setDateOfBirth(LocalDate.of(1989, 4, 9));
		customerEntityDomain.setDataCreate(LocalDate.now());
		customerEntityDomain.setDataLastUpdate(LocalDate.now());
		customerEntityDomain.setAnnualTurnover(BigDecimal.valueOf(1000));
		customerEntityDomain.setPec("utente@pec.com");
		customerEntityDomain.setCompanyPhone("32711223344");
		customerEntityDomain.setCustomerPhone("32711223344");
		customerEntityDomain.setCustomerEmail("utente@email.com");
		customerEntityDomain.setAddress(addressRepository.findById(3L).orElse(null));

		return customerEntityDomain;
	}

	@Lazy
	@Bean(name = "fatturaDefault")
	public InvoiceEntity fatturaDefault(){
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setYear(String.valueOf(LocalDate.now().getYear()));
		invoiceEntity.setDate(LocalDate.now());
		invoiceEntity.setAmount(3000d);
		invoiceEntity.setNumber(1);
		invoiceEntity.setPercentageIVA(22d);
		invoiceEntity.setAmountIVA(invoiceEntity.getAmount()* invoiceEntity.getPercentageIVA()/100);
		invoiceEntity.setPercentageDiscount(0d);
		invoiceEntity.setAmountDiscount(invoiceEntity.getAmount()* invoiceEntity.getPercentageDiscount()/100);
		invoiceEntity.setState(StatoFattura.PAGATA);
		invoiceEntity.setCustomer(customerRepository.findById(5L).orElse(null));

		return invoiceEntity;
	}

}
