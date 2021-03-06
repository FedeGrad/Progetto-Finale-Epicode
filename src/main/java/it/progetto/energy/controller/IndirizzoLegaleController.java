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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import it.progetto.energy.service.IndirizzoLegaleService;

@RestController
@RequestMapping("/indirizzo_legale")
public class IndirizzoLegaleController {

	@Autowired
	IndirizzoLegaleRepository indirizzoLegRepo;
	@Autowired
	IndirizzoLegaleService indirizzoLegServ;

	@Operation(summary = "Ritorna tutti gli Indirizzi Legali presenti nel sistema, paginati", description = "")
	@ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Leg. trovato")
	@GetMapping("/getIndirizziLegPaginati")
	public ResponseEntity getAllIndirizziLeg(Pageable page) {
		return ResponseEntity.ok(indirizzoLegServ.getIndirizziLegPaginati(page));
	}

	@Operation(summary = "Ritorna tutti gli Indirizzi Legali presenti nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Leg. trovato")
	@GetMapping
	public ResponseEntity getAllIndirizziLeg() {
		return ResponseEntity.ok(indirizzoLegServ.getAllIndirizziLegali());
	}

	@Operation(summary = "inserisce un Indirizzo Legale nel sistema", description = "inserisce un Indirizzo Legale nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Leg. inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public ResponseEntity inserisciIndirizzoLeg(@Valid @RequestBody IndirizzoDTO dto)
			throws ElementAlreadyPresentException {
		System.out.println(dto.getVia() + dto.getCap() + dto.getCivico() + dto.getLocalita());
		indirizzoLegServ.inserisciIndirizzoLegale(dto);
		return ResponseEntity.ok("Indirizzo Legale inserito");
	}

	@Operation(summary = "Modifica un Indirizzo Legale nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Indirizzo Leg. modificato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaIndirizzoLeg(@RequestBody IndirizzoModificaDTO modificaDTO) {
		indirizzoLegServ.modificaIndirizzoLegale(modificaDTO);
		return ResponseEntity.ok("Indirizzo Legale modificato");
	}

	@Operation(summary = "Elimina un Indirizzo Legale nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Indirizzo Leg. eliminato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaInidirizzoLeg(@PathVariable Long id) {
		indirizzoLegServ.eliminaIndirizzoLegale(id);
		return ResponseEntity.ok("Indirizzo Legale eliminato");
	}

}
