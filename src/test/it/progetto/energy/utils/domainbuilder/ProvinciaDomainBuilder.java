package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.ProvinciaDomain;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.REGION;
import static it.progetto.energy.utils.ConstantForTest.SIGLA_PROVINCIA;

public class ProvinciaDomainBuilder {

    public static ProvinciaDomain buildProvinciaDomainInput(Long entityId){
        return ProvinciaDomain.builder()
                .id(entityId)
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneList(List.of(ComuneDomain.builder().id(ENTITY_ID).build()))
                .build();
    }

    public static ProvinciaDomain buildProvinciaDomainOutput(Long entityId){
        return ProvinciaDomain.builder()
                .id(entityId)
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneList(List.of(ComuneDomain.builder().id(ENTITY_ID).build()))
                .build();
    }

}
