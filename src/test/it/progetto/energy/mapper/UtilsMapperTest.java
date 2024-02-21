package it.progetto.energy.mapper;

import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.model.PageDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static it.progetto.energy.utils.domainbuilder.UtilsDomainBuilder.buildPageDomain;
import static it.progetto.energy.utils.dtobuilder.UtilsDtoBuilder.buildPageDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UtilsMapperTest {

    @Autowired
    UtilsMapper utilsMapper;

    @Test
    void fromPageDTOToPageDomain() {
        PageDTO pageDTO = buildPageDTO();
        PageDomain expected = buildPageDomain();

        PageDomain actual = utilsMapper.fromPageDTOToPageDomain(pageDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromPageDomainToPageable() {
        PageDomain pageDomain = buildPageDomain();

        PageRequest pageRequest = utilsMapper.fromPageDomainToPageable(pageDomain);

        Boolean result = pageRequest.getSort().get()
                .map(order -> order.getDirection().equals(Sort.Direction.ASC))
                .findFirst()
                .orElse(null);
        assertTrue(pageRequest.isPaged());
        assertEquals(Boolean.TRUE, result);
    }

}