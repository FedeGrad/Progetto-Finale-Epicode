package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.provincia.ProvinciaDTO;
import it.progetto.energy.dto.provincia.ProvinciaOutputDTO;
import it.progetto.energy.dto.provincia.ProvinciaUpdateDTO;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.REGION;
import static it.progetto.energy.utils.ConstantForTest.SIGLA_PROVINCIA;

public class ProvinciaDTOBuilder {

    public static ProvinciaDTO buildProvinciaDTO(){
        return ProvinciaDTO.builder()
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneIdList(List.of(ENTITY_ID))
                .build();
    }

    public static ProvinciaUpdateDTO buildProvinciaUpdateDTO(){
        return ProvinciaUpdateDTO.builder()
                .id(ENTITY_ID)
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneIdList(List.of(ENTITY_ID))
                .build();
    }

    public static ProvinciaOutputDTO buildProvinciaOutputDTO(){
        return ProvinciaOutputDTO.builder()
                .id(ENTITY_ID)
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneIdList(List.of(ENTITY_ID))
                .build();
    }

}
