package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.csv.ComuneCSV;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneCSVMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "provincia.sigla", source = "siglaProvincia")
    ComuneEntity fromComuneCSVToComuneEntity(ComuneCSV comuneCSV);

    List<ComuneEntity> fromComuneCSVListToComuneEntityList(List<ComuneCSV> comuneCSVS);

}
