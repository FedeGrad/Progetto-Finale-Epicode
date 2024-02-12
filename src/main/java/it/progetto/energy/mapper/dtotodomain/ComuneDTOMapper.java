package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.comune.ComuneDTO;
import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.dto.comune.ComuneUpdateDTO;
import it.progetto.energy.model.ComuneDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComuneDTOMapper {

    @Mapping(target = "indirizziOperationalDomainList", ignore = true)
    @Mapping(target = "addressDomainList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provincia.id", source = "provinciaId")
    ComuneDomain fromComuneDTOToComuneDomain(ComuneDTO comuneDTO);

    @Mapping(target = "indirizziOperationalDomainList", ignore = true)
    @Mapping(target = "addressDomainList", ignore = true)
    @Mapping(target = "provincia.id", source = "provinciaId")
    ComuneDomain fromComuneUpdateDTOToComuneDomain(ComuneUpdateDTO comuneUpdateDTO);

    @Mapping(target = "provinciaId", source = "provincia.id")
    ComuneOutputDTO fromComuneDomainToComuneOutputDTO(ComuneDomain invoiceDomain);

    List<ComuneOutputDTO> fromComuneDomainListToComuneOutputDTOList(List<ComuneDomain> comuneDomainList);

}
