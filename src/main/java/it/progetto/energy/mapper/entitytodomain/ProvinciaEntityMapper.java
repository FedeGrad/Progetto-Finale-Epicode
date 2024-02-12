package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ProvinciaDomain;
import it.progetto.energy.persistence.entity.Provincia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ComuneEntityMapper.class})
public interface ProvinciaEntityMapper {

    @Mapping(target = "regione", source = "region")
    @Mapping(target = "nome", source = "name")
    @Mapping(target = "comuni", source = "comuneList")
    Provincia fromProvinciaDomainToProvinciaEntity(ProvinciaDomain provinciaDomain);

    @Mapping(target = "region", source = "regione")
    @Mapping(target = "name", source = "nome")
    @Mapping(target = "comuneList", source = "comuni")
    ProvinciaDomain fromProvinciaEntityToProvinciaDomain(Provincia provincia);

    List<ProvinciaDomain> fromProvinciaEntityListToProvinciaDomainList(List<Provincia> provinciaList);

}
