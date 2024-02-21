package it.progetto.energy.controller;

import it.progetto.energy.controller.base.ControllerBaseTest;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotDeletableException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.dtotodomain.AddressDTOMapper;
import it.progetto.energy.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ADDRESS_NOT_FOUND;
import static it.progetto.energy.exception.model.ErrorCodeDomain.COMUNE_NOT_FOUND;
import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainInput;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainOutput;
import static it.progetto.energy.utils.domainbuilder.UtilsDomainBuilder.buildPageDomain;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressDTO;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressOutputDTO;
import static it.progetto.energy.utils.dtobuilder.AddressDTOBuilder.buildAddressUpdateDTO;
import static it.progetto.energy.utils.dtobuilder.UtilsDtoBuilder.buildPageDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddressControllerTest extends ControllerBaseTest {
    private final String PATH_ADDRESS = "/address";

    @MockBean
    AddressServiceImpl addressServiceImpl;
    @MockBean
    AddressDTOMapper addressDTOMapper;
    @MockBean
    UtilsMapper utilsMapper;

    @Test
    void findAllAddress() throws Exception {
        var addressDomainList = List.of(buildAddressDomainOutput(ENTITY_ID));
        var expected = List.of(buildAddressOutputDTO());

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
        final String PATH_ADDRESS_PAGE = PATH_ADDRESS.concat("/page");
        var pageDTO = buildPageDTO();
        var pageDomain = buildPageDomain();
        var addressDomainList = List.of(buildAddressDomainOutput(ENTITY_ID));
        var expected = List.of(buildAddressOutputDTO());

        when(utilsMapper.fromPageDTOToPageDomain(pageDTO))
                .thenReturn(pageDomain);
        when(addressServiceImpl.findAllAddressPaged(pageDomain))
                .thenReturn(addressDomainList);
        when(addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList))
                .thenReturn(expected);

        mockMvc.perform(get(PATH_ADDRESS_PAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(pageDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(expected)))
                .andExpect(status().isOk());

        verify(addressServiceImpl, times(1)).findAllAddressPaged(pageDomain);
    }

    @Test
    void createAddress() throws Exception {
        var addressDTO = buildAddressDTO();
        var addressDomain = buildAddressDomainInput(null);
        var addressDomainReturn = buildAddressDomainOutput(ENTITY_ID);
        var expected = buildAddressOutputDTO();

        when(addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO))
                .thenReturn(addressDomain);
        when(addressServiceImpl.createIndirizzo(addressDomain))
                .thenReturn(addressDomainReturn);
        when(addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressDomainReturn))
                .thenReturn(expected);

        mockMvc.perform(post(PATH_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(addressDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(expected)))
                .andExpect(status().isCreated());

        verify(addressDTOMapper, times(1)).fromAddressDTOToAddressDomain(addressDTO);
        verify(addressServiceImpl, times(1)).createIndirizzo(addressDomain);
        verify(addressDTOMapper, times(1)).fromAddressDomainToAddressOutputDTO(addressDomainReturn);
    }

    @Test
    void createAddressWhenNotFoundComune() throws Exception {
        var addressDTO = buildAddressDTO();
        var addressDomain = buildAddressDomainInput(null);

        when(addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO))
                .thenReturn(addressDomain);
        when(addressServiceImpl.createIndirizzo(addressDomain))
                .thenThrow(new NotCreatableException(COMUNE_NOT_FOUND));

        mockMvc.perform(post(PATH_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(addressDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(addressDTOMapper, times(1)).fromAddressDTOToAddressDomain(addressDTO);
        verify(addressServiceImpl, times(1)).createIndirizzo(addressDomain);
        verify(addressDTOMapper, never()).fromAddressDomainToAddressOutputDTO(any());
    }

    @Test
    void updateAddress() throws Exception {
        var addressUpdateDTO = buildAddressUpdateDTO();
        var addressDomain = buildAddressDomainInput(ENTITY_ID);
        var expected = buildAddressOutputDTO();

        when(addressDTOMapper.fromAddressUpdateDTOToAddressDomain(addressUpdateDTO))
                .thenReturn(addressDomain);
        when(addressServiceImpl.updateAddress(addressDomain))
                .thenReturn(addressDomain);
        when(addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressDomain))
                .thenReturn(expected);

        mockMvc.perform(put(PATH_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(addressUpdateDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(convertToJson(expected)))
                .andExpect(status().isOk());

        verify(addressDTOMapper, times(1)).fromAddressUpdateDTOToAddressDomain(addressUpdateDTO);
        verify(addressServiceImpl, times(1)).updateAddress(addressDomain);
        verify(addressDTOMapper, times(1)).fromAddressDomainToAddressOutputDTO(addressDomain);
    }

    @Test
    void updateAddressWhenAddressNotFound() throws Exception {
        var addressUpdateDTO = buildAddressUpdateDTO();
        var addressDomain = buildAddressDomainInput(ENTITY_ID);

        when(addressDTOMapper.fromAddressUpdateDTOToAddressDomain(addressUpdateDTO))
                .thenReturn(addressDomain);
        when(addressServiceImpl.updateAddress(addressDomain))
                .thenThrow(new NotUpdatableException(ADDRESS_NOT_FOUND));

        mockMvc.perform(put(PATH_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(addressUpdateDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(addressDTOMapper, times(1)).fromAddressUpdateDTOToAddressDomain(addressUpdateDTO);
        verify(addressServiceImpl, times(1)).updateAddress(addressDomain);
        verify(addressDTOMapper, never()).fromAddressDomainToAddressOutputDTO(any());
    }

    @Test
    void deleteAddress() throws Exception {
        final String PATH_ADDRESS_DELETE = PATH_ADDRESS + "/{addressId}";

        doNothing().when(addressServiceImpl).deleteAddress(ENTITY_ID);

        mockMvc.perform(delete(PATH_ADDRESS_DELETE, ENTITY_ID))
                .andExpect(status().isNoContent());

        verify(addressServiceImpl, times(1)).deleteAddress(ENTITY_ID);
    }

    @Test
    void deleteAddressWhenAddressNotFound() throws Exception {
        final String PATH_ADDRESS_DELETE = PATH_ADDRESS + "/{addressId}";

        doThrow(new NotDeletableException(ADDRESS_NOT_FOUND))
                .when(addressServiceImpl).deleteAddress(ENTITY_ID);

        mockMvc.perform(delete(PATH_ADDRESS_DELETE, ENTITY_ID))
                .andExpect(status().isBadRequest());

        verify(addressServiceImpl, times(1)).deleteAddress(ENTITY_ID);
    }

}