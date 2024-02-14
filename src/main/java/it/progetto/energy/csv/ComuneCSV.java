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
public class ComuneCSV {

	@JsonProperty("Comune")
	private String name;

	@JsonProperty("Provincia")
	private String siglaProvincia;

	@JsonProperty("CAP")
	private String postalCode;

}
