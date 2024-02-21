package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import it.progetto.energy.persistence.entity.ComuneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.LOCATION;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainInput;
import static it.progetto.energy.utils.domainbuilder.AddressDomainBuilder.buildAddressDomainOutput;
import static it.progetto.energy.utils.domainbuilder.ComuneDomainBuilder.buildComuneDomainOutput;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildAddressEntity;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildComuneEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressEntityMapperTest {

    @Autowired
    AddressEntityMapper addressEntityMapper;

    @Test
    void fromAddressEntityToAddressDomain() {
        var addressEntity = buildAddressEntity(ENTITY_ID);
        addressEntity.setComune(buildComuneEntity(ENTITY_ID));
        var expected = buildAddressDomainOutput(ENTITY_ID);
        var comuneDomain = buildComuneDomainOutput(ENTITY_ID);
        comuneDomain.setProvincia(null);
        comuneDomain.setAddressList(null);
        expected.setComune(comuneDomain);
        expected.setCustomerList(Collections.emptyList());

        AddressDomain actual = addressEntityMapper.fromAddressEntityToAddressDomain(addressEntity);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressEntityListToAddressDomainList() {
        var addressEntity = buildAddressEntity(ENTITY_ID);
        addressEntity.setComune(buildComuneEntity(ENTITY_ID));
        var addressEntityList = List.of(addressEntity);
        var addressDomain = buildAddressDomainOutput(ENTITY_ID);
        var comuneDomain = buildComuneDomainOutput(ENTITY_ID);
        comuneDomain.setProvincia(null);
        comuneDomain.setAddressList(null);
        addressDomain.setComune(comuneDomain);
        addressDomain.setCustomerList(Collections.emptyList());
        var expected = List.of(addressDomain);

        List<AddressDomain> actual = addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainToAddressEntity() {
        var comuneDomain = ComuneDomain.builder().name(LOCATION).build();
        var addressDomain = buildAddressDomainInput(ENTITY_ID);
        addressDomain.setComune(comuneDomain);
        var comune = ComuneEntity.builder().name(LOCATION).build();
        var expected = buildAddressEntity(ENTITY_ID);
        expected.setComune(comune);

        AddressEntity actual = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);

        assertEquals(expected, actual);
    }

}