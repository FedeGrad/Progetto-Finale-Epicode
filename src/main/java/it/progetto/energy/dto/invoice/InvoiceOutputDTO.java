package it.progetto.energy.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutputDTO {
	
	private Long idFattura;

	private Integer anno;

	private LocalDate data;

	private Double importo;

	private Integer numero;

	private String stato;

	private Long idCliente;

}
