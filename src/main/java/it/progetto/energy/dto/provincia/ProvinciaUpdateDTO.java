package it.progetto.energy.dto.provincia;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinciaUpdateDTO {

	@NotNull
	private Long idProvincia;

	private String sigla;

	private String nome;

	private String regione;

	private Long idComune;

}
