package it.progetto.energy.dto.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceAddPDFDTO {

	@Schema(example = "1", type = "long", description = "inserisci l'id Fattura", name = "Id fattura")
	private Long idFattura;

	@NotNull @Schema(description = "carica il file", name = "Fattura")
	private MultipartFile fileFattura;

}
