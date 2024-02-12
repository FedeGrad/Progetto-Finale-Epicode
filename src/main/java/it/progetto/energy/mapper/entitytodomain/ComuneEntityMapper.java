package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.Comune;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneEntityMapper {

    @Mapping(target = "indirizziOperativi", ignore = true)
    @Mapping(target = "indirizziLegali", ignore = true)
    @Mapping(target = "nome", source = "name")
    @Mapping(target = "cap", source = "postalCode")
    Comune fromComuneDomainToComune(ComuneDomain comuneDomain);

    @Mapping(target = "indirizziOperationalDomainList", ignore = true)
    @Mapping(target = "addressDomainList", ignore = true)
    @Mapping(target = "postalCode", source = "cap")
    @Mapping(target = "name", source = "nome")
    ComuneDomain fromComuneToComuneDomain(Comune comune);

    List<ComuneDomain> fromComuneListToComuneDomainList(List<Comune> comuneList);

}
