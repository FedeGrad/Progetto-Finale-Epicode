package it.progetto.energy.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.StatoFattura;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

	static final String DATE_PATTERN = "dd/MM/yyyy";

	@Schema(hidden = true)
	private Integer anno;

	@Schema(example = "20/01/2022", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data;

	private Double importo;

	private Integer numero;

	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	@Enumerated(EnumType.STRING)
	private StatoFattura stato;

	@NotNull
	private Long idCliente;

	private Double importoIVA;

	@Percentage
	private Double percentualeIVA;

	private Double importoSconto;

	@Percentage
	private Double percentualeSconto;

}
