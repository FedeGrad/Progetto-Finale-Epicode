package it.progetto.energy.mapper;

import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.model.PageDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@Mapper(componentModel = "spring")
public interface UtilsMapper {

    @Mapping(target = "direction", source = "sortDirection", qualifiedByName = "fromDirectionToSortDirection")
    PageDomain fromPageDTOToPageDomain(PageDTO pageDTO);

    default PageRequest fromPageDomainToPageable(PageDomain pageDomain){
        Sort sort;
        if(pageDomain.getDirection().equals(Sort.Direction.ASC)){
            sort = Sort.by(Sort.Order.asc(pageDomain.getSortBy()));
        } else {
            sort = Sort.by(Sort.Order.desc(pageDomain.getSortBy()));
        }
        return PageRequest.of(pageDomain.getPage(), pageDomain.getSize(), sort);
    }

    @Named("fromDirectionToSortDirection")
    default Sort.Direction fromSortToSortDirection(String sortDirection){
        if("ASC".equals(sortDirection)){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.ASC;
    }

}
