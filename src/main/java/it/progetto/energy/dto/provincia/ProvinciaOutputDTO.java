package it.progetto.energy.dto.provincia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinciaOutputDTO {

	private Long id;

	private String sigla;

	private String name;

	private String region;

	private List<Long> comuneIdList;

}
