package it.progetto.energy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {
	
	static final String DATE_PATTERN = "dd/MM/yyyy";

	@NotNull
	@Schema(example = "20/01/2000", type = "string")
	@JsonFormat(pattern = DATE_PATTERN)
	private LocalDate data;

}
