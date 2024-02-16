package it.progetto.energy.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

	@NotNull
	@Schema(example = "location", type = "string")
	private Long comuneId;
	
}
