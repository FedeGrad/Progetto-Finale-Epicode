package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.persistence.entity.Fattura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerEntityMapper.class})
public interface InvoiceEntityMapper {

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "stato", source = "state")
    @Mapping(target = "percentualeSconto", source = "percentageDiscount")
    @Mapping(target = "percentualeIVA", source = "percentageIVA")
    @Mapping(target = "numero", source = "number")
    @Mapping(target = "importoSconto", source = "amountDiscount")
    @Mapping(target = "importoIVA", source = "amountIVA")
    @Mapping(target = "importo", source = "amount")
    @Mapping(target = "data", source = "date")
    @Mapping(target = "anno", source = "year")
    Fattura fromInvoiceDomainToInvoiceEntity(InvoiceDomain invoiceDomain);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "date", source = "data")
    @Mapping(target = "year", source = "anno")
    @Mapping(target = "state", source = "stato")
    @Mapping(target = "percentageIVA", source = "percentualeIVA")
    @Mapping(target = "percentageDiscount", source = "percentualeSconto")
    @Mapping(target = "number", source = "numero")
    @Mapping(target = "amountIVA", source = "importoIVA")
    @Mapping(target = "amountDiscount", source = "importoSconto")
    @Mapping(target = "amount", source = "importo")
    InvoiceDomain fromInvoiceEntityToInvoiceDomain(Fattura fattura);

    List<InvoiceDomain> fromInvoiceEntityListToInvoiceDomainList(List<Fattura> fatturaList);

}
