package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.model.AddressDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainInput;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainOutput;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressDTO;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressOutputDTO;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressUpdateDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressDTOMapperTest {

    @Autowired
    AddressDTOMapper addressDTOMapper;

    @Test
    void fromAddressDTOToAddressDomain() {
        var addressDTO = buildAddressDTO();
        var expected = buildAddressDomainInput(null);

        AddressDomain actual = addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressUpdateDTOToAddressDomain() {
        var addressUpdateDTO = buildAddressUpdateDTO();
        var expected = buildAddressDomainInput(1L);

        AddressDomain actual = addressDTOMapper.fromAddressUpdateDTOToAddressDomain(addressUpdateDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainToAddressOutputDTO() {
        var addressDomain = buildAddressDomainOutput(ENTITY_ID);
        var expected = buildAddressOutputDTO();

        AddressOutputDTO actual = addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainListToAddressOutputDTOList() {
        var addressDomain = buildAddressDomainOutput(ENTITY_ID);
        var addressDomainList = List.of(addressDomain);
        var expectedList = List.of(buildAddressOutputDTO());

        List<AddressOutputDTO> actualList = addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList);

        assertEquals(expectedList, actualList);
    }

}