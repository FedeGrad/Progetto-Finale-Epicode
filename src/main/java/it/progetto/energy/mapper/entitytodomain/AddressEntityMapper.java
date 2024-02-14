package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerEntityMapper.class, ComuneEntityMapper.class})
public interface AddressEntityMapper {

    @Mapping(target = "customerList", source = "customer")
    AddressDomain fromAddressEntityToAddressDomain(AddressEntity addressEntity);

    List<AddressDomain> fromAddressEntityListToAddressDomainList(List<AddressEntity> addressEntities);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "location", source = "comune.name")
    AddressEntity fromAddressDomainToAddressEntity(AddressDomain addressDomain);

}
