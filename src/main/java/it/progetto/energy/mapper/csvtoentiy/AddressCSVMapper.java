package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.csv.AddressCSV;
import it.progetto.energy.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressCSVMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "comune", ignore = true)
    AddressEntity fromAddressCSVToAddressEntity(AddressCSV addressCSV);

    List<AddressEntity> fromAddressCSVListToAddressEntityList(List<AddressCSV> addressCSVList);

}
