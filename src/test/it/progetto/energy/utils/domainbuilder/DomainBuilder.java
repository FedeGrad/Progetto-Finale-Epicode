package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.CustomerDomain;

import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class DomainBuilder {

    public static AddressDomain buildAddressDomain(Long entityId, ComuneDomain comune, CustomerDomain customer){
        return AddressDomain.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comune(comune)
                .customer(customer)
                .build();
    }

}
