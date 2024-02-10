package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AddressOperationalApi {

    @Operation(summary = "Ritorno Indirizzi Operativi",
            description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
    List<IndirizzoOperativo> findAllAddressOperational();

    @Operation(summary = "Recupero Indirizzi Operativi per pagina",
            description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
    Page<IndirizzoOperativo> findAllAddressOperational(Pageable page);

    @Operation(summary = "Inserimento Indirizzo Operativo",
            description = "Inserisce un Indirizzo Operativo nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Op. inserito correttamente nel sistema")
    @ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
    void createOperationalAddress(IndirizzoDTO indirizzoDTO) throws ElementAlreadyPresentException;

    @Operation(summary = "Modifica Indirizzo Operativo",
            description = "Modifica Indirizzo Operativo presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Op. modificato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
    void updateOperationalAddress(@RequestBody IndirizzoModificaDTO indirizzoModificaDTO);

    @Operation(summary = "Eliminazione Indirizzo Operativo",
            description = "Elimina un Indirizzo Operativo presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Indirizzo Op. eliminato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
    void deleteOperationalAddress(@PathVariable("id") Long operationalAddressId);
}
