package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildAddressDomain;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressDTOMapperTest {

    @Autowired
    AddressDTOMapper addressDTOMapper;

    @Test
    void fromAddressDTOToAddressDomain() {
        AddressDTO addressDTO = buildAddressDTO();
        ComuneDomain comuneDomain = ComuneDomain.builder().id(ENTITY_ID).build();
        AddressDomain expected = buildAddressDomain(null, comuneDomain, null);

        AddressDomain actual = addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressUpdateDTOToAddressDomain() {
    }

    @Test
    void fromAddressDomainToAddressOutputDTO() {
    }

    @Test
    void fromAddressDomainListToAddressOutputDTOList() {
    }
}