package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.comune.ComuneOutputDTO;
import it.progetto.energy.model.ComuneDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.ComuneDomainBuilder.buildComuneDomainInput;
import static it.progetto.energy.utils.domainbuilder.ComuneDomainBuilder.buildComuneDomainOutput;
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
        var comuneDTO = buildComuneDTO();
        var expected = buildComuneDomainInput(null);

        ComuneDomain actual = comuneDTOMapper.fromComuneDTOToComuneDomain(comuneDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneUpdateDTOToComuneDomain() {
        var comuneUpdateDTO = buildComuneUpdateDTO();
        var expected = buildComuneDomainInput(ENTITY_ID);

        ComuneDomain actual = comuneDTOMapper.fromComuneUpdateDTOToComuneDomain(comuneUpdateDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneDomainToComuneOutputDTO() {
        var comuneDomain = buildComuneDomainOutput(ENTITY_ID);
        var expected = buildComuneOutputDTO();

        ComuneOutputDTO actual = comuneDTOMapper.fromComuneDomainToComuneOutputDTO(comuneDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneDomainListToComuneOutputDTOList() {
        var comuneDomain = buildComuneDomainOutput(ENTITY_ID);
        var comuneDomainList = List.of(comuneDomain);
        var expected = List.of(buildComuneOutputDTO());

        List<ComuneOutputDTO> actual = comuneDTOMapper.fromComuneDomainListToComuneOutputDTOList(comuneDomainList);

        assertEquals(expected, actual);
    }

}