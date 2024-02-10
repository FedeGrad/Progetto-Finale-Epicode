package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.dto.fattura.FatturaDTO;
import it.progetto.energy.dto.fattura.FatturaModificaDTO;
import it.progetto.energy.dto.fattura.FatturaPDFDTO;
import it.progetto.energy.persistence.entity.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface InvoiceApi {

    @Operation(summary = "Recupera Fatture",
            description = "Restituisce tutte le Fatture presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<Fattura> findAllInvoice();

    @Operation(summary = "Recupera Fatture per pagina",
            description = "Restituisce tutte le Fatture presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    Page<Fattura> findAllInvoice(Pageable page);

    @Operation(summary = "Recupera fatture per ID",
            description = "Restituisce tutte le Fatture di un Cliente tramite l'ID Cliente")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    List<Fattura> findInvoiceByCustomer(Long invoiceId);

    @Operation(summary = "Recupera Fatture per stato",
            description = "Restituisce tutte le Fatture in un determinato stato")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    Page<Fattura> findInvoiceByState(StatoDTO statoDTO, Pageable page);

    @Operation(summary = "Recupera Fatture per data",
            description = "Restituisce tutte le Fatture per data")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    Page<Fattura> findInvoiceByDate(DataDTO dataDTO, Pageable page);

    @Operation(summary = "Recupera Fatture per anno",
            description = "Restituisce tutte le Fatture di un determinato anno")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    Page<Fattura> findInvoiceByYear(Integer year, Pageable page);

    @Operation(summary = "Recupera Fatture by range",
            description = "Restituisce tutte le Fatture in un range")
    @ApiResponse(responseCode = "200", description = "Fatture trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
    Page<Fattura> findInvoiceByRange(RangeDTO rangeDTO, Pageable page);

    @Operation(summary = "Crea Fattura in PDF",
            description = "Inserisci i dati della fattura che verrà creata e aggiunta al DB")
    @ApiResponse(responseCode = "204", description = "Fattura creata/inserita correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
    void createInvoice(FatturaDTO fatturaDTO) throws IOException;

    @Operation(summary = "Aggiunge Fattura File",
            description = "Inserisci l'id della fattura pre-creata e il File")
    @ApiResponse(responseCode = "200", description = "Fattura creata/inserita correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
    void uploadInvoice(String token, FatturaPDFDTO fatturaPDFDTO) throws IOException;

    @Operation(summary = "Modifica Fattura",
            description = "Modifica una Fattura presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Fattura modificata")
    @ApiResponse(responseCode = "404", description = "Fattura non trovata")
    @ApiResponse(responseCode = "500", description = "Errore modifica")
    void updateInvoice(FatturaModificaDTO fatturaModificaDTO);

    @Operation(summary = "Elimina Fattura",
            description = "Elimina una Fattura presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Fattura eliminata")
    @ApiResponse(responseCode = "404", description = "Fattura non trovata")
    void deleteInvoice(Long invoiceId);

}
