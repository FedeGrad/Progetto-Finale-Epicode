package it.progetto.energy.dto.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import it.progetto.energy.persistence.entity.Tipologia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	@NotBlank @Schema(example = "company name", type = "string")
	private String ragioneSociale;

	@NotBlank @Schema(example = "000990000099", type = "string")
	private String partitaIva;

	@Schema(example = "example@example.it", type = "string")
	private String email;

	@Schema(hidden = true) @JsonFormat(pattern = DATE_TIME_PATTERN)
	private LocalDate dataInserimento = LocalDate.now();

	@Schema(hidden = true)
	@JsonFormat(pattern = DATE_PATTERN)
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

	@NotBlank @Schema(example = "name", type = "string")
	private String nomeContatto;

	@NotBlank @Schema(example = "surname", type = "string")
	private String cognomeContatto;

	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dataDiNascita;

	@Schema(example = "3279999999", type = "string")
	private String telefonoContatto;

	private Long idIndirizzoOperativo;

	private Long idIndirizzoLegale;
	
}
