package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.invoice.InvoiceAddPDFDTO;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.StatoFattura;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {

    List<InvoiceDomain> findAllInvoice();

    List<InvoiceDomain> findAllInvoice(Pageable page);

    List<InvoiceDomain> findInvoiceByCustomer(Long customerId);

    List<InvoiceDomain> findInvoiceByAmountBetween(RangeDTO rangeDTO, Pageable page);

    List<InvoiceDomain> findInvoiceByState(StatoFattura stato, Pageable page);

    List<InvoiceDomain> findInvoiceByDate(DataDTO data, Pageable page);

    List<InvoiceDomain> findInvoiceByYear(Integer year, Pageable page);

    InvoiceDomain createInvoice(InvoiceDomain invoiceDomain);

    InvoiceDomain updateInvoice(InvoiceDomain invoiceDomain);

    void uploadInvoicePDF(InvoiceAddPDFDTO invoiceAddPDFDTO) throws IOException;

    void deleteInvoice(Long id);

}
