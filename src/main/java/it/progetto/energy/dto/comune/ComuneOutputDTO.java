package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComuneOutputDTO {

    private Long idComune;

    private String nome;

    private String cap;

    private String siglaProvincia;

}
