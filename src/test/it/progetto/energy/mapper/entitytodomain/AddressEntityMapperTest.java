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
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildAddressDomain;
import static it.progetto.energy.utils.entitybuilder.EntityBuilder.buildAddressEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressEntityMapperTest {

    @Autowired
    AddressEntityMapper addressEntityMapper;

    @Test
    void fromAddressEntityToAddressDomain() {
        AddressEntity addressEntity = buildAddressEntity(ENTITY_ID);
        AddressDomain expected = buildAddressDomain(ENTITY_ID);
        expected.setCustomerList(Collections.emptyList());

        AddressDomain actual = addressEntityMapper.fromAddressEntityToAddressDomain(addressEntity);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressEntityListToAddressDomainList() {
        List<AddressEntity> addressEntityList = List.of(buildAddressEntity(ENTITY_ID));
        AddressDomain addressDomain = buildAddressDomain(ENTITY_ID);
        addressDomain.setCustomerList(Collections.emptyList());
        List<AddressDomain> expected = List.of(addressDomain);

        List<AddressDomain> actual = addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);

        assertEquals(expected, actual);
    }

    @Test
    void fromAddressDomainToAddressEntity() {
        ComuneDomain comuneDomain = ComuneDomain.builder().name(LOCATION).build();
        AddressDomain addressDomain = buildAddressDomain(ENTITY_ID);
        addressDomain.setComune(comuneDomain);
        ComuneEntity comune = ComuneEntity.builder().name(LOCATION).build();
        AddressEntity expected = buildAddressEntity(ENTITY_ID);
        expected.setComune(comune);

        AddressEntity actual = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);

        assertEquals(expected, actual);
    }

}