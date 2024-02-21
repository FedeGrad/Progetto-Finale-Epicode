package it.progetto.energy.mapper.csvtoentiy;

import it.progetto.energy.persistence.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.csvbuilder.buildCsv.buildAddressCSV;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildAddressEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressCSVMapperTest {

    @Autowired
    AddressCSVMapper addressCSVMapper;

    @Test
    void fromAddressCSVToAddressEntity() {
        var addressCSV = buildAddressCSV();
        var expected = buildAddressEntity(null);

        AddressEntity actual = addressCSVMapper.fromAddressCSVToAddressEntity(addressCSV);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressCSVListToAddressEntityList() {
        var addressCSVList = List.of(buildAddressCSV());
        var expectedList = List.of(buildAddressEntity(null));

        List<AddressEntity> actualList = addressCSVMapper.fromAddressCSVListToAddressEntityList(addressCSVList);

        assertEquals(expectedList, actualList);
    }

}