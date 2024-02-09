package it.progetto.energy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FatturaModificaDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";
	
	private Long idFattura;
	private Integer anno;
	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data;
	private Double importo;
	private Integer numero;
	private String stato;
	private Long idCliente;

}
