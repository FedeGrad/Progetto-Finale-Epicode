package it.progetto.energy.dto;

import com.sun.istack.NotNull;
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
public class ProvinciaModificaDTO {

	@NotNull
	private Long idProvincia;
	private String sigla;
	private String nome;
	private String regione;
//	private Long idComune;

}
