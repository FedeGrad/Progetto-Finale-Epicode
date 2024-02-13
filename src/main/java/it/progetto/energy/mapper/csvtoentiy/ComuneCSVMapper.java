package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.csv.ComuneCorrettoCSV;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneCSVMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "provincia.sigla", source = "siglaProvincia")
    @Mapping(target = "postalCode", source = "cap")
    @Mapping(target = "name", source = "nome")
    ComuneEntity fromComuneCSVToComuneEntity(ComuneCorrettoCSV comuneCorrettoCSV);

    List<ComuneEntity> fromComuneCSVListToComuneEntityList(List<ComuneCorrettoCSV> comuneCorrettoCSVS);

}
