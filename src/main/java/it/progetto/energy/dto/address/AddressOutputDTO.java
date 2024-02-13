package it.progetto.energy.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutputDTO {
	
	private Long id;

	private String way;

	private String number;

	private String postalCode;

	private String comuneId;

}
