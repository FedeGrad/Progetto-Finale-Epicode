package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.UserApi;
import it.progetto.energy.dto.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.service.UserRuoliService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Gestione registrazione e accessi")
@Slf4j
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserRuoliService userRuoliService;

	@Deprecated
	@Operation(summary = "Recupero Utenti",
			description = "Restituisce gli Utenti presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Utenti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> findAllUser() {
		return userRuoliService.getAllUser();
	}

	@Operation(summary = "Recupero Utenti per pagina",
			description = "Restituisce gli Utenti presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Utenti trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<User> findAllUser(Pageable page) {
		return userRuoliService.getAllUser(page);
	}

	@Operation(summary = "Inserimento Utente",
			description = "Inserisce un Utente nel sistema")
	@ApiResponse(responseCode = "204", description = "Utente inserito correttamente nel sistema")
	@ApiResponse(responseCode = "400", description = "Utente gia presente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createUser(@Valid @RequestBody UserDTO userDTO) throws ElementAlreadyPresentException {
		userRuoliService.inserisciUser(userDTO);
		log.info("User created");
	}

	@Operation(summary = "Modifica Utente",
			description = "Modifica un Utente presente nel sistema")
	@ApiResponse(responseCode = "204", description = "Utente modificato")
	@ApiResponse(responseCode = "404", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@RequestBody UserDTO modificaDTO) {
		userRuoliService.modificaUser(modificaDTO);
		log.info("Utente modificato");
	}

	@Operation(summary = "Eliminazione Utente", 
			description = "Elimina un Utente presente nel sistema")
	@ApiResponse(responseCode = "204", description = "Utente eliminato")
	@ApiResponse(responseCode = "404", description = "Utente non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		userRuoliService.eliminaUser(id);
		log.info("Utente eliminato");
	}

}
