package it.progetto.energy.utils.entitybuilder;

import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.model.Tipologia;
import it.progetto.energy.persistence.entity.AddressEntity;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.entity.CustomerEntity;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import it.progetto.energy.persistence.entity.ProvinciaEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static it.progetto.energy.utils.ConstantForTest.COMPANY_NAME;
import static it.progetto.energy.utils.ConstantForTest.DATA_CREATE;
import static it.progetto.energy.utils.ConstantForTest.DATA_LAST_UPDATE;
import static it.progetto.energy.utils.ConstantForTest.DATE_OF_BIRTH;
import static it.progetto.energy.utils.ConstantForTest.EMAIL;
import static it.progetto.energy.utils.ConstantForTest.LOCATION;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.NPI;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.PEC;
import static it.progetto.energy.utils.ConstantForTest.PHONE_NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.REGION;
import static it.progetto.energy.utils.ConstantForTest.SIGLA_PROVINCIA;
import static it.progetto.energy.utils.ConstantForTest.SURNAME;
import static it.progetto.energy.utils.ConstantForTest.WAY;
import static it.progetto.energy.utils.ConstantForTest.YEAR;

public class EntityBuilder {

    public static AddressEntity buildAddressEntity(Long entityId){
        return AddressEntity.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .location(LOCATION)
                .postalCode(POSTAL_CODE)
                .comune(null)
                .customer(null)
                .build();
    }

    public static ComuneEntity buildComuneEntity(Long entityId){
        return ComuneEntity.builder()
                .id(entityId)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provincia(null)
                .addressList(null)
                .build();
    }

    public static ProvinciaEntity buildProvinciaEntity(Long entityId){
        return ProvinciaEntity.builder()
                .id(entityId)
                .sigla(SIGLA_PROVINCIA)
                .name(NAME)
                .region(REGION)
                .comuneList(null)
                .build();
    }

    public static CustomerEntity buildCustomerEntity(Long entityId){
        return CustomerEntity.builder()
                .id(entityId)
                .companyName(COMPANY_NAME)
                .npi(NPI)
                .email(EMAIL)
                .dataCreate(DATA_CREATE)
                .dataLastUpdate(DATA_LAST_UPDATE)
                .annualTurnover(BigDecimal.ONE)
                .type(Tipologia.PA)
                .pec(PEC)
                .companyPhone(PHONE_NUMBER)
                .customerEmail(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .customerPhone(PHONE_NUMBER)
                .age(23)
                .address(null)
                .invoiceList(null)
                .build();
    }

    public static InvoiceEntity buildInvoiceEntity(Long entityId){
        return InvoiceEntity.builder()
                .id(entityId)
                .year(YEAR)
                .date(LocalDate.now())
                .amount(Double.MAX_VALUE)
                .number(1)
                .file(null)
                .amountIVA(Double.MIN_VALUE)
                .percentageIVA(Double.MIN_NORMAL)
                .amountDiscount(Double.MIN_VALUE)
                .percentageDiscount(Double.MIN_NORMAL)
                .state(StatoFattura.PAGATA)
                .customer(null)
                .build();
    }

}
