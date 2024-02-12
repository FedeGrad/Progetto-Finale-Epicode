package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;
import it.progetto.energy.model.AddressDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "comune.id", source = "comuneId")
    AddressDomain fromAddressDTOToAddressDomain(AddressDTO addressDTO);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "comune.id", source = "comuneId")
    AddressDomain fromAddressUpdateDTOToAddressDomain(AddressUpdateDTO addressUpdateDTO);

    @Mapping(target = "comuneId", source = "comune.id")
    AddressOutputDTO fromAddressDomainToAddressOutputDTO(AddressDomain addressDomain);

    List<AddressOutputDTO> fromAddressDomainListToAddressOutputDTOList(List<AddressDomain> addressDomainList);

}
