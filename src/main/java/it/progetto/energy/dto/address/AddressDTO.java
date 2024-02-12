package it.progetto.energy.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	
	@NotBlank
	@Schema(example = "way", type = "string")
	private String way;

	@NotBlank
	@Schema(example = "civic number", type = "string")
	private String number;

	@NotBlank
	@Schema(example = "postal code", type = "string")
	private String postalCode;

	@NotBlank
	@Schema(example = "location", type = "string")
	private String comuneId;
	
}
