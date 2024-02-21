package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.Tipologia;

import java.math.BigDecimal;
import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.COMPANY_NAME;
import static it.progetto.energy.utils.ConstantForTest.DATA_CREATE;
import static it.progetto.energy.utils.ConstantForTest.DATA_LAST_UPDATE;
import static it.progetto.energy.utils.ConstantForTest.DATE_OF_BIRTH;
import static it.progetto.energy.utils.ConstantForTest.EMAIL;
import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.NPI;
import static it.progetto.energy.utils.ConstantForTest.PEC;
import static it.progetto.energy.utils.ConstantForTest.PHONE_NUMBER;
import static it.progetto.energy.utils.ConstantForTest.SURNAME;

public class CustomerDomainBuilder {

    public static CustomerDomain buildCustomerDomainInput(Long customerId){
        return CustomerDomain.builder()
                .id(customerId)
                .companyName(COMPANY_NAME)
                .npi(NPI)
                .email(EMAIL)
                .dataCreate(null)
                .dataLastUpdate(null)
                .annualTurnover(BigDecimal.ONE)
                .type(Tipologia.PA)
                .pec(PEC)
                .companyPhone(PHONE_NUMBER)
                .customerEmail(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .customerPhone(PHONE_NUMBER)
                .age(0)
                .address(AddressDomain.builder().id(ENTITY_ID).build())
                .invoiceList(List.of(InvoiceDomain.builder().id(ENTITY_ID).build()))
                .build();
    }

    public static CustomerDomain buildCustomerDomainOutput(Long customerId){
        return CustomerDomain.builder()
                .id(customerId)
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
                .address(AddressDomain.builder().id(ENTITY_ID).build())
                .invoiceList(List.of(InvoiceDomain.builder().id(ENTITY_ID).build()))
                .build();
    }

}
