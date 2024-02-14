package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.address.AddressDTO;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class AddressDTOBuilder {

    public static AddressDTO buildAddressDTO(){
        return AddressDTO.builder()
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comuneId(ENTITY_ID)
                .build();
    }

}
