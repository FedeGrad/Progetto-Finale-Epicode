package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.PageDTO;

public class UtilsDtoBuilder {

    public static PageDTO buildPageDTO(){
        return PageDTO.builder()
                .page(0)
                .size(10)
                .sortDirection("ASC")
                .sortBy("id")
                .build();
    }

}
