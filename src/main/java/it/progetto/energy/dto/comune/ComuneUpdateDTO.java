package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneUpdateDTO {
	
	private Long idComune;

	private String nome;

	private String cap;

	private String siglaProvincia;

}
