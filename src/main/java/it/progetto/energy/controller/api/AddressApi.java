package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;

import java.util.List;

public interface AddressApi {

    @Operation(summary = "Recupero Indirizzi Legali",
            description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
    List<AddressOutputDTO> findAllAddress();

    @Operation(summary = "Recupero Indirizzi Legali",
            description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
    List<AddressOutputDTO> findAllAddressPaged(PageDTO pageDTO);

    @Operation(summary = "Inserimento Indirizzo Legale",
            description = "Inserisce un Indirizzo Legale nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Leg. inserito correttamente")
    AddressOutputDTO createAddress(AddressDTO addressDTO);

    @Operation(summary = "Modifica Indirizzo Legale",
            description = "Modifica un Indirizzo Legale presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Indirizzo Leg. modificato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
    AddressOutputDTO updateAddress(AddressUpdateDTO addressUpdateDTO);

    @Operation(summary = "Eliminazione Indirizzo Legale",
            description = "Elimina un Indirizzo Legale presente nel sistema tramite ID")
    @ApiResponse(responseCode = "204", description = "Indirizzo Leg. eliminato")
    @ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
    void deleteAddress(Long mainAddressId);

}
