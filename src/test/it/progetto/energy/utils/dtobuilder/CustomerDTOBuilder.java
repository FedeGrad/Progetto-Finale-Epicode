package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.customer.CustomerDTO;
import it.progetto.energy.dto.customer.CustomerOutputDTO;
import it.progetto.energy.dto.customer.CustomerUpdateDTO;
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

public class CustomerDTOBuilder {

    public static CustomerDTO buildCustomerDTO(){
        return CustomerDTO.builder()
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
                .addressId(ENTITY_ID)
                .invoiceIdList(List.of(ENTITY_ID))
                .build();
    }

    public static CustomerUpdateDTO buildCustomerUpdateDTO(){
        return CustomerUpdateDTO.builder()
                .id(ENTITY_ID)
                .companyName(COMPANY_NAME)
                .npi(NPI)
                .email(EMAIL)
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
                .addressId(ENTITY_ID)
                .invoiceIdList(List.of(ENTITY_ID))
                .build();
    }

    public static CustomerOutputDTO buildCustomerOutputDTO(){
        return CustomerOutputDTO.builder()
                .id(ENTITY_ID)
                .companyName(COMPANY_NAME)
                .npi(NPI)
                .email(EMAIL)
                .dataCreate(DATA_CREATE)
                .dataLastUpdate(DATA_LAST_UPDATE)
                .annualTurnover(BigDecimal.ONE)
                .type(Tipologia.PA.name())
                .pec(PEC)
                .companyPhone(PHONE_NUMBER)
                .customerEmail(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .customerPhone(PHONE_NUMBER)
                .addressId(ENTITY_ID)
                .invoiceIdList(List.of(ENTITY_ID))
                .build();
    }

}
