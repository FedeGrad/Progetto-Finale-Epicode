package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.AddressMainApi;
import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.service.IndirizzoLegaleService;
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
@RequestMapping("/indirizzo_legale")
@Tag(name = "Indirizzo Legale Controller", description = "Gestione degli indirizzi legali")
@Slf4j
@RequiredArgsConstructor
public class AddressMainController implements AddressMainApi {

	private final IndirizzoLegaleService indirizzoLegServ;

	@Deprecated
	@Operation(summary = "Recupero Indirizzi Legali",
			description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<IndirizzoLegale> findAllMainAddress() {
		return indirizzoLegServ.getAllIndirizziLegali();
	}

	@Operation(summary = "Recupero Indirizzi Legali",
			description = "Restituisce tutti gli Indirizzi Legali presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Indirizzi Leg. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Legale trovato")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<IndirizzoLegale> findAllMainAddress(Pageable page) {
		return indirizzoLegServ.getAllIndirizziLegali(page);
	}

	@Operation(summary = "Inserimento Indirizzo Legale",
			description = "Inserisce un Indirizzo Legale nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Leg. inserito correttamente")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createMainAddress(@Valid @RequestBody IndirizzoDTO dto) throws ElementAlreadyPresentException {
		log.info(dto.getVia() + dto.getCap() + dto.getCivico() + dto.getLocalita());
		indirizzoLegServ.inserisciIndirizzoLegale(dto);
	}

	@Operation(summary = "Modifica Indirizzo Legale",
			description = "Modifica un Indirizzo Legale presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Leg. modificato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMainAddress(@RequestBody IndirizzoModificaDTO modificaDTO) {
		indirizzoLegServ.modificaIndirizzoLegale(modificaDTO);
		log.info("Main address updated");
	}

	@Operation(summary = "Eliminazione Indirizzo Legale",
			description = "Elimina un Indirizzo Legale presente nel sistema tramite ID")
	@ApiResponse(responseCode = "204", description = "Indirizzo Leg. eliminato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Leg. non trovato")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMainAddress(@PathVariable Long id) {
		indirizzoLegServ.eliminaIndirizzoLegale(id);
		log.info("Main address deleted");
	}

}
