package it.progetto.energy.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinciaCSV {
	
	@JsonProperty("Sigla")
	private String siglaProvincia;

	@JsonProperty("Provincia")
	private String name;

	@JsonProperty("Regione")
	private String region;

}
