package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.StatoFattura;

import java.time.LocalDate;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.YEAR;

public class InvoiceDomainBuilder {

    public static InvoiceDomain buildInvoiceDomainInput(Long entityId){
        return InvoiceDomain.builder()
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
                .customer(CustomerDomain.builder().id(ENTITY_ID).build())
                .build();
    }

    public static InvoiceDomain buildInvoiceDomainOutput(Long entityId){
        return InvoiceDomain.builder()
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
                .customer(CustomerDomain.builder().id(ENTITY_ID).build())
                .build();
    }

}
