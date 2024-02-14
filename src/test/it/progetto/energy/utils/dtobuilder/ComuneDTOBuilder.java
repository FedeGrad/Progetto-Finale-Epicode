package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.comune.ComuneDTO;
import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.dto.comune.ComuneUpdateDTO;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;

public class ComuneDTOBuilder {

    public static ComuneDTO buildComuneDTO(){
        return ComuneDTO.builder()
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provinciaId(ENTITY_ID)
                .build();
    }

    public static ComuneUpdateDTO buildComuneUpdateDTO(){
        return ComuneUpdateDTO.builder()
                .id(ENTITY_ID)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provinciaId(ENTITY_ID)
                .build();
    }

    public static ComuneOutputDTO buildComuneOutputDTO(){
        return ComuneOutputDTO.builder()
                .id(ENTITY_ID)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provinciaId(ENTITY_ID)
                .build();
    }

}
