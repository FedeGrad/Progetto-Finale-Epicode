package it.progetto.energy.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.StatoFattura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceUpdateDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	
	private Long idFattura;

	private Integer anno;

	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data;

	private Double importo;

	private Integer numero;

	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	@Enumerated(EnumType.STRING)
	private StatoFattura stato;

	private Long idCliente;

}
