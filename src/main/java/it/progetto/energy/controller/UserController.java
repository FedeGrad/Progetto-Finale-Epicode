package it.progetto.energy.controller;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.energy.dto.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.service.UserRuoliService;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Gestione registrazione e accessi")
public class UserController {

	@Autowired
	UserRuoliService userServ;

	@Deprecated
	@Operation(summary = "Recupero Utenti",
			description = "Restituisce gli Utenti presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Utenti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
	@GetMapping
	public ResponseEntity getAllUser() {
		return ResponseEntity.ok(userServ.getAllUser());
	}

	@Operation(summary = "Recupero Utenti per pagina",
			description = "Restituisce gli Utenti presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Utenti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
	@GetMapping("/getAllUser")
	public ResponseEntity getAllUser(Pageable page) {
		return ResponseEntity.ok(userServ.getAllUser(page));
	}

	@Operation(summary = "Inserimento Utente",
			description = "Inserisce un Utente nel sistema")
	@ApiResponse(responseCode = "200", description = "Utente inserito correttamente nel sistema")
	@ApiResponse(responseCode = "400", description = "Utente gia presente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity inserisciUser(@Valid @RequestBody UserDTO dto) throws ElementAlreadyPresentException {
		userServ.inserisciUser(dto);
		return ResponseEntity.ok("Utente inserito");
	}

	@Operation(summary = "Modifica Utente",
			description = "Modifica un Utente presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Utente modificato")
	@ApiResponse(responseCode = "404", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaUser(@RequestBody UserDTO modificaDTO) {
		userServ.modificaUser(modificaDTO);
		return ResponseEntity.ok("Utente modificato");
	}

	@Operation(summary = "Eliminazione Utente", 
			description = "Elimina un Utente presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Utente eliminato")
	@ApiResponse(responseCode = "404", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaUser(@PathVariable Long id) {
		userServ.eliminaUser(id);
		return ResponseEntity.ok("Utente eliminato");
	}

}
