package it.progetto.energy.csv;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProvinciaCSV {
	
	@JsonProperty("Sigla")
	private String sigla;
	@JsonProperty("Provincia")
	private String nome;
	@JsonProperty("Regione")
	private String regione;

}
