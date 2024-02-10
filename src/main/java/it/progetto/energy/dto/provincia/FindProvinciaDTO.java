package it.progetto.energy.dto.provincia;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindProvinciaDTO {
	
	@Schema(example = "Verona", type = "string")
	private String provincia;

}
