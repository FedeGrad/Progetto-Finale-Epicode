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
public class InvoiceUpdateDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";

	@NotNull
	private Long invoiceId;

	@Schema(hidden = true)
	private Integer year;

	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate date;

	private Double amount;

	private Integer number;

	private Double amountIVA;

	@Percentage
	private Double percentageIVA;

	private Double amountDiscount;

	@Percentage
	private Double percentageDiscount;

	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	@Enumerated(EnumType.STRING)
	private StatoFattura state;

	private Long customerId;

}
