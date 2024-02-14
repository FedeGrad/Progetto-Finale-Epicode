package it.progetto.energy.controller;

import it.progetto.energy.controller.base.ControllerBaseTest;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.dtotodomain.AddressDTOMapper;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildAddressDomain;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressOutputDTO;
import static it.progetto.energy.utils.dtobuilder.UtilsDtoBuilder.buildPageDTO;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AddressController.class)
class AddressControllerTest extends ControllerBaseTest {

    private final String PATH_ADDRESS = "/address";

    @MockBean
    AddressServiceImpl addressServiceImpl;
    @MockBean
    AddressDTOMapper addressDTOMapper;
    @Autowired
    UtilsMapper utilsMapper;

    @Test
    void findAllAddress() throws Exception {
        List<AddressDomain> addressDomainList = List.of(buildAddressDomain(ENTITY_ID));
        List<AddressOutputDTO> expected = List.of(buildAddressOutputDTO());

        when(addressServiceImpl.findAllIndirizziLegali())
                .thenReturn(addressDomainList);
        when(addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList))
                .thenReturn(expected);

        mockMvc.perform(get(PATH_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(expected)))
                .andExpect(status().isOk());

        verify(addressServiceImpl, times(1)).findAllIndirizziLegali();
    }

    @Test
    void findAllAddressPaged() throws Exception {
        PageDTO pageDTO = buildPageDTO();
        List<AddressDomain> addressDomainList = List.of(buildAddressDomain(ENTITY_ID));
        List<AddressOutputDTO> expected = List.of(buildAddressOutputDTO());

        PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
        when(addressServiceImpl.findAllAddressPaged(pageDomain))
                .thenReturn(addressDomainList);
        when(addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList))
                .thenReturn(expected);

        mockMvc.perform(get(PATH_ADDRESS + "/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(pageDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(expected)))
                .andExpect(status().isOk());

        verify(addressServiceImpl, times(1)).findAllAddressPaged(pageDomain);
    }

    @Test
    void createMainAddress() {
    }

    @Test
    void updateMainAddress() {
    }

    @Test
    void deleteMainAddress() {
    }

}