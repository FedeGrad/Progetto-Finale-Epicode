package it.progetto.energy.dto.indirizzo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndirizzoOutputDTO {
	
	private Long idIndirizzo;

	private String via;

	private String civico;

	private String localita;

	private String cap;

}
