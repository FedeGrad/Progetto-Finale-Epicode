package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring"/*, uses = {CustomerEntityMapper.class}*/)
public interface InvoiceEntityMapper {

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "customer", ignore = true)
    InvoiceEntity fromInvoiceDomainToInvoiceEntity(InvoiceDomain invoiceDomain);

    @Mapping(target = "file", ignore = true)
    InvoiceDomain fromInvoiceEntityToInvoiceDomain(InvoiceEntity invoiceEntity);

    List<InvoiceDomain> fromInvoiceEntityListToInvoiceDomainList(List<InvoiceEntity> invoiceEntityList);

}
