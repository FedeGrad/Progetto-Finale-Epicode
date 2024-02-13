package it.progetto.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.StatoFattura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatoDTO {
	
	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	@Enumerated(EnumType.STRING)
	private StatoFattura stato;

}
