package it.progetto.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.persistence.entity.StatoFattura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatoDTO {
	
	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	@Enumerated(EnumType.STRING)
	private StatoFattura stato;

}
