package it.progetto.energy.dto.invoice;

import it.progetto.energy.model.StatoFattura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutputDTO {

	private Long id;

	private String year;

	private LocalDate date;

	private Double amount;

	private Integer number;

	private Double amountIVA;

	private Double amountDiscount;

	private StatoFattura state;

	private Long customerId;


}
