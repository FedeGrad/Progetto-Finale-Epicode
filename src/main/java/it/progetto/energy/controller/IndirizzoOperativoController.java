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
import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.service.IndirizzoOperativoService;

@RestController
@RequestMapping("/indirizzo_operativo")
@Tag(name = "Indirizzo Operativo Controller", description = "Gestione degli indirizzi operativi")
public class IndirizzoOperativoController {

	@Autowired
	IndirizzoOperativoService indirizzoOpServ;

	@Deprecated
	@Operation(summary = "Ritorno Indirizzi Operativi",
			description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
	@GetMapping
	public ResponseEntity getAllIndirizziOp() {
		return ResponseEntity.ok(indirizzoOpServ.getAllIndirizziOperativi());
	}

	@Operation(summary = "Recupero Indirizzi Operativi per pagina",
			description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
	@GetMapping("/getIndirizziOpPaginati")
	public ResponseEntity getAllIndirizziOp(Pageable page) {
		return ResponseEntity.ok(indirizzoOpServ.getAllIndirizziOperativi(page));
	}

	@Operation(summary = "Inserimento Indirizzo Operativo",
			description = "Inserisce un Indirizzo Operativo nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Op. inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public ResponseEntity inserisciIndirizzoOp(@Valid @RequestBody IndirizzoDTO dto)
			throws ElementAlreadyPresentException {
		indirizzoOpServ.inserisciIndirizzoOperativo(dto);
		return ResponseEntity.ok("Indirizzo Operativo inserito");
	}

	@Operation(summary = "Modifica Indirizzo Operativo",
			description = "Modifica Indirizzo Operativo presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Op. modificato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaIndirizzoOp(@RequestBody IndirizzoModificaDTO modificaDTO) {
		indirizzoOpServ.modificaIndirizzoOperativo(modificaDTO);
		return ResponseEntity.ok("Indirizzo Operativo modificato");
	}

	@Operation(summary = "Eliminazione Indirizzo Operativo",
			description = "Elimina un Indirizzo Operativo presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Op. eliminato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaIndirizzoOp(@PathVariable Long id) {
		indirizzoOpServ.eliminaIndirizzoOperativo(id);
		return ResponseEntity.ok("Indirizzo Operativo eliminato");
	}

}
