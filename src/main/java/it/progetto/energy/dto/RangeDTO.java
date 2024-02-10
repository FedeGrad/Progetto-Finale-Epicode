package it.progetto.energy.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {

	@NotNull
	private Double importoMin;

	@NotNull
	private Double importoMax;

}
