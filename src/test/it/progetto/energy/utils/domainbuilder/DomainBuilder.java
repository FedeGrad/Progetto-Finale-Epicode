package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;

import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class DomainBuilder {

    public static AddressDomain buildAddressDomain(Long entityId){
        return AddressDomain.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comune(null)
                .customerList(null)
                .build();
    }

    public static ComuneDomain buildComuneDomain(Long entityId){
        return ComuneDomain.builder()
                .id(entityId)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provincia(null)
                .addressList(null)
                .build();
    }

}
