package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneEntityMapper {

    @Mapping(target = "provincia", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    ComuneEntity fromComuneDomainToComuneEntity(ComuneDomain comuneDomain);

    ComuneDomain fromComuneEntityToComuneDomain(ComuneEntity comuneEntity);

    List<ComuneDomain> fromComuneEntityListToComuneDomainList(List<ComuneEntity> comuneEntityList);


}
