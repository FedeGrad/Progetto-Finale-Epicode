package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;

import java.util.List;

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

    public static AddressUpdateDTO buildAddressUpdateDTO(){
        return AddressUpdateDTO.builder()
                .id(ENTITY_ID)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comuneId(ENTITY_ID)
                .build();
    }

    public static AddressOutputDTO buildAddressOutputDTO(){
        return AddressOutputDTO.builder()
                .id(ENTITY_ID)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comuneId(ENTITY_ID)
                .customerIdList(List.of(ENTITY_ID))
                .build();
    }

}
