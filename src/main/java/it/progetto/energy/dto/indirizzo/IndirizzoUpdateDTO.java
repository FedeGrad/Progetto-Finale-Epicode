package it.progetto.energy.dto.indirizzo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndirizzoUpdateDTO {

	@NotBlank
	private Long idIndirizzo;

	@Schema(example = "way", type = "string")
	private String via;

	@Schema(example = "civic number", type = "string")
	private String civico;

	@Schema(example = "location", type = "string")
	private String localita;

	@Schema(example = "postal code", type = "string")
	private String cap;

}
