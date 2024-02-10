package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.cliente.CustomerDTO;
import it.progetto.energy.dto.cliente.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.FindProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.persistence.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.webjars.NotFoundException;

import java.util.List;


public interface CustomerApi {

    @Operation(summary = "Recupero Clienti per pagina",
            description = "Restituisce tutti i Clienti presenti nel sistema per pagina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    Page<Cliente> findAllCustomer(Pageable page);

    @Operation(summary = "Recupero Clienti per nome",
            description = "Restituisce i Clienti presenti nel sistema con un determinato nome")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    Page<Cliente> findCustomerByName(String name, Pageable page);

    @Operation(summary = "Recupero Cliente per parte di nome",
            description = "Restituisce i Clienti presenti nel sistema che contengono il valore passato nel nome")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    Page<Cliente> findCustomerByNameContains(String name, Pageable page);

    @Operation(summary = "Recupero Clienti per data",
            description = "Restituisce i Clienti per data di inserimento")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    Page<Cliente> findCustomerByDataInserimento(DataDTO dataDTO, Pageable page);

    @Operation(summary = "Recupero Clienti per data ultimo contatto",
            description = "Restituisce i Clienti per data dell'ultimo contatto")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    Page<Cliente> findCustomerByDataLastUpdate(DataDTO dataDTO, Pageable page);

    @Operation(summary = "Recupero Clienti per provincia",
            description = "Restituisce i Clienti per provincia")
    @ApiResponse(responseCode = "200", description = "Clienti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
    List<Cliente> findCustomerByProvincia(FindProvinciaDTO findProvinciaDTO);

    @Operation(summary = "Inserimento Cliente",
            description = "Inserisce un Cliente nel sistema")
    @ApiResponse(responseCode = "200", description = "Cliente inserito correttamente")
    @ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
    Cliente createCustomer(CustomerDTO customerDTO) throws WrongInsertException;

    @Operation(summary = "Modifica Cliente",
            description = "Modifica un Cliente presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Cliente modificato")
    @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    void updateCustomer(CustomerUpdateDTO customerUpdateDTO)
            throws NotFoundException, WrongInsertException;

    @Operation(summary = "Eliminazione Cliente",
            description = "Elimina un cliente tramite l'ID")
    @ApiResponse(responseCode = "200", description = "Cliente eliminato")
    @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    void deleteCustomer(Long customerId);

}
