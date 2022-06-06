package it.progetto.energy.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.FatturaDTO;
import it.progetto.energy.dto.FatturaModificaDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.repository.FatturaRepository;
import it.progetto.energy.service.FatturaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/fattura")
//@Data
//@AllArgsConstructor
public class FatturaController {

	@Autowired
	FatturaRepository comuneRepo;
	@Autowired
	FatturaService fatturaServ;

	@Operation(summary = "Ritorna tutte le Fatture presenti nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity getAllFatture() {
		return ResponseEntity.ok(fatturaServ.getAllFatture());
	}

	@Operation(summary = "Ritorna tutte le Fatture presenti nel sistema, paginate", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFatturePaginate")
	public ResponseEntity getAllFatture(Pageable page) {
		return ResponseEntity.ok(fatturaServ.getAllFatture(page));
	}

	@Operation(summary = "Ritorna tutte le Fatture riferite ad un singolo cliente", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFattureByCliente/{id}")
	public ResponseEntity getFattureByCliente(@PathParam("id") Long id) {
		return ResponseEntity.ok(fatturaServ.getFatturaByCliente(id));
	}

	@Operation(summary = "Ritorna tutte le Fatture in un determinato stato", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByStato/{stato}")
	public ResponseEntity getFattureByStato(@RequestBody StatoDTO dto, Pageable page) throws WrongInsertException {
		// StatoFattura statoF = null;
		// switch (stato.toUpperCase()) {
		// case "PAGATA":
		// statoF = StatoFattura.PAGATA;
		// break;
		// case "NON PAGATA":
		// statoF = StatoFattura.NON_PAGATA;
		// break;
		// case "ANNULLATA":
		// statoF = StatoFattura.ANNULLATA;
		// break;
		// case "SCADUTA":
		// statoF = StatoFattura.SCADUTA;
		// break;
		// case "DA RIMBORSARE":
		// statoF = StatoFattura.DA_RIMBORSARE;
		// break;
		// case "RIMBORSATA":
		// statoF = StatoFattura.RIMBORSATA;
		// break;
		// default:
		// throw new WrongInsertException("errore inserimento");
		// }
		StatoFattura statoF = dto.getStato();
		return ResponseEntity.ok(fatturaServ.getFatturaByStato(statoF, page));
	}

	@Operation(summary = "Ritorna tutte le Fatture riferite ad una specifica data", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByData")
	public ResponseEntity getFattureByData(@RequestBody DataDTO data, Pageable page) {
		// DateTimeFormatter dtForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return ResponseEntity.ok(fatturaServ.getFatturaByData(data, page));
	}

	@Operation(summary = "Ritorna tutte le Fatture riferite ad un anno", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFattureByAnno/{anno}")
	public ResponseEntity getFattureByAnno(@PathParam("anno") Integer anno, Pageable page) {
		return ResponseEntity.ok(fatturaServ.getFatturaByAnno(anno, page));
	}

	@Operation(summary = "Ritorna tutte le Fatture in un range di importi ", description = "")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByRange")
	public ResponseEntity getFAttureByRange(@RequestBody RangeDTO dto, Pageable page) {
		return ResponseEntity.ok(fatturaServ.getFatturaByImporto(dto, page));
	}

	@Operation(summary = "inserisce una Fattura nel sistema", description = "inserisce una Fattura nel sistema")
	@ApiResponse(responseCode = "200", description = "Fattura inserita correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE nell'inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity inserisciFattura(@Valid @RequestBody FatturaDTO dto) {
		fatturaServ.inserisciFattura(dto);
		return ResponseEntity.ok("Fattura inserita");
	}

	@Operation(summary = "Modifica una Fattura nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Fattura modificata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaFattura(@Valid @RequestBody FatturaModificaDTO modificaDTO) {
		fatturaServ.modificaFattura(modificaDTO);
		return ResponseEntity.ok("Fattura modificata");
	}

	@Operation(summary = "Elimina una Fattura nel sistema", description = "")
	@ApiResponse(responseCode = "200", description = "Fattura eliminata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaFattura(@PathVariable Long id) {
		fatturaServ.eliminaFattura(id);
		return ResponseEntity.ok("Fattura eliminata");
	}

}
