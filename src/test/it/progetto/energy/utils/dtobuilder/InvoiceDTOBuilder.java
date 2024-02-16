package it.progetto.energy.utils.dtobuilder;

import it.progetto.energy.dto.invoice.InvoiceDTO;
import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.dto.invoice.InvoiceUpdateDTO;
import it.progetto.energy.model.StatoFattura;

import java.time.LocalDate;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.ConstantForTest.YEAR;

public class InvoiceDTOBuilder {

    public static InvoiceDTO buildInvoiceDTO(){
        return InvoiceDTO.builder()
                .year(YEAR)
                .date(LocalDate.now())
                .amount(Double.MAX_VALUE)
                .number(1)
                .amountIVA(Double.MIN_VALUE)
                .percentageIVA(Double.MIN_NORMAL)
                .amountDiscount(Double.MIN_VALUE)
                .percentageDiscount(Double.MIN_NORMAL)
                .state(StatoFattura.PAGATA)
                .customerId(ENTITY_ID)
                .build();
    }

    public static InvoiceUpdateDTO buildInvoiceUpdateDTO(){
        return InvoiceUpdateDTO.builder()
                .id(ENTITY_ID)
                .year(YEAR)
                .date(LocalDate.now())
                .amount(Double.MAX_VALUE)
                .number(1)
                .amountIVA(Double.MIN_VALUE)
                .percentageIVA(Double.MIN_NORMAL)
                .amountDiscount(Double.MIN_VALUE)
                .percentageDiscount(Double.MIN_NORMAL)
                .state(StatoFattura.PAGATA)
                .customerId(ENTITY_ID)
                .build();
    }

    public static InvoiceOutputDTO buildInvoiceOutputDTO(){
        return InvoiceOutputDTO.builder()
                .id(ENTITY_ID)
                .year(YEAR)
                .date(LocalDate.now())
                .amount(Double.MAX_VALUE)
                .number(1)
                .amountIVA(Double.MIN_VALUE)
                .amountDiscount(Double.MIN_VALUE)
                .state(StatoFattura.PAGATA)
                .customerId(ENTITY_ID)
                .build();
    }

}
