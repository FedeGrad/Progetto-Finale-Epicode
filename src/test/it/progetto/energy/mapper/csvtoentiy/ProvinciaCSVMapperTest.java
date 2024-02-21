package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.csvbuilder.buildCsv.buildProvinciaCSV;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildProvinciaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProvinciaCSVMapperTest {

    @Autowired
    ProvinciaCSVMapper provinciaCSVMapper;

    @Test
    void fromProvinciaCSVToProvinciaEntity() {
        var provinciaCSV = buildProvinciaCSV();
        var expected = buildProvinciaEntity(null);

        ProvinciaEntity actual = provinciaCSVMapper.fromProvinciaCSVToProvinciaEntity(provinciaCSV);

        assertEquals(expected, actual);
    }

    @Test
    void fromProvinciaCSVListToProvinciaEntityList() {
        var provinciaCSV = List.of(buildProvinciaCSV());
        var expected = List.of(buildProvinciaEntity(null));

        List<ProvinciaEntity> actual = provinciaCSVMapper.fromProvinciaCSVListToProvinciaEntityList(provinciaCSV);

        assertEquals(expected, actual);
    }

}