package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.ProvinciaDomain;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;

public class ComuneDomainBuilder {

    public static ComuneDomain buildComuneDomainInput(Long entityId){
        return ComuneDomain.builder()
                .id(entityId)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provincia(ProvinciaDomain.builder().id(ENTITY_ID).build())
                .addressList(null)
                .build();
    }

    public static ComuneDomain buildComuneDomainOutput(Long entityId){
        return ComuneDomain.builder()
                .id(entityId)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provincia(ProvinciaDomain.builder().id(ENTITY_ID).build())
                .addressList(List.of(AddressDomain.builder().id(ENTITY_ID).build()))
                .build();
    }

}
