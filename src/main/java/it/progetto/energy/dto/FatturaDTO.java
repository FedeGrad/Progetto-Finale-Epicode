package it.progetto.energy.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FatturaDTO {

	static final String DATE_PATTERN = "dd/MM/yyyy";

	@Schema(hidden = true)
	private Integer anno;
	@Schema(example = "20/01/2022", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data;
	private Double importo;
	private Integer numero;
	@Schema(example = "PAGATA / NON PAGATA / ANNULLATA / SCADUTA / DA RIMBORSARE / RIMBORSATA", type = "string")
	private String stato;
	private Long idCliente;
	private Double importoIVA;
	@Percentage
	private Double percentualeIVA;
	private Double importoSconto;
	@Percentage
	private Double percentualeSconto;

}
