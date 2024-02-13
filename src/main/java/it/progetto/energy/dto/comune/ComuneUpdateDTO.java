package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneUpdateDTO {

	@NotNull
	private Long id;

	private String name;

	private String postalCode;

	private String provinciaId;

}
