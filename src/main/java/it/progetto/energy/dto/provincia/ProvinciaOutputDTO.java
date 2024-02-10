package it.progetto.energy.dto.provincia;

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
public class ProvinciaOutputDTO {

	private Long idProvincia;
	private String sigla;
	private String nome;
	private String regione;

}
