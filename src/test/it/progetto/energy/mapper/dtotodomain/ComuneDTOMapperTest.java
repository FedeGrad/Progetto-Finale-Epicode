package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.comune.ComuneDTO;
import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.dto.comune.ComuneUpdateDTO;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.ProvinciaDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildComuneDomain;
import static it.progetto.energy.utils.dtobuilder.ComuneDTOBuilder.buildComuneDTO;
import static it.progetto.energy.utils.dtobuilder.ComuneDTOBuilder.buildComuneOutputDTO;
import static it.progetto.energy.utils.dtobuilder.ComuneDTOBuilder.buildComuneUpdateDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ComuneDTOMapperTest {

    @Autowired
    ComuneDTOMapper comuneDTOMapper;

    @Test
    void fromComuneDTOToComuneDomain() {
        ComuneDTO comuneDTO = buildComuneDTO();
        ProvinciaDomain provincia = ProvinciaDomain.builder().id(ENTITY_ID).build();
        ComuneDomain expected = buildComuneDomain(null);
        expected.setProvincia(provincia);

        ComuneDomain actual = comuneDTOMapper.fromComuneDTOToComuneDomain(comuneDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneUpdateDTOToComuneDomain() {
        ComuneUpdateDTO comuneUpdateDTO = buildComuneUpdateDTO();
        ProvinciaDomain provincia = ProvinciaDomain.builder().id(ENTITY_ID).build();
        ComuneDomain expected = buildComuneDomain(ENTITY_ID);
        expected.setProvincia(provincia);

        ComuneDomain actual = comuneDTOMapper.fromComuneUpdateDTOToComuneDomain(comuneUpdateDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneDomainToComuneOutputDTO() {
        ProvinciaDomain provincia = ProvinciaDomain.builder().id(ENTITY_ID).build();
        ComuneDomain comuneDomain = buildComuneDomain(ENTITY_ID);
        comuneDomain.setProvincia(provincia);
        ComuneOutputDTO expected = buildComuneOutputDTO();

        ComuneOutputDTO actual = comuneDTOMapper.fromComuneDomainToComuneOutputDTO(comuneDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneDomainListToComuneOutputDTOList() {
        ProvinciaDomain provincia = ProvinciaDomain.builder().id(ENTITY_ID).build();
        ComuneDomain comuneDomain = buildComuneDomain(ENTITY_ID);
        comuneDomain.setProvincia(provincia);
        List<ComuneDomain> comuneDomainList = List.of(comuneDomain);
        List<ComuneOutputDTO> expected = List.of(buildComuneOutputDTO());

        List<ComuneOutputDTO> actual = comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);

        assertEquals(expected, actual);
    }

}