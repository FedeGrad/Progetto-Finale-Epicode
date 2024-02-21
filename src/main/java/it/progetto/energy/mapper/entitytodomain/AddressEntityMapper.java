package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerEntityMapper.class})
public interface AddressEntityMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "comune", ignore = true)
    AddressEntity fromAddressDomainToAddressEntity(AddressDomain addressDomain);

    @Mapping(target = "customerList", source = "customer")
    AddressDomain fromAddressEntityToAddressDomain(AddressEntity addressEntity);

    List<AddressDomain> fromAddressEntityListToAddressDomainList(List<AddressEntity> addressEntities);


}
