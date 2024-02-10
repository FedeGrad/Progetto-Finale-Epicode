package it.progetto.energy.dto.provincia;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RicercaProvinciaDTO {
	
	@Schema(example = "Verona", type = "string")
	private String provincia;

}
