package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.invoice.InvoiceDTO;
import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.dto.invoice.InvoiceUpdateDTO;
import it.progetto.energy.model.InvoiceDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerDTOMapper.class, Page.class})
public interface InvoiceDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "customer.id", source = "customerId")
    InvoiceDomain fromInvoiceDTOToInvoiceDomain(InvoiceDTO invoiceDTO);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "customer.id", source = "customerId")
    InvoiceDomain fromInvoiceUpdateDTOToInvoiceDomain(InvoiceUpdateDTO invoiceDTO);

    @Mapping(target = "customerId", source = "customer.id")
    InvoiceOutputDTO fromInvoiceDomainToInvoiceOutputDTO(InvoiceDomain invoiceDomain);

    List<InvoiceOutputDTO> fromInvoiceListDomainToInvoiceOutputDTOList(List<InvoiceDomain> invoiceDomainList);

}
