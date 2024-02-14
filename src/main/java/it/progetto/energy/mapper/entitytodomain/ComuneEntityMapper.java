package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneEntityMapper {

    @Mapping(target = "addressList", ignore = true)
    ComuneEntity fromComuneDomainToComune(ComuneDomain comuneDomain);

    @Mapping(target = "addressList", ignore = true)
    ComuneDomain fromComuneToComuneDomain(ComuneEntity comuneEntity);

    List<ComuneDomain> fromComuneListToComuneDomainList(List<ComuneEntity> comuneEntityList);

}
