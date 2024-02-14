package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.CustomerDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildAddressDomain;
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
        AddressDTO addressDTO = buildAddressDTO();
        ComuneDomain comuneDomain = ComuneDomain.builder().id(ENTITY_ID).build();
        AddressDomain expected = buildAddressDomain(null);
        expected.setComune(comuneDomain);

        AddressDomain actual = addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressUpdateDTOToAddressDomain() {
        AddressUpdateDTO addressUpdateDTO = buildAddressUpdateDTO();
        ComuneDomain comuneDomain = ComuneDomain.builder().id(ENTITY_ID).build();
        AddressDomain expected = buildAddressDomain(1L);
        expected.setComune(comuneDomain);

        AddressDomain actual = addressDTOMapper.fromAddressUpdateDTOToAddressDomain(addressUpdateDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainToAddressOutputDTO() {
        ComuneDomain comuneDomain = ComuneDomain.builder().id(ENTITY_ID).build();
        CustomerDomain customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        AddressDomain addressDomain = buildAddressDomain(ENTITY_ID);
        addressDomain.setComune(comuneDomain);
        addressDomain.setCustomerList(List.of(customerDomain));
        AddressOutputDTO expected = buildAddressOutputDTO();

        AddressOutputDTO actual = addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainListToAddressOutputDTOList() {
        ComuneDomain comuneDomain = ComuneDomain.builder().id(ENTITY_ID).build();
        CustomerDomain customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        AddressDomain addressDomain = buildAddressDomain(ENTITY_ID);
        addressDomain.setComune(comuneDomain);
        addressDomain.setCustomerList(List.of(customerDomain));
        List<AddressDomain> addressDomainList = List.of(addressDomain);
        List<AddressOutputDTO> expectedList = List.of(buildAddressOutputDTO());

        List<AddressOutputDTO> actualList = addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList);

        assertEquals(expectedList, actualList);
    }

}