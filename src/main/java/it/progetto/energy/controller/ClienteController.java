package it.progetto.energy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import it.progetto.energy.repository.ClienteRepository;
import it.progetto.energy.service.ClienteService;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/cliente")
//@Data
//@AllArgsConstructor
@Tag(name = "Controller Cliente", description = "Gestione dei clienti")
public class ClienteController {

	
	@Autowired
	ClienteService clienteServ;
	@Autowired
	ClienteRepository clienteRepo;

	@Operation(summary = "Recupera tutti i clienti presenti nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessuna Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity getAllClienti() {
		return ResponseEntity.ok(clienteServ.getAllClienti());
	}

	@Operation(summary = "Recupera tutti i Clienti presenti nel sistema, paginati", description = "")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getClientiPaginati")
	public ResponseEntity getAllCliente(Pageable page) {
		return ResponseEntity.ok(clienteServ.getClientiPaginati(page));
	}

	@Operation(summary = "Recupera i Clienti presenti nel sistema filtrati e ordinati per nome", description = "Recupera i Clienti presenti nel sistema filtrati e ordinati per nome")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getClientiByName/{nome}")
	public ResponseEntity getClienteByName(@PathVariable("nome") String nome, Pageable page) {
		return ResponseEntity.ok(clienteServ.getClientiByNome(nome, page));
	}

	@Operation(summary = "Recupera i Clienti presenti nel sistema filtrati e ordinati per nome", description = "Recupera i Clienti presenti nel sistema filtrati e ordinati per nome")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getClientiByNameContains/{nome}")
	public ResponseEntity getClienteByNameContains(@PathVariable("nome") String nome, Pageable page) {
		return ResponseEntity.ok(clienteServ.getClientiByNomeContain(nome, page));
	}

	@Operation(summary = "Recupera i Clienti per data di inserimento", description = "Recupera i Clienti per data di inserimento")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getClientiByDataInserimento")
	public ResponseEntity getClienteByDataInserimento(@RequestBody DataDTO data, Pageable page) {
		return ResponseEntity.ok(clienteServ.getClientiByDataInserimento(data, page));
	}

	@Operation(summary = "Recupera i Clienti per data ultimo contatto", description = "Recupera i Clienti per data ultimo contatto")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getClienteByDataUltimoContatto")
	public ResponseEntity getClienteByDataUltimoContatto(@RequestBody DataDTO data, Pageable page) {
		return ResponseEntity.ok(clienteServ.getClientiByDataUltimoContatto(data, page));
	}

	@Operation(summary = "Recupera i Clienti presenti nel sistema filtrati e ordinati per provincia", description = "Recupera i Clienti presenti nel sistema filtrati e ordinati per provincia")
	@ApiResponse(responseCode = "200", description = "Clienti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Cliente trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getClientiByProvincia")
	public ResponseEntity getClienteByProvincia(@RequestBody RicercaProvinciaDTO dto) {
		return ResponseEntity.ok(clienteServ.getClientiByProvincia(dto));
	}

	@Operation(summary = "inserisce un Cliente nel sistema", description = "inserisce un Cliente nel sistema")
	@ApiResponse(responseCode = "200", description = "Cliente inserito correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity inserisciCliente(@Valid @RequestBody ClienteDTO dto) throws WrongInsertException {
		clienteServ.inserisciCliente(dto);
		return ResponseEntity.ok("Cliente inserito");
	}

	@Operation(summary = "Modifica un Cliente nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Cliente modificato")
	@ApiResponse(responseCode = "404", description = "Cliente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaCliente(@Valid @RequestBody ClienteModificaDTO modificaDTO)
			throws NotFoundException, WrongInsertException {
		clienteServ.modificaCliente(modificaDTO);
		return ResponseEntity.ok("Cliente modificato");
	}

	@Operation(summary = "Elimina un Cliente nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Cliente eliminato")
	@ApiResponse(responseCode = "404", description = "Cliente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaCliente(@PathVariable Long id) {
		clienteServ.eliminaCliente(id);
		return ResponseEntity.ok("Cliente eliminato");
	}

}
