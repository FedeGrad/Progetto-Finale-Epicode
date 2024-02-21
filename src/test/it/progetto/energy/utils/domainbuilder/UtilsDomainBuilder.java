package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.PageDomain;
import org.springframework.data.domain.Sort;

public class UtilsDomainBuilder {

    public static PageDomain buildPageDomain(){
        return PageDomain.builder()
                .page(0)
                .size(10)
                .direction(Sort.Direction.ASC)
                .sortBy("id")
                .build();
    }

}
