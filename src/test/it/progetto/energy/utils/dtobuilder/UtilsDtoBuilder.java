package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.PageDTO;
import org.springframework.data.domain.Sort;

public class UtilsDtoBuilder {

    public static PageDTO buildPageDTO(){
        return PageDTO.builder()
                .page(0)
                .size(10)
                .direction(Sort.Direction.ASC)
                .build();
    }

}
