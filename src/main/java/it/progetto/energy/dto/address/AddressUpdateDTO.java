package it.progetto.energy.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateDTO {

	@NotNull
	private Long id;

	@Schema(example = "way", type = "string")
	private String way;

	@Schema(example = "civic number", type = "string")
	private String number;

	@Schema(example = "postal code", type = "string")
	private String postalCode;

	@Schema(example = "location", type = "string")
	private String comuneId;

}
