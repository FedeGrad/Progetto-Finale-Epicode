package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.customer.CustomerDTO;
import it.progetto.energy.dto.customer.CustomerOutputDTO;
import it.progetto.energy.dto.customer.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.ProvinciaSearchDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CustomerApi {

    //TODO REFACTOR

    @Operation(summary = "Recupero Clienti per pagina",
            description = "Restituisce tutti i Clienti presenti nel sistema per pagina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    List<CustomerOutputDTO> findAllCustomer(Pageable page);

    @Operation(summary = "Recupero Clienti per nome",
            description = "Restituisce i Clienti presenti nel sistema con un determinato nome")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<CustomerOutputDTO> findCustomerByName(String name, Pageable page);

    @Operation(summary = "Recupero Cliente per parte di nome",
            description = "Restituisce i Clienti presenti nel sistema che contengono il valore passato nel nome")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<CustomerOutputDTO> findCustomerByNameContains(String name, Pageable page);

    @Operation(summary = "Recupero Clienti per data",
            description = "Restituisce i Clienti per data di inserimento")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<CustomerOutputDTO> findCustomerByDataInserimento(DataDTO dataDTO, Pageable page);

    @Operation(summary = "Recupero Clienti per data ultimo contatto",
            description = "Restituisce i Clienti per data dell'ultimo contatto")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<CustomerOutputDTO> findCustomerByDataLastUpdate(DataDTO dataDTO, Pageable page);

    @Operation(summary = "Recupero Clienti per provincia",
            description = "Restituisce i Clienti per provincia")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<CustomerOutputDTO> findCustomerByProvincia(ProvinciaSearchDTO provinciaSearchDTO);

    @Operation(summary = "Inserimento Cliente",
            description = "Inserisce un Cliente nel sistema")
    @ApiResponse(responseCode = "200", description = "Cliente inserito correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
    CustomerOutputDTO createCustomer(CustomerDTO customerDTO);

    @Operation(summary = "Modifica Cliente",
            description = "Modifica un Cliente presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Cliente modificato")
    @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    CustomerOutputDTO updateCustomer(CustomerUpdateDTO customerUpdateDTO);

    @Operation(summary = "Eliminazione Cliente",
            description = "Elimina un cliente tramite l'ID")
    @ApiResponse(responseCode = "200", description = "Cliente eliminato")
    @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    void deleteCustomer(Long customerId);

}
