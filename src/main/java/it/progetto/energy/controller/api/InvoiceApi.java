package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.dto.invoice.InvoiceDTO;
import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.dto.invoice.InvoiceUpdateDTO;
import it.progetto.energy.dto.invoice.InvoiceUploadPdfDTO;

import java.io.IOException;
import java.util.List;

public interface InvoiceApi {

    @Operation(summary = "Recupera Fatture",
            description = "Restituisce tutte le Fatture presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findAllInvoice();

    @Operation(summary = "Recupera Fatture per pagina",
            description = "Restituisce tutte le Fatture presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findAllInvoicePaged(PageDTO pageDTO);

    @Operation(summary = "Recupera fatture per ID",
            description = "Restituisce tutte le Fatture di un Cliente tramite l'ID Cliente")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findInvoiceByCustomer(Long invoiceId);

    @Operation(summary = "Recupera Fatture per stato",
            description = "Restituisce tutte le Fatture in un determinato stato")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findInvoiceByState(StatoDTO statoDTO, PageDTO pageDTO);

    @Operation(summary = "Recupera Fatture per data",
            description = "Restituisce tutte le Fatture per data")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findInvoiceByDate(DataDTO dataDTO, PageDTO pageDTO);

    @Operation(summary = "Recupera Fatture per anno",
            description = "Restituisce tutte le Fatture di un determinato anno")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findInvoiceByYear(String year, PageDTO pageDTO);

    @Operation(summary = "Recupera Fatture by range",
            description = "Restituisce tutte le Fatture in un range")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<InvoiceOutputDTO> findInvoiceByRange(RangeDTO rangeDTO, PageDTO pageDTO);

    @Operation(summary = "Crea Fattura in PDF",
            description = "Inserisci i dati della fattura che verrà creata e aggiunta al DB")
    @ApiResponse(responseCode = "204", description = "Fattura creata/inserita correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
    InvoiceOutputDTO createInvoice(InvoiceDTO invoiceDTO);

    @Operation(summary = "Aggiunge Fattura File",
            description = "Inserisci l'id della fattura pre-creata e il File")
    @ApiResponse(responseCode = "200", description = "Fattura creata/inserita correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
    void uploadInvoice(String token, InvoiceUploadPdfDTO invoiceUploadPdfDTO) throws IOException;

    @Operation(summary = "Modifica Fattura",
            description = "Modifica una Fattura presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Fattura modificata")
    @ApiResponse(responseCode = "404", description = "Fattura non trovata")
    @ApiResponse(responseCode = "500", description = "Errore modifica")
    InvoiceOutputDTO updateInvoice(InvoiceUpdateDTO invoiceUpdateDTO);

    @Operation(summary = "Elimina Fattura",
            description = "Elimina una Fattura presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Fattura eliminata")
    @ApiResponse(responseCode = "404", description = "Fattura non trovata")
    void deleteInvoice(Long invoiceId);

}
