package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.provincia.ProvinciaDTO;
import it.progetto.energy.dto.provincia.ProvinciaOutputDTO;
import it.progetto.energy.dto.provincia.ProvinciaUpdateDTO;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.ProvinciaDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProvinciaDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comuneList", source = "comuneIdList", qualifiedByName = "fromComuneIdListToComuneDomainList")
    ProvinciaDomain fromProvinciaDTOToProvinciaDomain(ProvinciaDTO provinciaDTO);

    @Mapping(target = "comuneList", source = "comuneIdList", qualifiedByName = "fromComuneIdListToComuneDomainList")
    ProvinciaDomain fromProvinciaUpdateDTOToProvinciaDomain(ProvinciaUpdateDTO provinciaUpdateDTO);

    @Mapping(target = "comuneIdList", source = "comuneList", qualifiedByName = "fromComuneDomainListToComuneIdList")
    ProvinciaOutputDTO fromProvinciaDomainToProvinciaOutputDTO(ProvinciaDomain provinciaDomain);

    List<ProvinciaOutputDTO> fromProvinciaDomainListToProvinciaOutputDTOList(List<ProvinciaDomain> provinciaDomainList);

    @Named("fromComuneIdListToComuneDomainList")
    default List<ComuneDomain> fromComuneIdListToComuneDomainList(List<Long> comuneIdList){
        if (!comuneIdList.isEmpty()){
            List<ComuneDomain> comuneDomainList = new ArrayList<>();
            comuneIdList.forEach(comuneId -> {
                ComuneDomain comuneDomain = ComuneDomain.builder()
                        .id(comuneId)
                        .build();
                comuneDomainList.add(comuneDomain);
            });
            return comuneDomainList;
        }
        return Collections.emptyList();
    }

    @Named("fromComuneDomainListToComuneIdList")
    default List<Long> fromComuneDomainListToComuneIdList(List<ComuneDomain> comuneDomainList){
        if(!comuneDomainList.isEmpty()){
            return comuneDomainList.stream()
                    .map(ComuneDomain::getId)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
