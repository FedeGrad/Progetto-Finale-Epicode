package it.progetto.energy.dto;

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
public class ComuneModificaDTO {
	
	private Long idComune;
	private String nome;
	private String cap;
	private String siglaProvincia;

}
