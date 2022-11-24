package it.progetto.energy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Percentage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FatturaPDFDTO {

	@Schema(example = "1", type = "int", description = "inserisci l'id Fattura", name = "Id fattura")
	private Long idFattura;
	//	private Long idCliente;
	@NotNull
	@Schema(description = "carica il file", name = "Fattura                           ")
	private MultipartFile fileFattura;

}
