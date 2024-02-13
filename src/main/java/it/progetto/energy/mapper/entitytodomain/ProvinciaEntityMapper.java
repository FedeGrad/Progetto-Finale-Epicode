package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ProvinciaDomain;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ComuneEntityMapper.class})
public interface ProvinciaEntityMapper {

    ProvinciaEntity fromProvinciaDomainToProvinciaEntity(ProvinciaDomain provinciaDomain);

    ProvinciaDomain fromProvinciaEntityToProvinciaDomain(ProvinciaEntity provinciaEntity);

    List<ProvinciaDomain> fromProvinciaEntityListToProvinciaDomainList(List<ProvinciaEntity> provinciaEntityList);

}
