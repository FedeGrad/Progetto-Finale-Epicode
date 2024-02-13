package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDTO {

	@NotBlank
	private String name;

	@NotBlank
	private String postalCode;
	
	@NotBlank
	private String provinciaId;

}
