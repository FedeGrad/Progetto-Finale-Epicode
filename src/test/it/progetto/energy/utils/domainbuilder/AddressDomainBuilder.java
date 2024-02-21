package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.CustomerDomain;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class AddressDomainBuilder {

    public static AddressDomain buildAddressDomainInput(Long entityId){
        return AddressDomain.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comune(ComuneDomain.builder().id(ENTITY_ID).build())
                .customerList(null)
                .build();
    }

    public static AddressDomain buildAddressDomainOutput(Long entityId){
        return AddressDomain.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comune(ComuneDomain.builder().id(ENTITY_ID).build())
                .customerList(List.of(CustomerDomain.builder().id(ENTITY_ID).build()))
                .build();
    }


}
