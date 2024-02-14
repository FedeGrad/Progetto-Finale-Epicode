package it.progetto.energy.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDeprecatedCSV {

	@JsonProperty("Codice Provincia")
	private String codiceProvincia;

	@JsonProperty("Progressivo del Comune")
	private String codiceComune;

	@JsonProperty("Denominazione in italiano")
	private String nome;

	@JsonProperty("Provincia")
	private String provincia;

}
