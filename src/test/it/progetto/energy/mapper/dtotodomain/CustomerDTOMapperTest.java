package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.customer.CustomerOutputDTO;
import it.progetto.energy.model.CustomerDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.CustomerDomainBuilder.buildCustomerDomainInput;
import static it.progetto.energy.utils.domainbuilder.CustomerDomainBuilder.buildCustomerDomainOutput;
import static it.progetto.energy.utils.dtobuilder.CustomerDTOBuilder.buildCustomerDTO;
import static it.progetto.energy.utils.dtobuilder.CustomerDTOBuilder.buildCustomerOutputDTO;
import static it.progetto.energy.utils.dtobuilder.CustomerDTOBuilder.buildCustomerUpdateDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerDTOMapperTest {

    @Autowired
    CustomerDTOMapper customerDTOMapper;

    @Test
    void fromCustomerDomainToCustomerOutputDTO() {
        var customerDomain = buildCustomerDomainOutput(ENTITY_ID);
        var expected = buildCustomerOutputDTO();

        CustomerOutputDTO actual = customerDTOMapper.fromCustomerDomainToCustomerOutputDTO(customerDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromCustomerDomainListToCustomerOutputDTOList() {
        var customerDomain = buildCustomerDomainOutput(ENTITY_ID);
        var expected = List.of(buildCustomerOutputDTO());

        List<CustomerOutputDTO> actual = customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(List.of(customerDomain));

        assertEquals(expected, actual);
    }

    @Test
    void fromCustomerDTOToCustomerDomain() {
        var customerDTO = buildCustomerDTO();
        var expected = buildCustomerDomainInput(null);

        CustomerDomain actual = customerDTOMapper.fromCustomerDTOToCustomerDomain(customerDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromCustomerUpdateDTOToCustomerDomain() {
        var customerUpdateDTO = buildCustomerUpdateDTO();
        var expected = buildCustomerDomainInput(ENTITY_ID);

        CustomerDomain actual = customerDTOMapper.fromCustomerUpdateDTOToCustomerDomain(customerUpdateDTO);

        assertEquals(expected, actual);
    }

}