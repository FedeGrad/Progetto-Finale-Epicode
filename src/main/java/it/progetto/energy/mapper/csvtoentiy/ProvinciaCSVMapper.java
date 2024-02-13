package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.csv.ProvinciaCSV;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProvinciaCSVMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comuneList", ignore = true)
    @Mapping(target = "region", source = "regione")
    @Mapping(target = "name", source = "nome")
    ProvinciaEntity fromProvinciaCSVToProvinciaEntity(ProvinciaCSV provinciaCSV);

    List<ProvinciaEntity> fromProvinciaCSVListToProvinciaEntityList(List<ProvinciaCSV> provinciaCSVList);

}
