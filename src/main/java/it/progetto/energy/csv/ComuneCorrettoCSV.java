package it.progetto.energy.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComuneCorrettoCSV {

	@JsonProperty("Comune")
	private String nome;

	@JsonProperty("Provincia")
	private String siglaProvincia;

	@JsonProperty("CAP")
	private String cap;

}
