package it.progetto.energy.controller;

import javax.validation.Valid;

import it.progetto.energy.controller.api.CustomerApi;
import it.progetto.energy.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.dto.ClienteDTO;
import it.progetto.energy.dto.ClienteModificaDTO;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RicercaProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Cliente Controller", description = "Gestione dei clienti")
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

	private final ClienteService clienteService;

	@Deprecated
	@Operation(summary = "Recupero Clienti",
			description = "Restituisce tutti i Clienti presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessuna Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findAllCustomer() {
		return clienteService.getAllClienti();
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findAllCustomer(Pageable page) {
		//TODO ADDED MAPPPER
		return clienteService.getAllClienti(page);
	}

	@Operation(summary = "Recupero Clienti per nome",
			description = "Restituisce i Clienti presenti nel sistema con un determinato nome")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/{name}/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByName(@PathVariable("name") String name, Pageable page) {
		//TODO ADDED MAPPPER
		return clienteService.getClientiByNome(name, page);
	}

	@Operation(summary = "Recupero Cliente per parte di nome",
			description = "Restituisce i Clienti presenti nel sistema che contengono il valore passato nel nome")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/contains/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByNameContains(@PathVariable("name") String name, Pageable page) {
		//TODO ADDED MAPPPER
		return clienteService.getClientiByNomeContain(name, page);
	}

	@Operation(summary = "Recupero Clienti per data",
			description = "Restituisce i Clienti per data di inserimento")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/insert/data")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByDataInserimento(@RequestBody DataDTO data, Pageable page) {
		return clienteService.getClientiByDataInserimento(data, page);
	}

	@Operation(summary = "Recupero Clienti per data ultimo contatto",
			description = "Restituisce i Clienti per data dell'ultimo contatto")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/last/update/data")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByDataLastUpdate(@RequestBody DataDTO data, Pageable page) {
		return clienteService.getClientiByDataUltimoContatto(data, page);
	}

	@Operation(summary = "Recupero Clienti per provincia",
			description = "Restituisce i Clienti per provincia")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/provincia")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findCustomerByProvincia(@RequestBody RicercaProvinciaDTO dto) {
		return clienteService.getClientiByProvincia(dto);
	}

	@Operation(summary = "Inserimento Cliente",
			description = "Inserisce un Cliente nel sistema")
	@ApiResponse(responseCode = "200", description = "Cliente inserito correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createCustomer(@Valid @RequestBody ClienteDTO dto) throws WrongInsertException {
		return clienteService.inserisciCliente(dto);
	}

	@Operation(summary = "Modifica Cliente",
			description = "Modifica un Cliente presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Cliente modificato")
	@ApiResponse(responseCode = "404", description = "Cliente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<?> modificaCliente(@Valid @RequestBody ClienteModificaDTO modificaDTO)
			throws NotFoundException, WrongInsertException {
		clienteService.modificaCliente(modificaDTO);
		return ResponseEntity.ok("Cliente modificato");
	}

	@Operation(summary = "Eliminazione Cliente",
			description = "Elimina un cliente tramite l'ID")
	@ApiResponse(responseCode = "200", description = "Cliente eliminato")
	@ApiResponse(responseCode = "404", description = "Cliente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaCliente(@PathVariable Long id) {
		clienteService.eliminaCliente(id);
		return ResponseEntity.ok("Cliente eliminato");
	}

}
