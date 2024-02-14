package it.progetto.energy.dto.comune;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComuneDTO {

	@NotBlank
	private String name;

	@NotBlank
	private String postalCode;
	
	@NotBlank
	private Long provinciaId;

}
