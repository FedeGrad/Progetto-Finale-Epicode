package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.provincia.ProvinciaOutputDTO;
import it.progetto.energy.model.ProvinciaDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.ProvinciaDomainBuilder.buildProvinciaDomainInput;
import static it.progetto.energy.utils.domainbuilder.ProvinciaDomainBuilder.buildProvinciaDomainOutput;
import static it.progetto.energy.utils.dtobuilder.ProvinciaDTOBuilder.buildProvinciaDTO;
import static it.progetto.energy.utils.dtobuilder.ProvinciaDTOBuilder.buildProvinciaOutputDTO;
import static it.progetto.energy.utils.dtobuilder.ProvinciaDTOBuilder.buildProvinciaUpdateDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProvinciaDTOMapperTest {

    @Autowired
    ProvinciaDTOMapper provinciaDTOMapper;

    @Test
    void fromProvinciaDTOToProvinciaDomain() {
        var provinciaDTO = buildProvinciaDTO();
        var expected = buildProvinciaDomainInput(null);

        ProvinciaDomain actual = provinciaDTOMapper.fromProvinciaDTOToProvinciaDomain(provinciaDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromProvinciaUpdateDTOToProvinciaDomain() {
        var provinciaDTO = buildProvinciaUpdateDTO();
        var expected = buildProvinciaDomainInput(ENTITY_ID);

        ProvinciaDomain actual = provinciaDTOMapper.fromProvinciaUpdateDTOToProvinciaDomain(provinciaDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromProvinciaDomainToProvinciaOutputDTO() {
        var provinciaDomain = buildProvinciaDomainOutput(ENTITY_ID);
        var expected = buildProvinciaOutputDTO();

        ProvinciaOutputDTO actual = provinciaDTOMapper.fromProvinciaDomainToProvinciaOutputDTO(provinciaDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromProvinciaDomainListToProvinciaOutputDTOList() {
        var provinciaDomainList = List.of(buildProvinciaDomainOutput(ENTITY_ID));
        var provinciaOutputDTO = buildProvinciaOutputDTO();
        var expected = List.of(provinciaOutputDTO);

        List<ProvinciaOutputDTO> actual = provinciaDTOMapper.fromProvinciaDomainListToProvinciaOutputDTOList(provinciaDomainList);

        assertEquals(expected, actual);
    }

}