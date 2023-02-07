package it.progetto.energy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	@NotBlank @Schema(example = "nome azienda", type = "string")
	private String ragioneSociale;
	@NotBlank @Schema(example = " ", type = "string")
	private String partitaIva;
	@Schema(example = "example@example.it", type = "string")
	private String email;
	@Schema(hidden = true) @JsonFormat(pattern = DATE_TIME_PATTERN)
	private LocalDate dataInserimento = LocalDate.now();
//	@Schema(example = "20/01/2000", type = "string")
	@Schema(hidden = true) @JsonFormat(pattern = DATE_PATTERN)
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
	@NotBlank @Schema(example = " ", type = "string")
	private String nomeContatto;
	@NotBlank @Schema(example = " ", type = "string")
	private String cognomeContatto;
	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate dataDiNascita;
	@Schema(example = "0000000000", type = "string")
	private String telefonoContatto;
	private Long idIndirizzoOperativo;
	private Long idIndirizzoLegale;
	
}
