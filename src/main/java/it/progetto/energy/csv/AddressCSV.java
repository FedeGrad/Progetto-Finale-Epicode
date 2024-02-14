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
public class AddressCSV {

	@JsonProperty("VIA")
	private String way;

	@JsonProperty("CIVICO")
	private String number;

	@JsonProperty("CITTA")
	private String location;

	@JsonProperty("CAP")
	private String postalCode;
	
}
