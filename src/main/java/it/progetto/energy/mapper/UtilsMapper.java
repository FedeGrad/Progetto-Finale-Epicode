package it.progetto.energy.mapper;

import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.exception.FileException;
import it.progetto.energy.model.PageDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_CONVER_FILE;

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
        return "ASC".equals(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    default File fromMultipartFileToFile(MultipartFile multipartFile) {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileCopyUtils.copy(multipartFile.getBytes(), file);
            return file;
        } catch (IOException e) {
            throw new FileException(ERROR_CONVER_FILE);
        }
    }

    default MultipartFile fromFileToMultipartFile(File file) {
        //TODO IMPLEMENTS METHOD
        return null;
    }

}