package it.progetto.energy.dto.fattura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FatturaOutputDTO {
	
	private Long idFattura;
	private Integer anno;
	private LocalDate data;
	private Double importo;
	private Integer numero;
	private String stato;
	private Long idCliente;

}
