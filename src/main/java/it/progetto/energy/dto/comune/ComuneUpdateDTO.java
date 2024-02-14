package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComuneUpdateDTO {

	@NotNull
	private Long id;

	private String name;

	private String postalCode;

	private Long provinciaId;

}
