package it.progetto.energy.utils.domainbuilder;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.model.StatoFattura;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static it.progetto.energy.utils.ConstantForTest.NAME;
import static it.progetto.energy.utils.ConstantForTest.NUMBER;
import static it.progetto.energy.utils.ConstantForTest.POSTAL_CODE;
import static it.progetto.energy.utils.ConstantForTest.WAY;

public class DomainBuilder {

    public static AddressDomain buildAddressDomain(Long entityId){
        return AddressDomain.builder()
                .id(entityId)
                .way(WAY)
                .number(NUMBER)
                .postalCode(POSTAL_CODE)
                .comune(null)
                .customerList(null)
                .build();
    }

    public static ComuneDomain buildComuneDomain(Long entityId){
        return ComuneDomain.builder()
                .id(entityId)
                .name(NAME)
                .postalCode(POSTAL_CODE)
                .provincia(null)
                .addressList(null)
                .build();
    }

    public static PageDomain buildPageDomain(){
        return PageDomain.builder()
                .page(0)
                .size(1)
                .direction(Sort.Direction.DESC)
                .sortBy("id")
                .build();
    }

    public static InvoiceDomain buildInvoiceDomain(Long entityId){
        return InvoiceDomain.builder()
                .id(entityId)
                .year("2024")
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
