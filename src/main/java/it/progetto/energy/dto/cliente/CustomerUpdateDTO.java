package it.progetto.energy.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.model.Tipologia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

	@NotNull
	private Long idCliente;

	@Schema(example = "Company name", type = "string")
	private String ragioneSociale;

//	@Schema(hidden = false)
	@Schema(example = "NPI", type = "string")
	private String partitaIva;

	@Schema(example = "example@example.it", type = "string")
	private String email;

//	@JsonFormat(pattern = DATE_TIME_PATTERN)
//	@Schema(hidden = true)
//	private LocalDate dataInserimento;

//	@Schema(example = "20/01/2000", type = "string")
//	@JsonFormat(pattern = DATE_PATTERN)
	@Schema(hidden = true)
	private LocalDate dataUltimoContatto = LocalDate.now();

	private BigDecimal fatturatoAnnuale;

	@Schema(example = "PA / SAS / SPA / SRL", type = "string")
	private Tipologia tipologia;

	@Schema(example = "example@pec.it", type = "string")
	private String pec;

	@Schema(example = "3279999999", type = "string")
	private String telefono;

	@Schema(example = "example@example.it", type = "string")
	private String emailContatto;

	@Schema(example = "name", type = "string")
	private String nomeContatto;

	@Schema(example = "surname", type = "string")
	private String cognomeContatto;

	@Schema(example = "3279999999", type = "string")
	private String telefonoContatto;

	private Long idIndirizzoOperativo;

	private Long idIndirizzoLegale;

}
