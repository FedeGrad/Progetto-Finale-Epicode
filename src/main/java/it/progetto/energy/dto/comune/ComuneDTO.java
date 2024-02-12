package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDTO {
	
	private String name;

	private String postalCode;

	private String provinciaId;

}
