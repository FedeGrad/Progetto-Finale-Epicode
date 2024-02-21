package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.CustomerDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerList", ignore = true)
    @Mapping(target = "comune.id", source = "comuneId")
    AddressDomain fromAddressDTOToAddressDomain(AddressDTO addressDTO);

    @Mapping(target = "customerList", ignore = true)
    @Mapping(target = "comune.id", source = "comuneId")
    AddressDomain fromAddressUpdateDTOToAddressDomain(AddressUpdateDTO addressUpdateDTO);

    @Mapping(target = "customerIdList", source = "customerList", qualifiedByName = "fromCustomerDomainListToCustomerIdList")
    @Mapping(target = "comuneId", source = "comune.id")
    AddressOutputDTO fromAddressDomainToAddressOutputDTO(AddressDomain addressDomain);

    List<AddressOutputDTO> fromAddressDomainListToAddressOutputDTOList(List<AddressDomain> addressDomainList);

    @Named("fromCustomerDomainListToCustomerIdList")
    default List<Long> fromCustomerDomainListToCustomerIdList(List<CustomerDomain> customerDomainList){
        if((Objects.nonNull(customerDomainList) && (!customerDomainList.isEmpty()))){
            return customerDomainList.stream()
                    .map(CustomerDomain::getId)
                    .toList();
        }
        return Collections.emptyList();
    }

}
