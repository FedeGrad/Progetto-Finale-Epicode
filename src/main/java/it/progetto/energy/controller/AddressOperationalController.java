package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.AddressOperationalApi;
import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import it.progetto.energy.service.IndirizzoOperativoService;
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
@RequestMapping("/indirizzo_operativo")
@Tag(name = "Indirizzo Operativo Controller", description = "Gestione degli indirizzi operativi")
@Slf4j
@RequiredArgsConstructor
public class AddressOperationalController implements AddressOperationalApi {

	private final IndirizzoOperativoService indirizzoOpServ;

	@Deprecated
	@Operation(summary = "Ritorno Indirizzi Operativi",
			description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<IndirizzoOperativo> findAllAddressOperational() {
		return indirizzoOpServ.getAllIndirizziOperativi();
	}

	@Operation(summary = "Recupero Indirizzi Operativi per pagina",
			description = "Restituisce tutti gli Indirizzi Operativi presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Indirizzi Op. trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Indirizzo Op. trovato")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<IndirizzoOperativo> findAllAddressOperational(Pageable page) {
		return indirizzoOpServ.getAllIndirizziOperativi(page);
	}

	@Operation(summary = "Inserimento Indirizzo Operativo",
			description = "Inserisce un Indirizzo Operativo nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Op. inserito correttamente nel sistema")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void createOperationalAddress(@Valid @RequestBody IndirizzoDTO dto)
			throws ElementAlreadyPresentException {
		indirizzoOpServ.inserisciIndirizzoOperativo(dto);
		log.info("Address Operational added");
	}

	@Operation(summary = "Modifica Indirizzo Operativo",
			description = "Modifica Indirizzo Operativo presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Indirizzo Op. modificato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateOperationalAddress(@RequestBody IndirizzoModificaDTO modificaDTO) {
		indirizzoOpServ.modificaIndirizzoOperativo(modificaDTO);
		log.info("Address Operational updated");
	}

	@Operation(summary = "Eliminazione Indirizzo Operativo",
			description = "Elimina un Indirizzo Operativo presente nel sistema")
	@ApiResponse(responseCode = "204", description = "Indirizzo Op. eliminato")
	@ApiResponse(responseCode = "404", description = "Indirizzo Op. non trovato")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOperationalAddress(@PathVariable Long id) {
		indirizzoOpServ.eliminaIndirizzoOperativo(id);
		log.info("Indirizzo Operativo eliminato");
	}

}
