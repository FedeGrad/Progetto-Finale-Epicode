package it.progetto.energy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import it.progetto.energy.model.IndirizzoLegale;
import it.progetto.energy.repository.ComuneRepository;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import it.progetto.energy.repository.IndirizzoOperativoRepository;
import it.progetto.energy.service.ComuneService;
import it.progetto.energy.service.IndirizzoLegaleService;
import it.progetto.energy.service.IndirizzoOperativoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@EnableAsync
@OpenAPIDefinition
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer")
public class Config {
	
	@Autowired
	IndirizzoLegaleService indiLegServ;
	@Autowired
	IndirizzoLegaleRepository indiLegRepo;
	@Autowired
	IndirizzoOperativoService indiOpServ;
	@Autowired
	IndirizzoOperativoRepository indiOpRepo;
	@Autowired
	ComuneService comServ;
	@Autowired
	ComuneRepository comRepo;
	
	@Bean
	public IndirizzoLegale legaleUno() {
		IndirizzoLegale uno = new IndirizzoLegale();
		uno.setCap("37132");
		uno.setCivico("32");
		uno.setComune(comRepo.findByNomeAllIgnoreCase("verona"));
		uno.setVia("micheli");
		return uno;
	}

}
