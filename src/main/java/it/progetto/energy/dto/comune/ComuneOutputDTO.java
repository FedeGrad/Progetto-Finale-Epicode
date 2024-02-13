package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComuneOutputDTO {

    private Long id;

    private String name;

    private String postalCode;

    private String provinciaId;

}
