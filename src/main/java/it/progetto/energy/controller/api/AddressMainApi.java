package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.indirizzo.IndirizzoDTO;
import it.progetto.energy.dto.indirizzo.IndirizzoUpdateDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressMainApi {

    @Operation(summary = "Recupero Indirizzi Legali",
            description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
    List<IndirizzoLegale> findAllMainAddress();

    @Operation(summary = "Recupero Indirizzi Legali",
            description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
    Page<IndirizzoLegale> findAllMainAddress(Pageable page);

    @Operation(summary = "Inserimento Indirizzo Legale",
            description = "Inserisce un Indirizzo Legale nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Leg. inserito correttamente")
    void createMainAddress(IndirizzoDTO dto) throws ElementAlreadyPresentException;

    @Operation(summary = "Modifica Indirizzo Legale",
            description = "Modifica un Indirizzo Legale presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Leg. modificato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
    void updateMainAddress(IndirizzoUpdateDTO modificaDTO);

    @Operation(summary = "Eliminazione Indirizzo Legale",
            description = "Elimina un Indirizzo Legale presente nel sistema tramite ID")
    @ApiResponse(responseCode = "204", description = "Indirizzo Leg. eliminato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
    void deleteMainAddress(Long addressMailId);

}
