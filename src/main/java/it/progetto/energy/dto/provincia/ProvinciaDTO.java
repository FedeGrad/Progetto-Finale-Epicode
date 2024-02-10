package it.progetto.energy.dto.provincia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinciaDTO {
	
	private String sigla;

	private String nome;

	private String regione;

	private Long idComune;

}
