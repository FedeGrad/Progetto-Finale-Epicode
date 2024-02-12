package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerEntityMapper.class})
public interface AddressEntityMapper {

    @Mapping(target = "way", source = "via")
    @Mapping(target = "postalCode", source = "cap")
    @Mapping(target = "number", source = "civico")
    @Mapping(target = "customer", source = "cliente")
    AddressDomain fromAddressEntityToAddressDomain(IndirizzoLegale indirizzoLegale);

    List<AddressDomain> fromAddressEntityListToAddressDomainList(List<IndirizzoLegale> indirizzoLegale);

    @Mapping(target = "localita", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "via", source = "way")
    @Mapping(target = "civico", source = "number")
    @Mapping(target = "cap", source = "postalCode")
    IndirizzoLegale fromAddressDomainToAddressEntity(AddressDomain addressDomain);

}
