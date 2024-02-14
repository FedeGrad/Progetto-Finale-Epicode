package it.progetto.energy.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.StatoFattura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static it.progetto.energy.utils.ConstantUtils.DATE_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDTO {

	@NotNull
	@Schema(example = "20/01/2022", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate date;

	@NotNull
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

	@NotNull
	private Long customerId;

	@Schema(hidden = true)
	private String year;

}
