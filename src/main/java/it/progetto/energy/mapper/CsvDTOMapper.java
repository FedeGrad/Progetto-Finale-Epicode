package it.progetto.energy.mapper;

import it.progetto.energy.dto.csv.CsvImportedOutputDTO;
import it.progetto.energy.model.CsvImportedDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CsvDTOMapper {

    CsvImportedOutputDTO fromCsvImportedDomainToCsvImportedOutputDTO(CsvImportedDomain csvImportedDomain);

}
