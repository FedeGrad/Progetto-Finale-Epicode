package it.progetto.energy.csv;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.progetto.energy.model.Provincia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IndirizziCSV {

	@JsonProperty("VIA")
	private String via;
	@JsonProperty("CIVICO")
	private String civico;
	@JsonProperty("CITTA")
	private String localita;
	@JsonProperty("CAP")
	private String cap;
	

	
	
	
}
