package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDTO {
	
	private String nome;
	private String cap;
	private String siglaProvincia;

}
