package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.invoice.InvoiceUploadPdfDTO;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.model.StatoFattura;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {

    List<InvoiceDomain> findAllInvoice();

    List<InvoiceDomain> findAllInvoice(PageDomain pageDomain);

    List<InvoiceDomain> findInvoiceByCustomer(Long customerId);

    List<InvoiceDomain> findInvoiceByAmountBetween(RangeDTO rangeDTO, PageDomain pageDomain);

    List<InvoiceDomain> findInvoiceByState(StatoFattura stato, PageDomain pageDomain);

    List<InvoiceDomain> findInvoiceByDate(DataDTO data, PageDomain pageDomain);

    List<InvoiceDomain> findInvoiceByYear(String year, PageDomain pageDomain);

    InvoiceDomain createInvoice(InvoiceDomain invoiceDomain);

    InvoiceDomain updateInvoice(InvoiceDomain invoiceDomain);

    void uploadInvoicePDF(InvoiceUploadPdfDTO invoiceUploadPdfDTO) throws IOException;

    void deleteInvoice(Long id);

}
