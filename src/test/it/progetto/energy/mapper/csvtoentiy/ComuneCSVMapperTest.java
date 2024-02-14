package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.csv.ComuneCSV;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.SIGLA_PROVINCIA;
import static it.progetto.energy.utils.csvbuilder.buildCsv.buildComuneCSV;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildComuneEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ComuneCSVMapperTest {

    @Autowired
    ComuneCSVMapper comuneCSVMapper;

    @Test
    void fromComuneCSVToComuneEntity() {
        ComuneCSV comuneCSV = buildComuneCSV();
        ProvinciaEntity provincia = ProvinciaEntity.builder().sigla(SIGLA_PROVINCIA).build();
        ComuneEntity expected = buildComuneEntity(null);
        expected.setProvincia(provincia);

        ComuneEntity actual = comuneCSVMapper.fromComuneCSVToComuneEntity(comuneCSV);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneCSVListToComuneEntityList() {
        List<ComuneCSV> comuneCSVList = List.of(buildComuneCSV());
        ProvinciaEntity provincia = ProvinciaEntity.builder().sigla(SIGLA_PROVINCIA).build();
        ComuneEntity comune = buildComuneEntity(null);
        comune.setProvincia(provincia);
        List<ComuneEntity> expected = List.of(comune);

        List<ComuneEntity> actual = comuneCSVMapper.fromComuneCSVListToComuneEntityList(comuneCSVList);

        assertEquals(expected, actual);
    }

}