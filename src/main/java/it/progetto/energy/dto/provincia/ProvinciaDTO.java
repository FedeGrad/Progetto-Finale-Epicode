package it.progetto.energy.dto.provincia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinciaDTO {

	@NotNull
	private String sigla;

	@NotNull
	private String name;

	@NotNull
	private String region;

	private List<Long> comuneIdList;

}
