package it.progetto.energy.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressOutputDTO {
	
	private Long id;

	private String way;

	private String number;

	private String postalCode;

	private Long comuneId;

	private List<Long> customerIdList;

}
