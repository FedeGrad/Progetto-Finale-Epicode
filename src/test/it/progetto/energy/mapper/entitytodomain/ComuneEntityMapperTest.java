package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.ComuneDomainBuilder.buildComuneDomainOutput;
import static it.progetto.energy.utils.domainbuilder.ProvinciaDomainBuilder.buildProvinciaDomainInput;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildComuneEntity;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildProvinciaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ComuneEntityMapperTest {

    @Autowired
    ComuneEntityMapper comuneEntityMapper;

    @Test
    void fromComuneDomainToComuneEntity() {
        var comuneDomain = buildComuneDomainOutput(ENTITY_ID);
        comuneDomain.setProvincia(buildProvinciaDomainInput(ENTITY_ID));
        var expected = buildComuneEntity(ENTITY_ID);
        expected.setProvincia(buildProvinciaEntity(ENTITY_ID));

        ComuneEntity actual = comuneEntityMapper.fromComuneDomainToComuneEntity(comuneDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromComuneEntityListToComuneDomainList() {
    }

    @Test
    void fromComuneEntityToComuneDomain() {
        var comuneEntity = buildComuneEntity(ENTITY_ID);
        comuneEntity.setProvincia(buildProvinciaEntity(ENTITY_ID));
        var expected = buildComuneDomainOutput(ENTITY_ID);

        ComuneDomain actual = comuneEntityMapper.fromComuneEntityToComuneDomain(comuneEntity);

        assertEquals(expected, actual);
    }

}