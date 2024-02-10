package it.progetto.energy.dto.indirizzo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndirizzoDTO {
	
	@NotBlank
	@Schema(example = "way", type = "string")
	private String via;

	@NotBlank
	@Schema(example = "civic number", type = "string")
	private String civico;

	@NotBlank
	@Schema(example = "location", type = "string")
	private String localita;

	@NotBlank
	@Schema(example = "postal code", type = "string")
	private String cap;
	
}
