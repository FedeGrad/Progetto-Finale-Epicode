package it.progetto.energy.dto;

import javax.validation.constraints.NotBlank;

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
public class IndirizzoModificaDTO {
	
	private Long idIndirizzo;
	@NotBlank @Schema(example = " ", type = "string")
	private String via;
	private String civico;
	@NotBlank @Schema(example = " ", type = "string")
	private String localita;
	private String cap;

}
