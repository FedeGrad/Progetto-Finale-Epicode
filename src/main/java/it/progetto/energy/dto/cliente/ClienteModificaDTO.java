package it.progetto.energy.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ClienteModificaDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	private Long idCliente;
	@NotBlank
	@Schema(example = "nome azienda", type = "string")
	private String ragioneSociale;
//	@Schema(hidden = false)
	@NotBlank
	@Schema(example = " ", type = "string")
	private String partitaIva;
	@Schema(example = "example@example.it", type = "string")
	private String email;
//	@JsonFormat(pattern = DATE_TIME_PATTERN)
	@Schema(hidden = true)
	private LocalDate dataInserimento;
//	@Schema(example = "20/01/2000", type = "string")
//	@JsonFormat(pattern = DATE_PATTERN)
	@Schema(hidden = true)
	private LocalDate dataUltimoContatto = LocalDate.now();
	private BigDecimal fatturatoAnnuale;
	@Schema(example = "PA / SAS / SPA / SRL", type = "string")
	private String tipologia;
	@Schema(example = "example@pec.it", type = "string")
	private String pec;
	@Schema(example = "0000000000", type = "string")
	private String telefono;
	@Schema(example = "example@example.it", type = "string")
	private String emailContatto;
	@NotBlank
	@Schema(example = " ", type = "string")
	private String nomeContatto;
	@NotBlank
	@Schema(example = " ", type = "string")
	private String cognomeContatto;
	@Schema(example = "0000000000", type = "string")
	private String telefonoContatto;
	private Long iDindirizzoOperativo;
	private Long iDindirizzoLegale;

}
