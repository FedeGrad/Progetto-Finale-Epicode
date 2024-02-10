package it.progetto.energy.dto.fattura;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FatturaPDFDTO {

	@Schema(example = "1", type = "int", description = "inserisci l'id Fattura", name = "Id fattura")
	private Long idFattura;
	//	private Long idCliente;
	@NotNull @Schema(description = "carica il file", name = "Fattura")
	private MultipartFile fileFattura;

}
