package it.progetto.energy.mapper;

import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.model.PageDomain;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;


@Mapper(componentModel = "spring")
public interface UtilsMapper {

    PageDomain fromPageDTOToPageDomain(PageDTO pageDTO);

    default PageRequest fromPageDomainToPageable(PageDomain pageDomain){
        return PageRequest.of(pageDomain.getPage(), pageDomain.getSize(), pageDomain.getDirection());
    }

}
